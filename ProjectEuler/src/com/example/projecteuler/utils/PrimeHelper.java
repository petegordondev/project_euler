package com.example.projecteuler.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.BooleanSupplier;

public class PrimeHelper {
    public PrimeHelper() {
    }

    //TODO Proper prime checking method 'isPrime()' by using field arrays.
    public boolean isPrime(int n){
        if (n == 2) return true;
        int[] primes = findPrimesUnder(n);
        for (int i = 0; i <= primes.length/2; i++){
            if (n%primes[i] == 0) return false;
        }
        return true;
    }

    public int[] findPrimes(int n) // Returns an int array with the first n primes.
    {
        int primeCount = 0;

        // Going to use 'Sieve of Eratosthenes' to find primes.
        HashMap<Integer, Boolean> sieve = new HashMap<>();

        // Sieve has to be stepped, because we don't know where the nth prime will be found.
        int stepSize = 1000;
        int step = 0;

        // Going to store primes as they are found in an array.
        int[] primes = new int[n];

        // Find next prime using stepped 'Sieve of Eratosthenes'.
        while (primeCount < n){

            // Populate sieve for the next step (marking all values as 'true', essentially treating them all as primes before elimination).
            for (int j = step * stepSize; j < (step + 1) * stepSize; j++){
                sieve.put(j, true);
            }

            // If first step, set first two sieve values (0 and 1) to false; they ain't primes.
            if (step == 0){
                sieve.put(0, false);
                sieve.put(1, false);
            }

            // Now start eliminating non-primes for this step.
            // Get primes from array first and eliminate.
            for (int j = 0; j < primeCount; j++){
                // Get prime from array.
                int p = primes[j];
                // Set multiples of this prime to false (for the number of occurrences within this step).
                if (p != 0) {
                    for (int f = (step * stepSize) / p; f <= ((step + 1) * stepSize) / p; f++) {
                        // Gotta skip f = 1; that's out prime for goodness sake!
                        if (f != 1) {
                            int m = p * f;
                            sieve.put(m, false);
                        }
                    }
                }
            }

            // Find new primes.
            for (int j = step * stepSize; j < (step + 1) * stepSize; j++){
                // Check if value is a prime.
                if (sieve.get(j)) {
                    // Set multiples of this prime to false (for the number of occurrences within this step).
                    if (j != 0) {
                        for (int f = (step * stepSize) / j; f <= ((step + 1) * stepSize) / j; f++) {
                            // Gotta skip f = 1; that's our prime for goodness sake!
                            if (f != 1) {
                                int m = j * f;
                                sieve.put(m, false);
                            }
                        }
                        // Newly found prime into array from sieve.
                        if (primeCount == n){
                            break;
                        } else {
                            primes[primeCount] = j;
                            primeCount++;
                        }
                    }
                }
            }
            step++;
            sieve.clear();
        }
        return primes;
    }

    public int[] findPrimesUnder(int target) // Finds all primes lower than target.
    {
        HashMap<Integer, Boolean> sieve = new HashMap<>();
        List<Integer> l = new ArrayList<>();

        // Sieve has to be stepped, because target could be very large and consume memory.
        int stepSize = 1000;
        int step = 0;

        while (step * stepSize < target)
        {
            // Populate sieve for the next step (marking all values as 'true', essentially treating them all as primes before elimination).
            for (int i = step * stepSize; i < (step + 1) * stepSize; i++){
                sieve.put(i, true);
            }

            // If first step, set first two sieve values (0 and 1) to false; they ain't primes.
            if (step == 0){
                sieve.put(0, false);
                sieve.put(1, false);
            }

            // Now start eliminating non-primes for this step.
            // Get primes from array first and eliminate.
            for (Integer aL : l) {
                // Get prime from array.
                int p = aL;
                // Set multiples of this prime to false (for the number of occurrences within this step).
                for (int f = (step * stepSize) / p; f <= ((step + 1) * stepSize) / p; f++) {
                    // Gotta skip f = 1; that's out prime for goodness sake!
                    if (f != 1) {
                        int m = p * f;
                        sieve.put(m, false);
                    }
                }
            }

            // Find new primes.
            for (int j = step * stepSize; j < (step + 1) * stepSize; j++){
                // Check if value is a prime.
                if (sieve.get(j)) {
                    // Set multiples of this prime to false (for the number of occurrences within this step).
                    if (j != 0) {
                        for (int f = (step * stepSize) / j; f <= ((step + 1) * stepSize) / j; f++) {
                            // Gotta skip f = 1; that's our prime for goodness sake!
                            if (f != 1) {
                                int m = j * f;
                                sieve.put(m, false);
                            }
                        }
                        // Newly found prime into array from sieve.
                        if (j >= target) break;
                        else l.add(j);
                    }
                }
            }
            step++;
            sieve.clear();
        }

        // Convert arrayList to simple array.
        int n = l.size();
        int[] primes = new int[n];
        for (int i = 0; i < n; i++)
        {
            primes[i] = l.get(i);
        }
        return primes;
    }
}
