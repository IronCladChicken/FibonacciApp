package uk.co.opses.hello;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FibonacciAlgorithmTest extends TestCase {

    public void testRun() {
        FibonacciAlgorithmTest_0();
        FibonacciAlgorithmTest_1();
        FibonacciAlgorithmTest_5();
    }

    //0 - 0
    @Test
    public void FibonacciAlgorithmTest_0() {
        List<Integer> testData = new ArrayList<Integer>(){{add(0);}};
        assertEquals(FibonacciAlgorithm.Run(0), testData );
    }

    //1 - 0 1
    @Test
    public void FibonacciAlgorithmTest_1() {
        List<Integer> testData = new ArrayList<Integer>(){{add(0); add(1);}};
        assertEquals(FibonacciAlgorithm.Run(1), testData );
    }

    //5 - 0 1 1 2 3 5
    @Test
    public void FibonacciAlgorithmTest_5() {
        List<Integer> testData = new ArrayList<Integer>(){{add(0); add(1); add(1); add(2); add(3); add(5);}};
        assertEquals(FibonacciAlgorithm.Run(5), testData );
        //assertEquals(FibonacciAlgorithm.Run(5), new ArrayList<Integer>(){{add(0); add(1); add(1); add(2); add(3);}} );
    }
}