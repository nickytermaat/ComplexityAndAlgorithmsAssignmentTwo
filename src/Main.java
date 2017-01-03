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
        File file = new File("input.txt");
        Main main = new Main();
        RSHeap heap = new RSHeap(HEAP_SIZE, main.readFromFile(file));
        ArrayList<ArrayList<Integer>> output = heap.run();
        for (int i = 0; i < output.size(); i++) {
            for (int j = 0; j < output.get(i).size(); j++) {
                System.out.printf(String.valueOf(output.get(i).get(j)) + " ");
            }
            System.out.println("\n");
        }
    }

    /**
     * //TODO: Explain why using an arraylist is easier than an array
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    private int[] readFromFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",");
        ArrayList<Integer> numbers = new ArrayList<>();
        int i = 0;
        while(scanner.hasNext()){
            int number = scanner.nextInt();
            numbers.add(number);
            i++;
        }

        int[] toReturn = new int[numbers.size()];
        for (int j = 0; j < numbers.size(); j++) {
            toReturn[j] = numbers.get(j);
        }
        return toReturn;

    }
}
