/**
 * Created by Kris on 12/7/2016.
 */
public class RSHeap {
    private int heapSize = 0;
    private int[] heapArray;
    private int[] unsorted;

    public RSHeap(int heapSize, int[] unsorted) {
        this.heapSize = heapSize;
        this.unsorted = unsorted;
    }

    public int[] run(){
        //Run through the unsorted array and insert the stuff into the heapArray where it's needed

        // Build the heap
        heapArray = new int[heapSize];
        int amount = 0;
        for (int i = 0; i < unsorted.length; i++) {
            if(i <= heapSize){                                      //There's still room in the heap
                heapArray[i] = unsorted[i];
            } else {                                                //The heap is full. Time to sort it!
                buildHeap(unsorted);
                
            }
        }
        return heapArray;
    }

    private void buildHeap(int[] unsorted) {
    }


}
