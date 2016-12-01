import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Nicky on 01/12/2016.
 */
public class Main {

    //Hello
    public static void main(String[] args) {

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

}
