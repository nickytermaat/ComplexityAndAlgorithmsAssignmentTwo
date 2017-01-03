import java.io.IOException;
import java.util.ArrayList;

/**
 * TODO: Documentation
 */
public class RSHeap {
    private int[] unsorted;
    private int runs;
    private ArrayList<ArrayList<Integer>> output;
    private Heap heap;

    RSHeap(int heapSize, int[] unsorted) throws IOException {
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
     * TODO: Documentation
     * @return
     */
    ArrayList<ArrayList<Integer>> run() {
        int i = 0;
        output.add(new ArrayList<>());
        while(i <= unsorted.length){                                                //Loop through all unsorted numbers
            if(heap.getMaxHeapSize() == 0){                                         //IF the deadspace is full, use it to rebuild a heap
                buildFromDeadspace();
            }
            if( !heap.isFull()                                                      //There's still room in the heaparray
                    && i != unsorted.length-1){                                     //Check if we're at the last element
                heap.addNumberToHeap(unsorted[i]);
                i++;
            }
            else if(i != unsorted.length){                                          //There are unsorted numbers left
                heap.buildHeap();                                                   //Build the heap
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
                newRun();
                heap.setFull();
                writeHeapToOutput();
                i++;                                                                //Terminate the loop
            }
        }
        System.out.println("Done in " + runs + " runs!" );
        return output;
    }

    /**
     * TODO: Documentation
     */
    private void newRun() {
        runs++;
        output.add(new ArrayList<>());
    }

    /**
     * TODO: Documentation
     */
    private void writeHeapToOutput() {
        for (int j = 0; j < heap.getMaxHeapSize(); j++) {                           //Cleaning the heap..
            output.get(runs).add(heap.removeMinFromHeap());
        }
    }

    /**
     * TODO: Documentation
     */
    private void buildFromDeadspace() {
        System.out.println("Rebuilding from deadspace");
        heap.setMaxHeapSize(heap.getDeadspace());                                    //Reset maxHeapSize
        heap.setDeadspace(0);                                                        //Reset deadspace size
        newRun();
        heap.setFull();                                                              //Indicate that the heap is full
        heap.buildHeap();
    }

}
