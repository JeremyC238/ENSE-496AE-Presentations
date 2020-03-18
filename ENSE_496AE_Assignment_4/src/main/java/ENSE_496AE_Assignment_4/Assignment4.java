/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENSE_496AE_Assignment_4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Jeremy
 */
public class Assignment4 {
    
    private static long[] timeBetweenPrimes = new long[2000];
    private static long before;
    private static long after;
    
    public static void main(String[] args){
        
        try {
            int[] primeNumbers = new int[200]; // first requirement
            int[] primeNumbers2 = new int[2000]; // second requirement
            long bigSeed = 2147483647;
            long secretKey = 0;
 
            // creates txt file
            File file1 = new File("PrimeNumbers.txt");
            File file2 = new File("PrimeNumbers.doc");

            // check if file generates properly
            if(!file1.exists()){
                file1.createNewFile();
            }
            if(!file2.exists()){
                file2.createNewFile();
            }

            FileWriter writeFile1 = new FileWriter(file1);
            FileWriter writeFile2 = new FileWriter(file2);

            // generate random number with a seed value
            Random randomNumber = new Random(bigSeed);

            /*******************************************************************
             * First Requirement
            *******************************************************************/
            
            writeFile1.write("First Requirement\n");
            writeFile2.write("First Requirement\n");
            
            firstRequirement(primeNumbers, file1, writeFile1, randomNumber);
            firstRequirement(primeNumbers, file2, writeFile2, randomNumber);
            
            /*******************************************************************
             * Second Requirement
            *******************************************************************/
            
            writeFile1.write("\n");
            writeFile1.write("Second Requirement\n");
            writeFile2.write("\n");
            writeFile2.write("Second Requirement\n");
            
            secondRequirement(primeNumbers2, file1, writeFile1);
            secondRequirement(primeNumbers2, file2, writeFile2);
            
            // compare the two files to see if they are the same
            compareFiles(file1, file2);
            
            /*******************************************************************
             * Third Requirement
            *******************************************************************/
            
            // Diffie-Hellman algorithm
            secretKey = thirdRequirement();

            /*******************************************************************
             *  Fourth Requirement
            *******************************************************************/
            
            // writes 200 random prime values based on secret key from third requirement 
            writeFile1.write("\n");
            writeFile1.write("Fourth Requirement\n");
            writeFile2.write("\n");
            writeFile2.write("Fourth Requirement\n");
            
            //random number from secret key
            Random secretKeyRandomNumber = new Random(secretKey);
            writeFile1.write("200 random prime numbers based on secret key value from third requirement\n");
            writeFile2.write("200 random prime numbers based on secret key value from third requirement\n");
            firstRequirement(primeNumbers, file1, writeFile1, secretKeyRandomNumber);
            firstRequirement(primeNumbers, file2, writeFile2, secretKeyRandomNumber);
            
            writeFile1.close();
            writeFile2.close();
        }
        catch (IOException e){
            e.printStackTrace();
        } 
    }
    
    public static void firstRequirement(int[] primeNumbers, File FileName, FileWriter write, Random randomNumber){
        
        int number = 0;
        int counter = 0;
        
        try {
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
        }
        catch (IOException e){
            e.printStackTrace();
        } 
    }
    
    public static void secondRequirement(int[] primeNumbers, File FileName, FileWriter write){
        
        int number = 1000000;
        int counter = 0;
        
        try {
            for (int i = 0; i < primeNumbers.length; i++){

                if (checkIfPrime(number)){
                    
                    // check the time for identifying the prime
                    before = System.nanoTime();
                    checkIfPrime(number);
                    after = System.nanoTime();
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
                
                number++;
            }
            
            write.write("\n");
            write.write("Times between Primes\n");
            
            for(int i = 0; i < primeNumbers.length; i++){
                
                write.write("time for prime " + primeNumbers[i] + 
                        ": " + timeBetweenPrimes[i] + "\n");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        } 
    }
    
    public static long thirdRequirement(){
        
        // alice - private key a
        // bob - private key b
        // public keys - P, G
        
        long P = 23;
        long G = 9;
        long a = 4;
        long b = 3;
        long alicePublicKey;
        long bobPublicKey;
        long secretKeyAlice;
        long secretKeyBob;
        
        // determine alices public key
        System.out.printf("The private key for alice is: " + a + "\n");
        alicePublicKey = power(G, a, P);
        System.out.printf("Alices shared public value is: " + alicePublicKey + "\n");
        
        // determine bobs public key
        System.out.printf("the private key for bob is: " + b + "\n");
        bobPublicKey = power(G, b, P);
        System.out.printf("Bobs shared public value is: " + bobPublicKey + "\n");
        
        // generateing the secret key
        secretKeyAlice = power(bobPublicKey, a, P);
        System.out.printf("Alices secret key is: " + secretKeyAlice + "\n");
        secretKeyBob =  power(alicePublicKey, b, P);
        System.out.printf("Bobs secret key is: " + secretKeyBob + "\n");
        
        // check if the secret keys are the same
        if(secretKeyAlice == secretKeyBob)
            System.out.printf("the secret key is: " + secretKeyAlice);
        else
            System.out.printf("error, the secret keys do not match!!");
        
        return secretKeyAlice;
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
    
    public static void compareFiles(File fileName1, File fileName2){
        
        try {
            BufferedReader reader1 = new BufferedReader(new FileReader(fileName1));
            BufferedReader reader2 = new BufferedReader(new FileReader(fileName2));
            
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            
            boolean areEqual = true;
         
            int lineNum = 1;

            while (line1 != null || line2 != null){
                
                if(line1 == null || line2 == null){
                    areEqual = false;
                    break;
                }
                else if(! line1.equalsIgnoreCase(line2)){
                    areEqual = false;
                    break;
                }

                line1 = reader1.readLine();
                line2 = reader2.readLine();

                lineNum++;
            }

            if(areEqual){
                System.out.println("Two files have same content.");
            }
            else{
                System.out.println("Two files have different content. They differ at line "+lineNum);
                System.out.println("File1 has "+line1+" and File2 has "+line2+" at line "+lineNum);
            }

            reader1.close();
            reader2.close();
       
        }
        catch (IOException e){
            e.printStackTrace();
        } 
    }
    
    public static long power(long a, long b, long P){
        
        // calculate the key value using a^b mod P
        if (b == 1)
            return a;
        else {
            return (long)(Math.pow(a, b) % P);
        }  
    }
}
