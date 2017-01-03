package ComplexityAssignment2;

/**
 * The datastructure which handles everything that
 */
public class Heap {
    private int maxHeapSize = 0;
    private int[] heapArray;
    private int amountOfNumbersInside = 0;
    private int deadspace = 0;

    public Heap(int maxHeapSize){
        this.maxHeapSize = maxHeapSize;
        assert maxHeapSize >= 0 : "Invalid maximum heap size";

        this.heapArray = new int[maxHeapSize + 1];          //+1 because using a 1-based index is easier for finding children
        assert heapArray.length != maxHeapSize : "Invalid array length";
    }

    /**
     * Apply percolateDown repeatedly, starting at the last parent
     * O(1/2 N)
     */
    public void buildHeap() {
        for (int i = maxHeapSize /2; i > 0; i--) {
            percolateDown(i);
        }
    }

    /**
     * Remove the smallest element from the heap, found at the root of the heap
     * O(log N)
     */
    public int removeMinFromHeap(){
        int value = heapArray[1];                           //Store minValue in temporary value
        heapArray[1] = heapArray[amountOfNumbersInside];    //Set minValue to highest value in the heap
        amountOfNumbersInside-- ;                           //Reduce numbersInside by one
        percolateDown(1);                                   //Call recursive function percolateDown to set the new value on the right position
        return value;                                       //Return the value that has been deleted
    }

    /**
     * The value of each child node is greater than or equal to the value of its parent, with the minimum value at the root
     * If not, percolateDown so the smallest child is switched with the current parent node.
     * O(1)
     */
    private void percolateDown(int index){
        assert index >= 0 : "Invalid index";
        if((index * 2) + 1 <= amountOfNumbersInside){
            //RightChild exists. I'm checking with left.
            int leftChildValue = heapArray[(index * 2)];
            int rightChildValue = heapArray[(index * 2) + 1];
            int currentValue = heapArray[index];
            int smallestValue = Math.min(leftChildValue, rightChildValue);
            if(currentValue > smallestValue) {
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

    private void percolateUp(int index){
        assert index >= 0 : "Invalid index";
        int parent = heapArray[index/2];
        if(parent > heapArray[index] && index != 1){
            heapArray[index / 2] = heapArray[index];
            heapArray[index] = parent;
            percolateUp(index / 2);
        }
    }

    /**
     * The amount of numbers that is inside the heaparray is equal to the maximum heap size.
     */
    public boolean isFull(){
        assert amountOfNumbersInside >= 0 : "Invalid amount of numbers inside array";
        return amountOfNumbersInside == maxHeapSize;
    }

    /**
     * If there is room in the heaparray add a number into the heaparray. Increase the amount of numbers that is in the heaparray.
     */
    public void addNumberToHeap(int number){
        if(!isFull()){
            heapArray[amountOfNumbersInside + 1] = number;
            amountOfNumbersInside++;
            percolateUp(amountOfNumbersInside);
        }
    }

    /**
     * Add the number to the deadspace, decrease the maximum heapsize and increase the deadspace
     */
    public void addNumberToDeadspace(int number){
        maxHeapSize--;
        deadspace++;
        heapArray[maxHeapSize + 1] = number;
    }

    public int getSmallest(){
        return heapArray[1];
    }

    public int getValue(int index){
        assert index >= 0 : "Invalid index";
        return heapArray[index];
    }

    public int getDeadspace() {
        return deadspace;
    }

    /**
     * Transform the values in deadspace to values in heaparray
     * To create a heaparray with the deadspace values which then can be sorted again
     */
    void setValue(int index, int value){
        assert index >= 0 : "Invalid index";
        heapArray[index] = value;
    }

    public void setDeadspace(int deadspace) {
        assert deadspace >= 0 : "Invalid deadspace size";
        this.deadspace = deadspace;
    }

    public int getMaxHeapSize() {
        return maxHeapSize;
    }

    /**
     * Set the amount of numbers inside to the maximum heapsize (which is the last size of the deadspace)
     */
    public void setFull() {
        this.amountOfNumbersInside = this.maxHeapSize;
    }

    int getAmountOfNumbersInside() {
        return amountOfNumbersInside;
    }

    public void setMaxHeapSize(int maxHeapSize) {
        assert maxHeapSize >= 0 : "Invalid maximum heap size";
        this.maxHeapSize = maxHeapSize;
    }

    public boolean isOrdered(){
        for (int i = 1; i < amountOfNumbersInside; i++) {
            if((i * 2 ) + 1 <= amountOfNumbersInside) {
                if (heapArray[i] > heapArray[(i * 2)] || heapArray[i] > heapArray[(i * 2) + 1]) {
                    return false;
                }
            } else if (i*2 <= amountOfNumbersInside){
                if (heapArray[i] > heapArray[(i * 2)]) {
                    return false;
                }
            }
        }
        return true;
    }
    public String toString(){
        String toReturn = "";
        for (int i = 0; i < heapArray.length; i++) {
            toReturn += heapArray[i] + "\t";
        }
        return toReturn;
    }
}
