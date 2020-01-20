import sun.nio.cs.ext.MacArabic;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jaypatelbond
 * @version 1.0
 * @since 11/01/2020
 */

public class Main {
    public static int maxPizzaSlices;
    public static int typesofPizza;

    // array2D[i][j] is going to store true if sum j is
    // possible with array elements from 0 to i.
    public static boolean[][] array2D;

    static void createOutputFile(Set<Integer> v) {
        System.out.println("v = " + v);
        int sum = 0;
        for( int num : v) {
            sum += num;
        }
        System.out.println("v = " + sum);
        // Create output file.
        String outputFile = "src/Output Files/";
        String list = Arrays.toString(v.toArray()).replace("[", "").replace("]", "").replace(",", "");
        System.out.println("v = " + list);
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile, "b_small.out"))) {
            for (int i = 0; i < 1; i++) {
                writer.write("" + v.size());
            }
            writer.newLine();
            for (int i = v.size(); i == v.size(); i++) {
                writer.write(list);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // A recursive function to print all subsets with the
    // help of array2D[][]. Vector p[] stores current subset.
    static void printSubsetRecursion(int[] arr, int i, int sum, Set<Integer> p) {

        // If we reached end and sum is non-zero. We print
        // p[] only if arr[0] is equal to sum OR array2D[0][sum]
        // is true.
        if (i == 0 && sum != 0 && array2D[0][sum]) {
            p.add(arr[i]);
            createOutputFile(p);
            p.clear();
            return;
        }

        // If sum becomes 0
        if (i == 0 && sum == 0) {
            createOutputFile(p);
            p.clear();
            return;
        }

        // If given sum can be achieved after ignoring
        // current element.
        if (array2D[i - 1][sum]) {
            Set<Integer> b = new HashSet<>(p);
            printSubsetRecursion(arr, i - 1, sum, b);
        }

        // If given sum can be achieved after considering
        // current element.
        if (sum >= arr[i] && array2D[i - 1][sum - arr[i]]) {
            p.add(arr[i]);
            printSubsetRecursion(arr, i - 1, sum - arr[i], p);
        }
    }

    // Prints all subsets of arr[0..pizzaType-1] with sum 0.
    static void printAllSubsets(int[] arr, int pizzaType, int sum) {
        if (pizzaType == 0 || sum < 0)
            return;

        // Sum 0 can always be achieved with 0 elements
        array2D = new boolean[pizzaType][sum + 1];
        for (int i = 0; i < pizzaType; ++i) {
            array2D[i][0] = true;
        }

        // Sum arr[0] can be achieved with single element
        if (arr[0] <= sum)
            array2D[0][arr[0]] = true;

        // Fill rest of the entries in array2D[][]
        for (int i = 1; i < pizzaType; ++i)
            for (int j = 0; j < sum + 1; ++j)
                array2D[i][j] = (arr[i] <= j) ? (array2D[i - 1][j] || array2D[i - 1][j - arr[i]]) : array2D[i - 1][j];
        if (!array2D[pizzaType - 1][sum]) {
            System.out.println("There are no subsets with" + " sum " + sum);
            return;
        }

        // Now recursively traverse array2D[][] to find all
        // paths from array2D[n-1][sum]
        Set<Integer> p = new HashSet<Integer>();
        printSubsetRecursion(arr, pizzaType - 1, sum, p);
    }

    public static void main(String[] args) throws IOException {

        Instant start = Instant.now();
        //Read Input File.
        String inputFile = "src/Input Files/b_small";

        BufferedReader reader = new BufferedReader(new FileReader(inputFile + ".in"));

        String line, firstLine;
        firstLine = reader.readLine();

        String[] values;
        values = firstLine.split(" ");

        maxPizzaSlices = Integer.parseInt(values[0]);
        typesofPizza = Integer.parseInt(values[1]);

        while ((line = reader.readLine()) != null) {
            String[] types = line.split(" ");
            int[] array = Arrays.stream(types).mapToInt(Integer::parseInt).toArray();
            int pizzaType = types.length;
            printAllSubsets(array, pizzaType, maxPizzaSlices);
        }

        Instant finish = Instant.now();
        System.out.println("Execution time:" + Duration.between(start, finish).getSeconds() + " sec.");
        System.out.println("Execution time:" + Duration.between(start, finish).toMillis() + " ms.");

    }
}