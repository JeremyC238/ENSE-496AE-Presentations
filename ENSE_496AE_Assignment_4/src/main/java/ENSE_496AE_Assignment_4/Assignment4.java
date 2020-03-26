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
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Jeremy
 */
public class Assignment4 {
    
    private static long[] timeBetweenPrimes = new long[100];
    private static long before;
    private static long after;
    
    public static void main(String[] args){
        
        try {
            int[] primeNumbers = new int[200]; // first requirement
            int[] primeNumbers2 = new int[100]; // second requirement
            long bigSeed = 2147483647;
            long secretKey = 0;
 
            // creates txt file
            File file1 = new File("PrimeNumbers.txt");
            File file2 = new File("PrimeNumbers.doc");
            File file3 = new File("AudioBytes.txt");
            File file4 = new File("ShuffledAudioBytes.txt");

            // check if file generates properly
            if(!file1.exists()){
                file1.createNewFile();
            }
            if(!file2.exists()){
                file2.createNewFile();
            }
            if(!file3.exists()){
                file3.createNewFile();
            }
            if(!file4.exists()){
                file4.createNewFile();
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
            
            writeFile1.write("\n");
            writeFile2.write("\n");
            
            // compare the two files to see if they are the same
            compareFiles(file1, file2, writeFile1, writeFile2);
            
            writeFile1.write("\n");
            writeFile2.write("\n");
            
            /*******************************************************************
             * Third Requirement
            *******************************************************************/
            
            writeFile1.write("\n");
            writeFile1.write("Third Requirement\n");
            writeFile2.write("\n");
            writeFile2.write("Third Requirement\n");
            
            // Diffie-Hellman algorithm, returns the secret key
            secretKey = thirdRequirement(file1, writeFile1);
            
            writeFile1.write("\n");
            writeFile2.write("\n");

            /*******************************************************************
             * Fourth Requirement
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
            
            /*******************************************************************
             * Fifth Requirement
            *******************************************************************/
            
            // shuffles and unshuffles the
            fifthRequirement(file3, file4);

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
    
    public static void compareFiles(File fileName1, File fileName2, FileWriter write1, FileWriter write2){
        
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
                write1.write("Two files have same content.");
                write2.write("Two files have same content.");
            }
            else{
                write1.write("Two files have different content. They differ at line "+lineNum);
                write1.write("File1 has "+line1+" and File2 has "+line2+" at line "+lineNum);
                write2.write("Two files have different content. They differ at line "+lineNum);
                write2.write("File1 has "+line1+" and File2 has "+line2+" at line "+lineNum);
            }

            reader1.close();
            reader2.close();
        }
        catch (IOException e){
            e.printStackTrace();
        } 
    } 
    
    public static long thirdRequirement(File FileName, FileWriter write){
        
        // alice - private key a
        // bob - private key b
        // public keys - P, G
        
        long P = 23; //23
        long G = 9; //9
        long a = 4; //4
        long b = 3; //3
        long alicePublicKey;
        long bobPublicKey;
        long secretKeyAlice = 0;
        long secretKeyBob;
        
        try {
            // determine alices public key
            write.write("The private key for alice is: " + a + "\n");
            alicePublicKey = power(G, a, P);
            write.write("Alices shared public value is: " + alicePublicKey + "\n");

            // determine bobs public key
            write.write("the private key for bob is: " + b + "\n");
            bobPublicKey = power(G, b, P);
            write.write("Bobs shared public value is: " + bobPublicKey + "\n");

            // generateing the secret key
            secretKeyAlice = power(bobPublicKey, a, P);
            write.write("Alices secret key is: " + secretKeyAlice + "\n");
            secretKeyBob =  power(alicePublicKey, b, P);
            write.write("Bobs secret key is: " + secretKeyBob + "\n");

            // check if the secret keys are the same
            if(secretKeyAlice == secretKeyBob)
                write.write("the secret key is: " + secretKeyAlice);
            else
                write.write("error, the secret keys do not match!!");

            return secretKeyAlice;
        }
        catch (IOException e){
            e.printStackTrace();
        } 
        
        return secretKeyAlice;
    }
    
    public static long power(long a, long b, long P){
        
        // calculate the key value using a^b mod P
        if (b == 1)
            return a;
        else {
            return (long)(Math.pow(a, b) % P);
        }  
    }
    
    public static void fifthRequirement(File file3, File file4){
   
        try {

            Scanner nextByte = new Scanner(file3);
            List<String> byteValues = new ArrayList<>();
            int counter = 0;
            int i = 0;
            //int j = 0;
            int secretKey = 4894308;
            //Random secretKeyRandomNumber = new Random(secretKey);
            
            // fills the array with values
            while(nextByte.hasNext()){
                byteValues.add(nextByte.next());
            }
            
            nextByte.reset();
            nextByte.close();
            
            nextByte = new Scanner(file3);
            counter = 0;
            
            // shuffles the array and then unshuffles it
            byteValues = ShuffleBytes(byteValues, secretKey);
            byteValues = DeShuffle(byteValues, secretKey);
                        
            // prints the shuffled array
            for(int j = 0; j < byteValues.size(); j++){
                System.out.printf(byteValues.get(j) + " ");
                counter++;
                        if(counter == 16){
                            System.out.print("\n");
                            //writeFile4.write("\n");
                            counter = 0;
                        }
            }
            
            nextByte.reset();
            nextByte.close();
        }
        catch (IOException e){
            e.printStackTrace();

            System.out.printf("failed");
        }
    }
    
    public static int[] GetShuffleExchanges(int size, int key)
    {
        
        // creates the array of swapping index positions and RNG with key
        int[] exchanges = new int[size - 1];
        Random rand = new Random(key);
        
        // fills the array going backwards for the size of the byte array
        for (int i = size - 1; i > 0; i--)
        {
            int n = rand.nextInt(i + 1);
            exchanges[size - 1 - i] = n;
        }
        
        // returns the array 
        return exchanges;
    }

    public static List<String> ShuffleBytes(List<String> toShuffle, int key){
        
        // creates the array of swapping index positions
        int size = toShuffle.size();
        int[] exchanges = GetShuffleExchanges(size, key);
        
        // swaps values in the byte array going backwards
        for (int i = size - 1; i > 0; i--)
        {
            int n = exchanges[size - 1 - i];
            String tmp = toShuffle.get(i);
            toShuffle.set(i, toShuffle.get(n));
            toShuffle.set(n, tmp);
        }
        
        // returns shuffled array
        return toShuffle;
    }
    
    public static List<String> DeShuffle(List<String> shuffled, int key){
        
        // creates the array of swapping index positions
        int size = shuffled.size();
        int[] exchanges = GetShuffleExchanges(size, key);
        
        // swaps values in the byte array going forwards
        for (int i = 1; i < size; i++)
        {
            int n = exchanges[size - 1 - i];
            String tmp = shuffled.get(i);
            shuffled.set(i, shuffled.get(n));
            shuffled.set(n, tmp);
        }
        
        // returns original array
        return shuffled;
    }

    
}


