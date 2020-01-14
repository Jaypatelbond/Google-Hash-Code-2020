import java.util.Arrays;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author Jaypatelbond
 * @version 1.0
 * @since 11/01/2020
 */
public class Main {
    public static int maxPizzaSlices;
    public static int numberofPizza;
    public static String[] pizza;
    public static ArrayList<String> outputList;
    public static int types;
    public static int ordering;


    public static void main(String[] args) throws FileNotFoundException, IOException {
        outputList = new ArrayList<>();

        //Read Input File.
        String inputFile = "src/Input Files/a_example";
        String outputFile = "src/Output Files/a_example";
        BufferedReader reader = new BufferedReader(new FileReader(inputFile + ".in"));

        String line, firstLine;
        firstLine = reader.readLine();

        String[] values;
        values = firstLine.split(" ");

        maxPizzaSlices = Integer.parseInt(values[0]);
        numberofPizza = Integer.parseInt(values[1]);


        int i = 0;
        int j;
        while ((line = reader.readLine()) != null) {
            String[] slices = line.split(" ");
            j = 0;
            for (String slice : slices) {
                j++;
            }
            i++;
        }

        System.out.println("Types of Pizzas: " + types);
        System.out.println("Ordering Pizzas: " + ordering);

        // Create output file.
        try (PrintWriter output = new PrintWriter(outputFile + ".out", "UTF-8")) {
            output.println(types);
            for (String outputLine : outputList) {
                output.println(outputLine);
            }
        }


    }

}
