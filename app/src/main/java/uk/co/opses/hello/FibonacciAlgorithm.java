package uk.co.opses.hello;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FibonacciAlgorithm {

    private static List<Integer> calculateTheNthValue(int maxNumber){
        // Set it to the number of elements you want in the Fibonacci Series
        int previousNumber = 0;
        int nextNumber = 1;

        List<Integer> resultsList = new ArrayList<Integer>();

        for (int i = 1; i <= maxNumber; ++i)
        {
            resultsList.add(previousNumber);

            /* On each iteration, we are assigning second number
             * to the first number and assigning the sum of last two
             * numbers to the second number
             */
            int sum = previousNumber + nextNumber;
            previousNumber = nextNumber;
            nextNumber = sum;
        }

        resultsList.add(previousNumber);

        return resultsList;
    }

    private static List<Integer> calculateTheRankNForTheValueXIsLessThanN(int maxNumber){
        // Set it to the number of elements you want in the Fibonacci Series
        int previousNumber = 0;
        int nextNumber = 1;

        List<Integer> resultsList = new ArrayList<Integer>();

        do
        {
            resultsList.add(previousNumber);

            /* On each iteration, we are assigning second number
             * to the first number and assigning the sum of last two
             * numbers to the second number
             */
            int sum = previousNumber + nextNumber;
            previousNumber = nextNumber;
            nextNumber = sum;
        } while(previousNumber<=maxNumber);

        resultsList.add(previousNumber);

        return resultsList;
    }

    public static List<Integer> Run(int inMax){
        List<Integer> resultsList = calculateTheNthValue(inMax);
        //List<Integer> resultsList = calculateTheRankNForTheValueXIsLessThanN(inMax);
        return resultsList;
    }
    
}
