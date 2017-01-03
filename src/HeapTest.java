import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kris on 1/3/2017.
 */
public class HeapTest {
    Heap heap;
    private final int heap_size = 6;
    @Before
    public void initialize(){
        heap = new Heap(heap_size);
    }

    @Test
    public void addNumberToHeap() throws Exception {
        heap.addNumberToHeap(-1);
        heap.addNumberToHeap(9);
        heap.addNumberToHeap(110);
        heap.addNumberToHeap(11);
        heap.addNumberToHeap(89);
        assertTrue(heap.isOrdered());
    }
    @Test
    public void isFull() throws Exception {
        heap.addNumberToHeap(12);
        heap.addNumberToHeap(3);
        heap.addNumberToHeap(-1);
        heap.addNumberToHeap(5);
        heap.addNumberToHeap(21);
        assertFalse(heap.isFull());
        heap.addNumberToHeap(9);
        assertTrue(heap.isFull());
        heap.removeMinFromHeap();
        assertFalse(heap.isFull());
    }

    @Test
    public void buildHeap() throws Exception {
        //Buildheap is only used when building from the deadspace
        heap.addNumberToDeadspace(2);
        heap.addNumberToDeadspace(4);
        heap.addNumberToDeadspace(41);
        heap.addNumberToDeadspace(-9);
        heap.addNumberToDeadspace(-3);
        heap.addNumberToDeadspace(20);
        heap.setMaxHeapSize(heap.getDeadspace());
        heap.setDeadspace(0);
        heap.setFull();

        heap.buildHeap();
        assertTrue(heap.isOrdered());
        assertTrue(heap.isFull());
    }

    @Test
    public void removeMinFromHeap() throws Exception {
        fillHeap();
        heap.removeMinFromHeap();
        assertFalse(heap.isFull());
        assertTrue(heap.isOrdered());
    }

    @Test
    public void addNumberToDeadspace() throws Exception {
        fillHeap();
        heap.removeMinFromHeap();
        heap.addNumberToDeadspace(1);
        assertTrue(heap.getMaxHeapSize() == 5);
        assertTrue(heap.getDeadspace() == 1);
        assertTrue(heap.isFull());
        assertTrue(heap.isOrdered());
    }

    @Test
    public void getSmallest() throws Exception {
        fillHeap();
        assertTrue(heap.getSmallest() == -1);
    }

    @Test
    public void getValue() throws Exception {
        fillHeap();
        assertTrue(heap.getValue(1) ==-1);
    }

    @Test
    public void getMaxHeapSize() throws Exception {
        assertTrue(heap.getMaxHeapSize() == heap_size);
        heap.removeMinFromHeap();
        heap.addNumberToDeadspace(2);
        assertTrue(heap.getMaxHeapSize() == (heap_size - 1));
    }

    @Test
    public void setFull() throws Exception {
        heap.setFull();
        assertTrue(heap.isFull());
    }

    private void fillHeap(){
        heap.addNumberToHeap(12);
        heap.addNumberToHeap(3);
        heap.addNumberToHeap(-1);
        heap.addNumberToHeap(5);
        heap.addNumberToHeap(21);
        heap.addNumberToHeap(1);
    }
}