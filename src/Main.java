import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Nicky on 01/12/2016.
 */
public class Main {
    private static int HEAP_SIZE = 10;

    public static void main(String[] args) throws IOException {
        //Assert heapsize >= 1
        //TODO: User interface?
        // Ask to run test or actual sorting alogrithm
        // Ask for numbers as input or from file
        File file = new File("input.txt");
        Main main = new Main();
        RSHeap heap = new RSHeap(HEAP_SIZE, main.readFromFile(file));
        ArrayList<ArrayList<Integer>> output = heap.replacementSort();
        for (int i = 0; i < output.size(); i++) {
            System.out.printf("Run " + ( i + 1 )+ ": ");
            for (int j = 0; j < output.get(i).size(); j++) {
                System.out.printf(String.valueOf(output.get(i).get(j)) + " ");
            }
            System.out.printf("\n");
        }
        //TODO: Pretty-print the output
    }

    /**
     * @param file Reference to the file that should contain the input numbers
     * @return an array with all the numbers in the input file
     * @throws FileNotFoundException when the inputfile cannot be found
     *
     * This function first reads the file into an arrayList of integers, then returns it as an array of integers.
     * We're using an ArrayList because it grows on-demand and we don't have to declare the size beforehand.
     * We then return it as a normal array because that's faster to work with in the Heap class
     */
    private int[] readFromFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",");
        ArrayList<Integer> numbers = new ArrayList<>();
        while(scanner.hasNext()){
            int number = scanner.nextInt();
            numbers.add(number);
        }

        int[] toReturn = new int[numbers.size()];
        for (int j = 0; j < numbers.size(); j++) {
            toReturn[j] = numbers.get(j);
        }
        return toReturn;

    }
}
