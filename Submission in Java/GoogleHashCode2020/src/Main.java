import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Jaypatelbond
 * @version 1.0
 * @since 11/01/2020
 */

public class Main {
    private static final String inputSource = "src/Input Files/d_quite_big";
    private static final String outputSource = "src/Output Files/d_quite_big";
    private static final Path inputFile = Paths.get(inputSource + ".in");
    private static final Path outputFile = Paths.get(outputSource + ".out");


    public static void main(String[] args) {
        Instant start = Instant.now();
        Main main = new Main();
        main.startProblem();

        Instant finish = Instant.now();
        System.out.println("Execution time:" + Duration.between(start, finish).toMillis() + " ms.");

    }

    private void startProblem() {
        Main.Input input = readInputSource();
        input.print();
        Set<Integer> result = new HashSet<>();
        int[][] memo = getMemoMaze(input);

        System.out.println("Max possible Weight: " + ZeroOneKnapsack(input.getMaxSlices(), input.getTypesofPizza(), input.getSlices(), memo, result));

        System.out.println(Arrays.toString(result.toArray()));
        System.out.println(result.stream().map(index -> input.getSlices()[index]).reduce(Integer::sum));
        writeResultToFile(result);
    }

    private int[][] getMemoMaze(Input input) {
        int[][] memo = new int[input.getTypesofPizza() + 1][input.getMaxSlices() + 1];
        for (int j = 0; j <= input.getTypesofPizza(); j++) {
            Arrays.fill(memo[j], 0, input.getMaxSlices() + 1, -1);
        }
        return memo;
    }

    private int ZeroOneKnapsack(int maxWeight, int numberOfElements, int[] weights, int[][] memo, Set<Integer> result) {
        if (numberOfElements == 0 || maxWeight == 0) {
            return 0;
        }
        if (memo[numberOfElements][maxWeight] == -1) {
            if (weights[numberOfElements - 1] > maxWeight) {
                memo[numberOfElements][maxWeight] = ZeroOneKnapsack(maxWeight, numberOfElements - 1, weights, memo, result);

            } else {
                Set<Integer> resultWithElement = new HashSet<Integer>();
                Set<Integer> resultWithoutElement = new HashSet<Integer>();
                int weightWithElement = weights[numberOfElements - 1] + ZeroOneKnapsack(maxWeight - weights[numberOfElements - 1], numberOfElements - 1,
                        weights, memo, resultWithElement);
                int weightWithoutElement = ZeroOneKnapsack(maxWeight, numberOfElements - 1, weights, memo, resultWithoutElement);
                if (weightWithElement >= weightWithoutElement) {
                    result.addAll(resultWithElement);
                    result.add(numberOfElements - 1);
                    memo[numberOfElements][maxWeight] = weightWithElement;
                } else {
                    result.addAll(resultWithoutElement);
                    memo[numberOfElements][maxWeight] = weightWithoutElement;
                }
            }
        }
        return memo[numberOfElements][maxWeight];
    }


    private Input readInputSource() {
        try (BufferedReader reader = Files.newBufferedReader(inputFile, StandardCharsets.UTF_8)) {
            Main.Input input = new Input();
            String[] firstLine = reader.readLine().split(" ");
            input.setMaxSlices(Integer.parseInt(firstLine[0]));
            input.setTypesofPizza(Integer.parseInt(firstLine[1]));
            input.setSlices(stringArrayToIntArray(reader.readLine().split(" ")));
            return input;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    int[] stringArrayToIntArray(String[] stringArray) {
        return Stream.of(stringArray).mapToInt(Integer::parseInt).toArray();
    }

    private void writeResultToFile(Set<Integer> result) {
        StringBuilder outputFileBuilder = new StringBuilder();
        outputFileBuilder.append(result.size() + "\n");
        result.forEach(item -> outputFileBuilder.append(item + " "));

        try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
            writer.write(outputFileBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Input {
        private int maxSlices; // Max Weights
        private int typesofPizza; // Number of Element
        private int[] slices;  // Weights

        public int getMaxSlices() {
            return maxSlices;
        }

        public void setMaxSlices(int maxSlices) {
            this.maxSlices = maxSlices;
        }

        public int getTypesofPizza() {
            return typesofPizza;
        }

        public void setTypesofPizza(int typesofPizza) {
            this.typesofPizza = typesofPizza;
        }

        public int[] getSlices() {
            return slices;
        }

        public void setSlices(int[] slices) {
            this.slices = slices;
        }

        public void print() {
            System.out.println("maxSlices = " + maxSlices + "\ntypesofElement = " + typesofPizza + "\nSlices = " + Arrays.toString(slices) + "\n");
        }
    }
}