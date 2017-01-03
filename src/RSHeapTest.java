import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Kris on 1/3/2017.
 */
public class RSHeapTest {
    RSHeap rsHeap;
    @Before
    public void initialize(){
        rsHeap = new RSHeap(6, new int[]{2, 3, 1, 4, 2, 3, 5, 6, 7, 8, 2, 1, 2, 4, 5});
    }

    @Test
    public void replacementSort() throws Exception {
        //To test:
        /*
        * Building a heap
        * Removing an item and
        *
        */

    }
}