package ComplexityAssignment2;

import ComplexityAssignment2.test.HeapTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Nicky on 01/12/2016.
 */
public class Main {
    private static int HEAP_SIZE = 8;

    public static void main(String[] args) throws IOException {
        assert HEAP_SIZE >= 1 : "Invalid heap size";

        File file = new File("input.txt");
        Main main = new Main();
        main.userInterface();
        // Ask to run ComplexityAssignment2.test or actual sorting alogrithm
        // Ask for numbers as input or from file
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println("* \t " + choice + " Run sorting algorithm");
                System.out.println("******************************************************");
                RSHeap heap = new RSHeap(HEAP_SIZE, main.readFromFile(file));
                ArrayList<ArrayList<Integer>> output = heap.replacementSort();
                for (int i = 0; i < output.size(); i++) {
                    System.out.printf("* \t Run " + (i + 1) + ": ");
                    for (int j = 0; j < output.get(i).size(); j++) {
                        System.out.printf(String.valueOf(output.get(i).get(j)) + " ");
                    }
                    System.out.printf("\n");
                }
                break;
            case 2:
                System.out.println("* \t " + choice + " Run tests");
                System.out.println("******************************************************");

                JUnitCore junit = new JUnitCore();
                Result resultHeap = junit.run(HeapTest.class);
                System.out.println("* \t Test ComplexityAssignment2.Heap : Failure count \t" + resultHeap.getFailureCount() );
                Result resultRSHeap = junit.run(HeapTest.class);
                System.out.println("* \t Test ComplexityAssignment2.RSHeap : Failure count \t" + resultRSHeap.getFailureCount() );
                break;
        }

    }


    public void userInterface() {
        System.out.println("******************************************************");
        System.out.println("* \t Run sorting algorithm \t Press (1)");
        System.out.println("* \t Run tests \t Press (2)");
        System.out.println("******************************************************");
    }

    /**
     * @param file Reference to the file that should contain the input numbers
     * @return an array with all the numbers in the input file
     * @throws FileNotFoundException when the inputfile cannot be found
     *                               <p>
     *                               This function first reads the file into an arrayList of integers, then returns it as an array of integers.
     *                               We're using an ArrayList because it grows on-demand and we don't have to declare the size beforehand.
     *                               We then return it as a normal array because that's faster to work with in the ComplexityAssignment2.Heap class
     */
    private int[] readFromFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",");
        ArrayList<Integer> numbers = new ArrayList<>();
        while (scanner.hasNext()) {
            try{
                int number = scanner.nextInt();
                numbers.add(number);
            } catch (InputMismatchException IME){
                System.err.println("Invalid input on " + scanner.next());
            }
        }

        int[] toReturn = new int[numbers.size()];
        for (int j = 0; j < numbers.size(); j++) {
            toReturn[j] = numbers.get(j);
        }
        return toReturn;

    }
}
