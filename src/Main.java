import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Nicky on 01/12/2016.
 */
public class Main {

    public static void main(String[] args) {

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
