import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Kris on 12/7/2016.
 */
public class RSHeap {
    private int maxHeapSize = 0;
    private int[] heapArray;
    private int[] unsorted;
    private int deadspace = 0;
    private File out;
    private PrintWriter pw;
    private int runs;
    public RSHeap(int heapSize, int[] unsorted) throws IOException {
        this.maxHeapSize = heapSize;
        this.unsorted = unsorted;
        this.out = new File("output.txt");
        if(!out.exists()) {
            out.createNewFile();
        }
        this.pw = new PrintWriter(out);
        this.runs = 0;
    }

    public int[] run() throws FileNotFoundException {
        //Run through the unsorted array and insert the stuff into the heapArray where it's needed

        // Build the heap
        heapArray = new int[maxHeapSize];
        int amount = 0;
        int i = 0;
        while(i < unsorted.length){
//        for (int i = 0; i < unsorted.length; i++) {
            if(amount <= maxHeapSize && i != (unsorted.length - 1)){
                                                                //There's still room in the heap && we're not at the last element
                heapArray[amount] = unsorted[i]; i++;
                amount++;
            } else {                                            //The heap is full. Time to sort it!
                buildHeap(heapArray);                            //The heap should now be sorted

                /* FIRST TAKE THE SMALLEST ELEMENT FROM HEAP AND WRITE IT TO OUT *********************/
                writeOneOut();
                amount--;                                       //Make room for another element
                //deleteSmallest();

                /* THEN TAKE THE NEXT ELEMENT IN THE UNSORTED ARRAY*************************/
                //Get next element from the unsorted list
                i++;
                int nextNr = unsorted[i];
                int smallest = heapArray[0];
                if(nextNr >= smallest){
                    /*  IF NEXT UNSORTED ELEMENT IS BIGGER THAN CURRENT SMALLEST,
                        ADD IT TO HEAP AND WRITE SMALLEST FROM HEAP TO OUT*/
                    //Next number in the unsorted array is bigger than the current smallest, so all is good
                    heapArray[amount] = nextNr;
                    amount++;
                } else {
                    /* ELSE, ADD TO DEADSPACE */
                    deadspace++; //Take one spot from the maxHeapSize for the deadspace
                    maxHeapSize--; //Can safely decrease this one because the increment is stored in the deadspaec
                    heapArray[maxHeapSize] = nextNr; //Can access it by maxHeapSize because i just made it smaller, adding it to the newly created deadspace
                }
                if(maxHeapSize == 0){
                    //Deadspace is full. Write the out away and start over.
                    pw.write("End of run " + runs + "\n");
                    runs++;
                    maxHeapSize = deadspace;
                    deadspace = 0;
                    buildHeap(heapArray);
//                    amount = heapArray.length;
                    //Should reset or keep track of the amount of items currently in the heap?
                }
            }
        }
        return heapArray;
    }

    private void writeOneOut() throws FileNotFoundException {
        pw.write(heapArray[0] + ", ");
        //Do i need to flush / close the writer here?
        //I think i'll keep it active because i need to access it more often.
    }

    private void buildHeap(int[] unsorted) {

    }
}
