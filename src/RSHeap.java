import java.io.IOException;
import java.util.ArrayList;

/**
 * The RSHeap class is used for Replacement Selecting numbers
 * It uses the Heap class for the heap operations
 */
public class RSHeap {
    private int[] unsorted;
    private int runs;
    private ArrayList<ArrayList<Integer>> output;
    private Heap heap;

    RSHeap(int heapSize, int[] unsorted){
        //TODO: Assert
        int maxHeapSize;                                                            //HeapSize must be smaller than unsorted length
        if(heapSize <= unsorted.length){
            maxHeapSize = heapSize;
        } else {
            maxHeapSize = unsorted.length;
        }
        this.heap = new Heap(maxHeapSize);
        this.output = new ArrayList<>();
        this.unsorted = unsorted;
        this.runs = 0;
    }

    /**
     * This function does the actual replacement sorting.
     * It takes all unsorted numbers, puts them in the heap and then starts working on the RS
     * @return an arraylist of lists. Each list represents a run and it contains the numbers of that run
     */
    ArrayList<ArrayList<Integer>> replacementSort() {
        int i = 0;
        output.add(new ArrayList<>());
        while(i <= unsorted.length){                                                //Loop through all unsorted numbers
            if(heap.getMaxHeapSize() == 0){                                         //IF the deadspace is full, use it to rebuild a heap
                buildFromDeadspace();
            }
            if( !heap.isFull()                                                      //There's still room in the HeapArray
                    && i != unsorted.length-1){                                     //Check if we're at the last element
                heap.addNumberToHeap(unsorted[i]);
                i++;
            }
            else if(i != unsorted.length){                                          //There are unsorted numbers left
                output.get(runs).add(heap.removeMinFromHeap());                     //Removed smallest element
                int nextNumber = unsorted[i];
                i++;
                if (heap.getAmountOfNumbersInside() > 0 && nextNumber >= heap.getSmallest() ) {
                    heap.addNumberToHeap(nextNumber);
                } else {
                    heap.addNumberToDeadspace(nextNumber);
                }
            } else {                                                                //There are no unsorted numbers left, so delete everything from the heap.
                writeHeapToOutput();
                for (int j = 0; j < heap.getMaxHeapSize(); j++) {                   //Write deadspace to start of the heap
                    int temp = heap.getValue((heap.getDeadspace() + heap.getMaxHeapSize()) - j);
                    heap.setValue((j+1), temp);
                }

                heap.setMaxHeapSize(heap.getDeadspace());                           //Preparing the heap for final run
                heap.buildHeap();
                if(heap.getMaxHeapSize() > 0){
                    newRun();
                    heap.setFull();
                    writeHeapToOutput();
                }
                i++;                                                                //Terminate the loop
            }
        }
        return output;
    }

    /**
     * This function increases the amount of runs by 1 and makes a new arraylist for this run
     */
    private void newRun() {
        runs++;
        output.add(new ArrayList<>());
    }

    /**
     * This function is used to remove all the numbers that are currently in the HeapArray and write them to outpuy
     * It clears the heap
     */
    private void writeHeapToOutput() {
        for (int j = 0; j < heap.getMaxHeapSize(); j++) {                           //Cleaning the heap..
            output.get(runs).add(heap.removeMinFromHeap());
        }
    }

    /**
     * This function is called when the array is filled with deadspace.
     * It takes all numbers in the deadspace and puts them in a heap.
     */
    private void buildFromDeadspace() {
        heap.setMaxHeapSize(heap.getDeadspace());                                    //Reset maxHeapSize
        heap.setDeadspace(0);                                                        //Reset deadspace size
        newRun();
        heap.setFull();                                                              //Indicate that the heap is full
        heap.buildHeap();
    }
}
