package ComplexityAssignment2.test;

import ComplexityAssignment2.RSHeap;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertTrue;

/**
 * Created by Kris on 1/3/2017.
 */
public class RSHeapTest {
    RSHeap rsHeap;

    @Test
    public void replacementSort() throws Exception {
        //To ComplexityAssignment2.test:
        //This test runs the replacementsort algorithm 200 times with 800 numbers and checks if the order is valid
        ArrayList<ArrayList<Integer>> output;
        for (int i = 0; i < 200; i++) {
            Random rand = new Random();
            int[] numbers = new int[80];
            for (int j = 0; j < 80; j++) {
                numbers[j] = rand.nextInt(2000) -100;
            }
            RSHeap heap = new RSHeap(8, numbers);
            output = heap.replacementSort();
            for (int j = 0; j < output.size(); j++) {
                assertTrue("List isn't ordered!", isOrdered(output.get(j)));
            }
            assertTrue("Output doesn't contain the same numbers as input" , containsAllNumbers(output, numbers));
        }
    }

    public boolean containsAllNumbers(ArrayList<ArrayList<Integer>> ordered, int[] unordered){
        //To ComplexityAssignment2.test:
        //This method cheks if the ordered array contains all the same numbers as the
        int[] orderedArray = new int[unordered.length];
        int index = 0;
        for (int i = 0; i < ordered.size(); i++) {
            for (int o = 0; o < ordered.get(i).size(); o++) {
                orderedArray[index] = ordered.get(i).get(o);
                index++;
            }
        }
        if(orderedArray.length != unordered.length)
            return false;

        Arrays.sort(orderedArray);
        Arrays.sort(unordered);
        for (int i = 0; i < unordered.length; i++) {
            if(unordered[i] != orderedArray[i])
                return false;
        }
        return true;
    }

    public boolean isOrdered(ArrayList<Integer> numbers){
        for (int i = 0; i < numbers.size(); i++) {
            if(numbers.size() > i +1 && numbers.get(i) > numbers.get(i + 1)){
                return false;
            }
        }
        return true;
    }
}