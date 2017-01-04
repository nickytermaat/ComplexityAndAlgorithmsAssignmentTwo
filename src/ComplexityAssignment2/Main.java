package ComplexityAssignment2;

import ComplexityAssignment2.test.HeapTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Nicky on 01/12/2016.
 */
public class Main {
    private static int HEAP_SIZE = 8;
    private static String INPUT_FILE = "input.txt";

    public static void main(String[] args) throws IOException {
        assert HEAP_SIZE >= 1 : "Invalid heap size";
        boolean exit = false;
        File file = new File(INPUT_FILE);
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        while(!exit) {
            main.userInterface();
            try {
                int choice = scanner.nextInt();
                assert choice == 1 || choice == 2 || choice == 3 || choice == 9 : "Fill in a correct number";
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
                        main.printOutput(INPUT_FILE, new File("output.txt"), output);
                        break;
                    case 2:
                        System.out.println("* \t " + choice + " Run tests");
                        System.out.println("******************************************************");

                        JUnitCore junit = new JUnitCore();
                        Result resultHeap = junit.run(HeapTest.class);
                        System.out.println("* \t Test Heap : \tFailure count \t" + resultHeap.getFailureCount());
                        Result resultRSHeap = junit.run(HeapTest.class);
                        System.out.println("* \t Test RSHeap : \tFailure count \t" + resultRSHeap.getFailureCount());
                        break;
                    case 3:
                        System.out.println("* \t " + choice + " Run with stats");
                        System.out.println("******************************************************");
                        main.runMultiplesorts(500000, 20);

                        break;
                    case 9:
                        exit = true;
                        System.out.println("Bye bye.");
                        break;
                }
            } catch (InputMismatchException ex) {
                throw new InputMismatchException("Please, fill in a number");
            }
        }

    }

    public void userInterface() {
        System.out.println("******************************************************");
        System.out.println("* \t Run sorting algorithm \t Press (1)");
        System.out.println("* \t Run tests \t\t\t\t Press (2)");
        System.out.println("* \t Run with stats  \t\t Press (3)");
        System.out.println("* \t Exit   \t\t\t\t Press (9)");
        System.out.println("******************************************************");
    }

    /**
     * @param file Reference to the file that should contain the input numbers
     * @return an array with all the numbers in the input file
     * @throws FileNotFoundException when the inputfile cannot be found
     * This function first reads the file into an arrayList of integers, then returns it as an array of integers.
     * We're using an ArrayList because it grows on-demand and we don't have to declare the size beforehand.
     * We then return it as a normal array because that's faster to work with in the ComplexityAssignment2.Heap class
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

    private void runMultiplesorts(int amountNumbers, int heapSize){
        BufferedWriter bw =printDate("stats option", new File("statsoutput.txt"));
        for (int ind = 0; ind < 10; ind++) {
            heapSize += 20;
            double averageSize = 0;
            double averageRuns = 0;

            for (int j = 0; j < 10; j++) {
                Random rand = new Random();
                int[] numbers = new int[amountNumbers];
                for (int i = 0; i < amountNumbers; i++) {
                    numbers[i] = rand.nextInt(2000) -100;
                }
                RSHeap heap = new RSHeap(heapSize, numbers);
                ArrayList<ArrayList<Integer>> output = heap.replacementSort();
                int runs = output.size();
                averageRuns += runs;
                int[] runsizes = new int[runs];
                int sum = 0;
                for (int i = 0; i < output.size(); i++) {
                    runsizes[i] = output.get(i).size();
                    sum += output.get(i).size();
                }

                double avg = (sum / runs);
                averageSize += avg;
            }
        System.out.println("* \t Amount of numbers: \t" + amountNumbers);
        System.out.println("* \t Heap size: \t\t\t" + heapSize + "\n* ");

        System.out.println("* \t Expected run size without RS: \t" + heapSize );
        System.out.println("* \t Average run size with RS: \t\t" + averageSize / 10 + "\n* ");

        System.out.println("* \t Expected amount of runs without RS: \t" + (amountNumbers / heapSize));
        System.out.println("* \t Average amount of runs with RS: \t\t" + averageRuns / 10);
            printStats(bw, heapSize, averageSize, amountNumbers, averageRuns);
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printOutput(String from, File file, ArrayList<ArrayList<Integer>> output){
        try {
            Writer writer = printDate(from, file);
            //TODO: Write run here
            String line = "";

            for (int i = 0; i < output.size(); i++) {
                line += "Run: " + i + " with size: " + output.size() + "\n";
                for (int j = 0; j < output.get(i).size(); j++) {
                    line += output.get(i).get(j) + ",";
                }
                line += "\n";
                writer.write(line);
                line = "";
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printStats(BufferedWriter writer, int heapSize, double averageSize, int amountNumbers, double averageRuns) {
        try {
            writer.write(heapSize + "\t" + heapSize + "\t" + averageSize / 10 + "\t" + (amountNumbers / heapSize) + "\t" + averageRuns /10 + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedWriter printDate(String from, File file){
        try{
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true));
            Date date = new Date();
            writer.write("\n Running from "+from+" on " + date + "\n\n");
            return writer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
