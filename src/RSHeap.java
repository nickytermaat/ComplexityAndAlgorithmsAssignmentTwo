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



    // parent at position i
    // left child at position 2i
    // right child at position 2i + 1

    // child at position i
    // parent at position (i/2)

    // (minHeap) Parent's key <= children's key
    // (maxHeap) Parent's key >= children's key

    // The tree is filled from top to bottom, from left to right
    // All levels are completely filled, except possible the lowest level
    // The lowest level is filled from the left (no holes)

    // Percolate down:  delete the root from the heap (max element in maxHeap, min element in minHeap)
    //                  1. replace the root of the heap with the last element on the lowest level
    //                  2. compare the new root with its children; if they are in the correct order, stop
    //                  3. if not, swap the element with one of its children and return to the previous step

    // Percolate up:    1. add the element to the bottom level of the heap
    //                  2. compare the added element with its parent; if they are in the correct order, stop
    //                  3. if not, swap the element with its parent and return to the previous step.

    // insert(element) - insert element into heap - O(log N)
    // findMin - returns smallest element of a heap - O(1)
    // deleteMin - removes smallest element from a heap - O(log N)

    // In case of N alterning insertions and deletions: apply insert and deleteMin (of oder O(N log N)
    // In case of N insertions before deletions: apply insert to each element (of order O(N log N)
    // At the time of deletion the heap must be correct, not necessarily after each insertion

    // buildHeap - percolateDown repeatedly from the rear (starting at the last parent)

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
        for (int i = maxHeapSize/2; i < 0; i--) {
            percolateDown(i);
        }
    }

    /**
     * Percolate down from a given node in the heap
     * @param startNode the index of the node at which the percolate starts
     */
    private void percolateDown(int startNode) {
        int child;
        int temp = heapArray[startNode];
        int hole;

        // The hole is the startNode, the hole*2 must be smaller or equal to the heapsize, the hole becomes the child
        for(hole = startNode; hole * 2 <= maxHeapSize; hole = child) {
            child = hole * 2;
            boolean childCompare;

            // If the left child is smaller than the right child
            if (heapArray[child] <= heapArray[child+1]){
                childCompare = true; // the left child should become the parent node
            } else { // Else left child bigger than right child
                childCompare = false; // the right child should become the parent node
            }

            // If the childnodeposition is smaller than the max heapsize and
            // the left child is bigger than the right child
            // increase the child-index so it becomes the right child
            if (child != maxHeapSize && !childCompare) {
                child++;
            }

            // if the element on the heapindex child is smaller than the temp value,
            // the value on the startnode index becomes the value found at childindex
            if (!(heapArray[child] < temp)) {
                heapArray[startNode] = heapArray[child];
            } else {
                break;
            }

        }

        heapArray[hole] = temp;
    }

}
