import java.util.*;
import java.io.*;

public class createkn01{

    // Necessary variable definition
    static int N_UPPER_LIMIT = 10;
    static int N_LOWER_LIMIT = 5;
    static int WEIGHT_UPPER_LIMIT = 5;
    static int WEIGHT_LOWER_LIMIT = 20;
    static int PROFIT_UPPER_LIMIT = 10;
    static int PROFIT_LOWER_LIMIT = 30;

    // Function to Calculate the sum of the array
    public int calculateSum(int arr[]){
        int sum = 0;
        for(int i=0; i<arr.length; i++){
            sum+=arr[i];
        }
        return sum;
    }

    // function to generate random values
    public int[] generateRandomValues(int n, int lowerLimit, int upperLimit){
        int arr[] = new int[n];
        for(int i=0;i<n;i++){
            arr[i]=(int) (Math.random()*(upperLimit-lowerLimit)+lowerLimit);
        }
        return arr;
    }

    // Function to write into the file
    public void writeToFile(String filePath, int n, int weight[], int profit[], int knapsackCapacity) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(n+" "+knapsackCapacity+"\n");
            for(int i=0;i<n;i++){
                myWriter.write("item"+(i+1)+" "+profit[i]+" "+weight[i]+"\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing into file!");
            e.printStackTrace();
        }
    }

    // Driver function
    public static void main(String[] args) {

        // Pre-checks for valid usage
        if(args.length != 1) {
            System.out.println("Wrong Usage! \nCorrect Usage : java createkn01 <file_path>");
            return;
        }

        // getting filepath from command line argument
        String filePath = args[0];

        // generating random value for n
        int n = (int) (Math.random() * (N_UPPER_LIMIT - N_LOWER_LIMIT) + N_LOWER_LIMIT);

        // Creating object for the class
        createkn01 obj = new createkn01();

        // Generating random values for weights for given n
        int weight[] = obj.generateRandomValues(n, WEIGHT_LOWER_LIMIT, WEIGHT_UPPER_LIMIT);

        // Generating random values for profits for given n
        int profit[] = obj.generateRandomValues(n, PROFIT_LOWER_LIMIT, PROFIT_UPPER_LIMIT);

        // calcuating knapsack capacity
        int knapsackCapacity = (int)Math.floor(0.6 * obj.calculateSum(weight));

        // Writing the calculated values into file
        obj.writeToFile(filePath, n, weight, profit, knapsackCapacity);

        return;
    }
}