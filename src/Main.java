import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Nicky on 01/12/2016.
 */
public class Main {

    private int heapSize;
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("input.txt");
        Main main = new Main();
        main.readFromFile(file);
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

    private void readFromFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(",");
        while(scanner.hasNext()){
            int number = scanner.nextInt();
            heapSize++;
            System.out.println(number + ", ");
            //TODO: Add number to heap

        }
    }
}
