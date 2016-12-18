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

    public int[] run() {
        //Run through the unsorted array and insert the stuff into the heapArray where it's needed

        // Build the heap
        int i = 0;
        heapArray = new int[maxHeapSize+1];
        while(i < unsorted.length){                                              //Loop through all unsorted numbers
            if(amountOfNumbersInside < maxHeapSize && i != (unsorted.length)){     //There's still room in the heap
                // && we're not at the last element
                insertInHeap(unsorted[i]);
                i++;
            } else {
                //Now... We're at a full heap and need to remove one.
                int minVal = removeMinFromHeap();
                System.out.println("Writing minval " + minVal);
                if(unsorted[i] > peekAtSmallest()){
                    //Next incoming value is bigger than our smallest, so all is good
                    insertInHeap(unsorted[i]);
                    i++;
                } else {
                    maxHeapSize--;
                    deadspace++;
                    heapArray[maxHeapSize + 1] = unsorted[i]; //Insert into deadspace
                    i++;
                }
            }
            if(maxHeapSize == 0){
                //The deadspace is now full. Time to sort it as a heap.
                //Build a new heap in the deadSpace
                maxHeapSize = deadspace;
                deadspace = 0;
                buildHeap();
            }
        }
        while(0 < amountOfNumbersInside){
            System.out.println("Removed" + removeMinFromHeap());
        }
        System.out.println("Done");
        pw.flush();
        return heapArray;
    }

    private int peekAtSmallest(){
        return heapArray[1];
    }
    private void buildHeap() {
        for (int i = maxHeapSize/2; i > 0; i--) {
            percolateDown(i);
        }
        System.out.println("Finished building");
    }

    public int removeMinFromHeap(){
        int value = heapArray[1];
        percolateDown(1);
        amountOfNumbersInside--;
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
        if((index * 2) + 1 < amountOfNumbersInside){
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
        } else if((index * 2) == amountOfNumbersInside) {
            //The LeftChild is the last child in the tree, swap
            int leftChild = heapArray[(index * 2)];
            heapArray[index * 2] = heapArray[index];
            heapArray[index] = leftChild;
        } else {
            //I'm at the last node. Remove
            //TODO: This should NOT be here. Percolating down should stop now.
//            heapArray[index] = removeMaxFromHeap();
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
