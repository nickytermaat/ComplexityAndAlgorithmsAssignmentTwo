package ComplexityAssignment2.test;

import ComplexityAssignment2.RSHeap;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Kris on 1/3/2017.
 */
public class RSHeapTest {
    RSHeap rsHeap;
    @Before
    public void initialize(){
        rsHeap = new RSHeap(6, new int[]{3, 2, 4, 6, 7, 9, 1, 2,3 , 5, 7, 8, 3, 4, 6, 8, 3, 54, 5, 2, 3, 4, });
    }

    @Test
    public void replacementSort() throws Exception {
        //To ComplexityAssignment2.test:
        /*
        * Building a heap
        * Removing an
        *
        */
        rsHeap.replacementSort();
    }
}