/**
 * TODO: Explain why this class is used
 * Created by Kris on 1/3/2017.
 */
public class Heap {
    private int maxHeapSize = 0;
    private int[] heapArray;
    private int amountOfNumbersInside = 0;
    private int deadspace = 0;

    Heap(int maxHeapSize){
        this.maxHeapSize = maxHeapSize;
        this.heapArray = new int[maxHeapSize + 1];          //+1 because using a 1-based index is easier for finding children
    }

    /**
     * TODO: Documentation
     */
    void buildHeap() {
        for (int i = maxHeapSize /2; i > 0; i--) {
            percolateDown(i);
        }
    }

    /**
     * TODO: Documentation
     */
    int removeMinFromHeap(){
        int value = heapArray[1];                           //Store minValue in temporary value
        heapArray[1] = heapArray[amountOfNumbersInside];    //Set minValue to highest value in the heap
        amountOfNumbersInside-- ;                           //Reduce numbersInside by one
        percolateDown(1);                                   //Call recursive function percolateDown to set the new value on the right position
        return value;                                       //Return the value that has been deleted
    }

    /**
     * TODO: Documentation
     */
    private void percolateDown(int index){
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

    /**
     * TODO: Documentation
     */
    boolean isFull(){
        return amountOfNumbersInside == maxHeapSize;
    }

    /**
     * TODO: Documentation
     */
    void addNumberToHeap(int number){
        if(!isFull()){
            heapArray[amountOfNumbersInside + 1] = number;
            amountOfNumbersInside++;
        }
    }

    /**
     * TODO: Documentation
     */
    void addNumberToDeadspace(int number){
        heapArray[amountOfNumbersInside + 1] = number;
        maxHeapSize--;
        deadspace++;
    }

    int getSmallest(){
        return heapArray[1];
    }

    int getValue(int index){
        //TODO: Error handling
        return heapArray[index];
    }

    int getDeadspace() {
        return deadspace;
    }

    /**
     * TODO: Documentation
     * Explain why it's used
     */
    void setValue(int index, int value){
        heapArray[index] = value;
    }

    void setDeadspace(int deadspace) {
        this.deadspace = deadspace;
    }

    int getMaxHeapSize() {
        return maxHeapSize;
    }

    /**
     * TODO: Documentation
     */
    void setFull() {
        this.amountOfNumbersInside = this.maxHeapSize;
    }

    int getAmountOfNumbersInside() {
        return amountOfNumbersInside;
    }

    void setMaxHeapSize(int maxHeapSize) {
        this.maxHeapSize = maxHeapSize;
    }

}
