/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENSE_496AE_Assignment_4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 *
 * @author Jeremy
 */
public class Assignment4 {
    
    private static long[] timeBetweenPrimes = new long[200];
    
    public static void main(String[] args){
        
        try {
            int[] primeNumbers = new int[200];
            int number;
            int counter = 0;
            long bigSeed = 2147483647;
            GregorianCalendar timeBefore = new GregorianCalendar();
            GregorianCalendar timeAfter = new GregorianCalendar();
            long before;
            long after;

            // creates txt file
            File fileWithPrimes = new File("PrimeNumbers.txt");

            // check if file generates properly
            if(!fileWithPrimes.exists()){
                fileWithPrimes.createNewFile();
            }

            FileWriter write = new FileWriter(fileWithPrimes);

            // generate random number with a seed value
            Random randomNumber = new Random(bigSeed); 

            /*******************************************************************
             * First Requirement
            *******************************************************************/
            
            write.write("First Requirement\n");
            
            for (int i = 0; i < primeNumbers.length; i++){

                // create a random number and check if it is prime
                // if so, then add it to the prime number array
                number = randomNumber.nextInt(1000) + 1;
                
                if (checkIfPrime(number)){
                    primeNumbers[i] = number;
                    write.write(primeNumbers[i] + " ");  
                    counter++;
                    if(counter == 10){
                        write.write("\n");
                        counter = 0;
                    }
                }
                else
                    i--;
            }
            
            /*******************************************************************
             * Second Requirement
            *******************************************************************/
            
            write.write("\n");
            write.write("Second Requirement\n");
            
            number = 0;
            
            for (int i = 0; i < primeNumbers.length; i++){

                
                
                if (checkIfPrime(number)){
                    
                    // check the time for identifying the prime
                    before = timeBefore.getTimeInMillis();
                    checkIfPrime(number);
                    after = timeAfter.getTimeInMillis();
                    timeBetweenPrimes(number, i, before, after);
                    
                    // write the prime number to txt file
                    primeNumbers[i] = number;
                    write.write(primeNumbers[i] + " ");  
                    counter++;
                    if(counter == 10){
                        write.write("\n");
                        counter = 0;
                    }
                }
                else
                    i--;
                
                //System.out.printf("number: " + number + "\n");
                number++;
            }
            
            write.write("\n");
            write.write("Times between Primes\n");
            
            for(int i = 0; i < primeNumbers.length; i++){
                
                write.write("time for prime " + primeNumbers[i] + 
                        ": " + timeBetweenPrimes[i] + "\n");
            }
            
            write.close();
        }
        catch (IOException e){
            e.printStackTrace();
        } 
    }
    
    public static boolean checkIfPrime(int number){
        
        boolean isPrime = true;
        
        for (int i = 2; i <= number/2; ++i){
            
            // if not prime
            if (number % i == 0){
                isPrime = false;
                break;
            }
        }
        
        return isPrime;
    }
    
    public static void timeBetweenPrimes(int number, int index, long before, long after){
        
        timeBetweenPrimes[index] = after - before;
    }
}
