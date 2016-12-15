import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Nicky on 01/12/2016.
 */
public class Main {

    private int heapSize;
    public static void main(String[] args) throws IOException {
        File file = new File("input.txt");
        Main main = new Main();
        RSHeap heap = new RSHeap(6, main.readFromFile(file));
        heap.run();
    }

    private void toCSV() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("cna-assignmenttwo.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        pw.flush();
        pw.close();
    }

    private int[] readFromFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",");
        int[] toReturn = new int[12];
        int i = 0;
        while(scanner.hasNext()){
            int number = scanner.nextInt();
            heapSize++;
            System.out.println(number + ", ");
            toReturn[i] = number;
            //TODO: Add number to heap
            i++;
        }
        return toReturn;
    }
}
