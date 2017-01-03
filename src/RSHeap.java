import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kris on 12/7/2016.
 */
public class RSHeap {
    private int maxHeapSize = 0;
    private int[] heapArray;
    private int[] unsorted;
    private int deadspace = 0;
    private int amountOfNumbersInside = 0;
    private int runs;
    private ArrayList<ArrayList<Integer>> output;

    public RSHeap(int heapSize, int[] unsorted) throws IOException {
        //HeapSize must be smaller than unsorted length
        if(heapSize <= unsorted.length){
            this.maxHeapSize = heapSize;
        } else {
            maxHeapSize = unsorted.length;
        }
        this.output = new ArrayList<>();
        this.unsorted = unsorted;
        this.runs = 0;
    }

    public ArrayList<ArrayList<Integer>> run() {
        //Run through the unsorted array and insert the stuff into the heapArray where it's needed
        // Build the heap
        int i = 0;
        output.add(new ArrayList<>());
        heapArray = new int[maxHeapSize+1];
        while(i <= unsorted.length){                                             //Loop through all unsorted numbers
            if(maxHeapSize == 0){               //IF the deadspace is full, use it to rebuild a heap
                System.out.println("Rebuilding from deadspace");
                maxHeapSize = deadspace;        //Reset maxHeapSize
                deadspace = 0;                  //Reset deadspace size
                runs++;                         //Keep track of amount of runs
                output.add(new ArrayList<>());
                //What to do with amountOfNumbersInside?
                amountOfNumbersInside = maxHeapSize; //Indicate that the heap is full
                buildHeap();
            }
            /*INITIAL RUN*/
            if(amountOfNumbersInside < maxHeapSize //There's still room
                    && i != unsorted.length-1){     //Check if we're at the last element
                // Add number to heap
                heapArray[amountOfNumbersInside + 1] = unsorted[i];
                amountOfNumbersInside++;
                i++;
            }
            /*END OF INITIAL RUN, NOW ReplacementSelecting*/
            else if(i != unsorted.length){ //There are unsorted numbers left, so keep adding into the heap
                //Heap is full, now build it
                buildHeap();
                output.get(runs).add(removeMinFromHeap());                        //Removed smallest element
                //TODO: Check if new element (unsorted[i]) should be placed in the heap or in the deadspace
                int smallest = heapArray[1];
                int nextNumber = unsorted[i];
                i++;
                if (amountOfNumbersInside > 0 && nextNumber >= smallest ) {
                    //TODO: Insert into heap
                    heapArray[amountOfNumbersInside + 1] = nextNumber;
                    amountOfNumbersInside++;
                } else {
                    //TODO: Insert into deadspace
                    heapArray[amountOfNumbersInside + 1] = nextNumber;
                    deadspace++;
                    maxHeapSize--;
                }
            } /*END OF ReplacementSelecting, cleaning heap */
            else { //There are no unsorted numbers left, so delete everything from the heap.
                //TODO: First, clean the rest of the heap
                //Then, build a new heap from leftover deadspace
                //Then clean heap again
                //TODO: Make for-loop that loops through what's left
                System.out.println("Cleaning heap..");
                for (int j = 0; j < maxHeapSize; j++) {            //Cleaning the heap..
                    output.get(runs).add(removeMinFromHeap());
                }

                System.out.println("Done cleaning, now clean deadspace!");

                //Heap is empty
                for (int j = 0; j < maxHeapSize; j++) {
                    int temp = heapArray[(deadspace + maxHeapSize) - j];
                    heapArray[j + 1] = temp;
                }
                maxHeapSize = deadspace;
                buildHeap();
                runs++;
                output.add(new ArrayList<>());
                amountOfNumbersInside = deadspace;
                for (int j = 0; j < maxHeapSize; j++) {            //Cleaning the heap..
                    output.get(runs).add(removeMinFromHeap());
                }
                //Amountofnumbersinside is nu 0
                //MaxHeapSize mag nu
                i++; //This is needed to stop the loop from running
            }
        }
        System.out.println("Done in " + runs + " runs!" );
        return output;
    }

    private void buildHeap() {
        for (int i = maxHeapSize /2; i > 0; i--) {
            percolateDown(i);
        }
    }

    private int removeMinFromHeap(){
        int value = heapArray[1];                           //Store minValue in temporary value
        heapArray[1] = heapArray[amountOfNumbersInside];    //Set minValue to highest value in the heap
        amountOfNumbersInside-- ;                           //Reduce numbersInside by one
        percolateDown(1);                                   //Call recursive function percolateDown to set the new value on the right position
        return value;                                       //Return the value that has been deleted
    }

    private void percolateDown(int index){
        //Find smallest of 2 children
        //Left child = index * 2
        //Right child = (index * 2 ) +1
        if((index * 2) + 1 <= amountOfNumbersInside){
            //RightChild exists. I'm checking with left.
            int leftChildValue = heapArray[(index * 2)];
            int rightChildValue = heapArray[(index * 2) + 1];
            int currentValue = heapArray[index];
            int smallestValue = Math.min(leftChildValue, rightChildValue);
            if(currentValue > smallestValue) {
                //TODO: Check with which child to swap.
                if(leftChildValue <= rightChildValue){
                    heapArray[(index * 2)] = currentValue;
                    heapArray[index] = leftChildValue;
                    percolateDown((index * 2));
                } else if(leftChildValue > rightChildValue){
                    heapArray[(index * 2) + 1] = currentValue;
                    heapArray[index] = rightChildValue;
                    percolateDown((index * 2) + 1);
                }
            }
        } else if((index * 2) == amountOfNumbersInside && heapArray[index * 2] < heapArray[index]) {
            //The LeftChild is the last child in the tree, swap
            int leftChild = heapArray[(index * 2)];
            heapArray[index * 2] = heapArray[index];
            heapArray[index] = leftChild;
            //Don't need to percolate again, because this is a last child.
        }
    }
}
