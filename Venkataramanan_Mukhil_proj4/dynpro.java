import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class dynpro {

    int N;
    int W;
    Item items[];
    int DP[][]; 

    // Function to Initialize the DP matrix
    public void initializeMatrix(dynpro dynproObj){
        for(int i=0; i<dynproObj.N+1; i++){
            for(int j=0; j<dynproObj.W+1; j++){
                dynproObj.DP[i][j] = -1;
            }
        }
    }

    // Funtion to read the input file
    public void readFile(String filePath, dynpro obj){
        try {

            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);

            String firstLine = myReader.nextLine();
            String strArr[] = firstLine.split("\s+",0); 

            // System.out.println("strArr:"+strArr[0]+","+strArr[1]);

            obj.N = Integer.parseInt(strArr[0]);
            obj.W = Integer.parseInt(strArr[1]);
            obj.items = new Item[N];

            for(int i=0;i<obj.N;i++){

                if(myReader.hasNextLine()) {

                    String line = myReader.nextLine();
                    String entries[] = line.split("\s+",0); 

                    obj.items[i] = new Item();

                    obj.items[i].itemNumber = entries[0];
                    obj.items[i].profit = Integer.parseInt(entries[1]);
                    obj.items[i].weight = Integer.parseInt(entries[2]);
                    obj.items[i].profitPerUnit = (float) (1.0*obj.items[i].profit) / obj.items[i].weight; 

                    // System.out.println("arr:"+entries[0]+","+obj.items[i].profit +","+obj.items[i].weight);

                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file!");
            e.printStackTrace();
        }
    }

    // Function to write entries into the file
    public void writeEntries(String filePath){

        int numberOfItems = DP.length;
        int knapsackCapacity = DP[0].length;
        try {
            FileWriter myWriter = new FileWriter(filePath);
            for(int i=1;i<numberOfItems;i++){
                myWriter.write("row"+i+" ");
                for(int j=0; j<knapsackCapacity; j++){
                    if(DP[i][j]!=-1) myWriter.write(DP[i][j]+"\t");
                }
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing into file!");
            e.printStackTrace();
        }

    }

    /* Original Dynamic programming code to cross verify the answer */
    public int refinedDynPro(Item[] items, int n, int knapsackCapacity) {
        int profit[] = new int[n];
        int weight[] = new int[n];
        for(int i=0; i<n; i++){
            profit[i] = items[i].profit;
            weight[i] = items[i].weight;
        }

        DP = new int[n+1][knapsackCapacity+1];
        for(int i=0; i<=n; i++){
            for(int j=0; j<=knapsackCapacity; j++){
                if(i==0 || j==0) {
                    DP[i][j] = 0;
                } else if(weight[i-1] <= j) {
                    DP[i][j] = Math.max(DP[i-1][j], DP[i-1][j-weight[i-1]]+profit[i-1]);
                } else {
                    DP[i][j] = DP[i-1][j];
                }
            }
        }

        return DP[n][knapsackCapacity];

    }

    /* Actual refined Dynamic programing algorithm */
    public int refinedDynPro2(int n, int knapsackCapacity, int[] profit, int[] weight, dynpro dynproObj ) {

        // If knapsackCapacity<=0, then no items can be added into the knapsack
        // number of items(n) <=0, no items are there to add into the knapsack
        if (n <= 0 || knapsackCapacity <= 0) {
            return 0;
        }

        // If the value is already computed, then return from the DP matrix
        if(dynproObj.DP[n][knapsackCapacity] != -1) {
            return dynproObj.DP[n][knapsackCapacity];
        }

        // If weight of the item under consideration is greater than tha knapsack weight,
        // then, cannot include the item into the knapsack, hence get the profit from previous row
        if (weight[n-1] > knapsackCapacity) {
            dynproObj.DP[n][knapsackCapacity] = refinedDynPro2(n-1, knapsackCapacity, profit, weight, dynproObj);
        }
        
        // else, compute the max(maxProfit for first n-1 items + profit of nth item, maxProfit for first n items) 
        else {
            dynproObj.DP[n][knapsackCapacity] =  Math.max(
                profit[n - 1] + refinedDynPro2(n - 1, knapsackCapacity - weight[n - 1], profit, weight, dynproObj), 
                refinedDynPro2(n-1, knapsackCapacity, profit, weight, dynproObj)
            );
        } 

        return dynproObj.DP[n][knapsackCapacity];

    }

    // Function to get the items with optimal solution 
    public ArrayList<Item> getOptimalSolItems(int n, int knapsackCapacity, dynpro dynproObj){
        ArrayList<Item> selectedItems = new ArrayList<Item>();
        int i = n, w = knapsackCapacity;
        while(i>0 && w>0){
            // System.out.println(dynproObj.DP[i][w] +","+ dynproObj.DP[i-1][w] +" \t (i,w)-"+ i+ ","+w);
            if(dynproObj.DP[i][w] != dynproObj.DP[i-1][w]){
                selectedItems.add(dynproObj.items[i-1]);
                w -= dynproObj.items[i-1].weight;
                i-=1;
            } 
            else {
                i-=1;
            }
            // System.out.println(w);
        }
        return selectedItems;
    }

    public String readFile1() {
        String retStr = "";
        try {
          File myObj = new File("output1.txt");
          Scanner myReader = new Scanner(myObj);
          while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            retStr = retStr + data + "\n";
            // System.out.println(data);
          }
          myReader.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        return retStr;
      }

    public void writeToFile1(String filePath, ArrayList<Item> itemsWithMaxProfit) {
        int numberOfItems = itemsWithMaxProfit.size();
        String retStr = readFile1();
        try {
            FileWriter myWriter = new FileWriter(filePath);
            // myWriter.write(numberOfItems+" "+getSumOfProfits(itemsWithMaxProfit)+" "+getSumOfWeights(itemsWithMaxProfit)+"\n");
            // for(int i=0;i<numberOfItems;i++){
            //     Item currItem = itemsWithMaxProfit.get(i);
            //     myWriter.write(currItem.itemNumber+" "+currItem.profit+" "+currItem.weight+"\n");
            // }
            myWriter.write(retStr);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing into file!");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        // check for correct usage of the program
        if(args.length != 1) {
            System.out.println("Wrong Usage! \nCorrect Usage : java dynpro <file_path>");
            return;
        }

        // getting input file path from the command line argument
        String filePath = args[0];

        // creating object for this class
        dynpro dynproObj = new dynpro();

        // read the input file
        dynproObj.readFile(filePath, dynproObj);

        // creating object for bruteforce class
        bruteforce bruteforceObj = new bruteforce();

        /* Code to cross verify the answer of refined algorithm with actual DP algorithm */
        /*
        int dynProProfit = dynproObj.refinedDynPro(dynproObj.items, dynproObj.N, dynproObj.W);
        ArrayList<Item> optimalSolItems = dynproObj.getOptimalSolItems(dynproObj.N, dynproObj.W, dynproObj);
        bruteforce bruteforceObj = new bruteforce();
        bruteforceObj.writeToFile("Output2.txt", optimalSolItems);
        dynproObj.writeEntries("entries2.txt");
        */


        // initialzie the profits and weights
        int profit[] = new int[dynproObj.N];
        int weight[] = new int[dynproObj.N];
        for(int i=0; i<dynproObj.N; i++){
            profit[i] = dynproObj.items[i].profit;
            weight[i] = dynproObj.items[i].weight;
        }

        // initialize the DP matrix
        dynproObj.DP = new int[dynproObj.N+1][dynproObj.W+1];
        dynproObj.initializeMatrix(dynproObj);

        // calculate Profit by refined dynamic program algorithm
        int dynProProfit2 = dynproObj.refinedDynPro2(dynproObj.N, dynproObj.W, profit, weight, dynproObj);

        // Write the DP matrix entries into entries file
        dynproObj.writeEntries("entries2.txt");

        // Get the items with max profit
        ArrayList<Item> optimalSolItems = dynproObj.getOptimalSolItems(dynproObj.N, dynproObj.W, dynproObj);

        // Write the items with maxprofit into output file
        bruteforceObj.writeToFile("output2.txt", optimalSolItems);
        dynproObj.writeToFile1("output2.txt", optimalSolItems);

        return;
    }
}
