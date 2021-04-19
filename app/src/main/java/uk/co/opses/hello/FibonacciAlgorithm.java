package uk.co.opses.hello;

import android.util.Log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FibonacciAlgorithm {

    //Note: Better example can be found here: https://www.nayuki.io/res/fast-fibonacci-algorithms/FastFibonacci.java
    private static List<BigInteger> calculateTheNthValue(int maxNumber){
        // Set it to the number of elements you want in the Fibonacci Series
        BigInteger previousNumber = BigInteger.valueOf(0);
        BigInteger nextNumber = BigInteger.valueOf(1);
        BigInteger sum;
        List<BigInteger> resultsList = new ArrayList<>();

        for (int i = 1; i <= maxNumber; ++i)
        {
            resultsList.add(previousNumber);

            /* On each iteration, we are assigning second number
             * to the first number and assigning the sum of last two
             * numbers to the second number
             */
            sum = previousNumber.add(nextNumber);
            previousNumber = nextNumber;
            nextNumber = sum;
        }

        resultsList.add(previousNumber);

        return resultsList;
    }

    private static List<BigInteger> calculateTheRankNForTheValueXIsLessThanN(BigInteger maxNumber){
        // Set it to the number of elements you want in the Fibonacci Series
        BigInteger previousNumber = BigInteger.valueOf(0);
        BigInteger nextNumber = BigInteger.valueOf(1);
        BigInteger sum;

        List<BigInteger> resultsList = new ArrayList<>();

        do
        {
            resultsList.add(previousNumber);

            /* On each iteration, we are assigning second number
             * to the first number and assigning the sum of last two
             * numbers to the second number
             */
            sum = previousNumber.add(nextNumber);
            previousNumber = nextNumber;
            nextNumber = sum;
        } while(previousNumber.compareTo(maxNumber) == -1);

        resultsList.add(previousNumber);

        return resultsList;
    }

    public static List<BigInteger> Run(int inMax){
        return calculateTheNthValue(inMax);
        //return calculateTheRankNForTheValueXIsLessThanN(BigInteger.valueOf(inMax));
    }
    
}
