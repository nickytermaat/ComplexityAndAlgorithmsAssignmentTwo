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
    private int amountOfNumbersInside = 0;
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

    public int[] run() {
        //Run through the unsorted array and insert the stuff into the heapArray where it's needed
        // Build the heap
        int i = 0;
        heapArray = new int[maxHeapSize+1];
        while(i < unsorted.length){                                             //Loop through all unsorted numbers
            if(amountOfNumbersInside < maxHeapSize && i != unsorted.length-1){                            //Add number to heap
                heapArray[amountOfNumbersInside + 1] = unsorted[i];
                amountOfNumbersInside++;
                i++;
            } else {                                                            //Heap is full, now build it
                buildHeap();
                //TODO: Delete smallest element
                //TODO: Check if new element (unsorted[i]) should be placed in the heap or in the deadspace
                //TODO: Put element where it belongs
            }
        }

        while(maxHeapSize > 0){
            System.out.println("Deleting " + removeMinFromHeap());
            maxHeapSize--;
        }
        System.out.println("Done!");
        return heapArray;
    }

    private void buildHeap() {
        for (int i = maxHeapSize /2; i > 0; i--) {
            percolateDown(i);
        }
    }

    private int removeMinFromHeap(){
        int value = heapArray[1];
        heapArray[1] = heapArray[amountOfNumbersInside];
        amountOfNumbersInside-- ;
        percolateDown(1);
        return value;
    }

    public int removeMaxFromHeap(){
        int value = heapArray[amountOfNumbersInside];
        heapArray[amountOfNumbersInside] = -1;
        return value;
    }

    private void percolateDown(int index){
        //Find smallest of 2 children
        //Left child = index * 2
        //Right child = (index * 2 ) +1
        if((index * 2) + 1 <= amountOfNumbersInside){
            //RightChild exists. I'm checking with left.
            int leftChild = heapArray[(index * 2)];
            int rightChild = heapArray[(index * 2) + 1];
            if(leftChild <= rightChild){                        //The left child is smaller
                heapArray[index * 2] = heapArray[index];
                heapArray[index] = leftChild;
                percolateDown(index * 2);
            } else if (leftChild > rightChild){                 //The right child is smaller
                heapArray[index * 2 + 1] = heapArray[index];
                heapArray[index] = rightChild;
                percolateDown(index * 2 + 1);
            }
        } else if((index * 2) == amountOfNumbersInside && heapArray[index * 2] < heapArray[index]) {
            //The LeftChild is the last child in the tree, swap
            int leftChild = heapArray[(index * 2)];
            heapArray[index * 2] = heapArray[index];
            heapArray[index] = leftChild;
        }
    }

    private void insertInHeap(int number){
        int insertAt = amountOfNumbersInside + 1;
        if(insertAt <= maxHeapSize) {
            if (amountOfNumbersInside == 0) {
                heapArray[insertAt] = number;                              //0 can safely be set because it's the first element
                amountOfNumbersInside++;
            } else {                                                //Numbers already exist in the heap
                int parentIndex = insertAt / 2;
                int parent = heapArray[parentIndex];                //Integer division takes care of finding the proper parent
                if (number < parent) {                                //If number < parent, then percolateUp
                    percolateUp(number, insertAt);                             //We need to percolate up
                } else {                                            //I'm bigger than my parent, can place here
                    heapArray[insertAt] = number;
                }
                amountOfNumbersInside++;

            }
        }
    }
    private void percolateUp(int number, int index){
        if(amountOfNumbersInside <= maxHeapSize){
            int parent = heapArray[index / 2];                      //Swapping with parent
            heapArray[index / 2] = number;
            heapArray[index] = parent;
            index = index/2;                                        //Swapped up, so my index became that of my parent
            parent = heapArray[index / 2];
            if(number < parent){
                percolateUp(number, index);                         //Recursive call to keep percolating until i'm in position
            }
        }
    }
}
