import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class igreedy{

    int N;
    int W;
    Item items[];

    // Function to read the input file
    public void readFile(String filePath, igreedy obj){
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

    // Function to sort the items based on their profitPerUnit value in descending order
    public ArrayList<Item> sortBasedOnProfitPerUnit(Item[] items){
        ArrayList<Item> itemsList = new ArrayList<Item>();
        for(int i=0; i<items.length; i++){
            itemsList.add(items[i]);
        }
        ProfitPerUnitCompare customComparator = new ProfitPerUnitCompare();
        Collections.sort(itemsList, customComparator);
        return itemsList;
    }

    // Greedy 4 algorithm
    public ArrayList<Item> greedy4(Item[] items, int n, int knapsackCapacity){

        // sorted in descending order
        ArrayList<Item> sortedItems = sortBasedOnProfitPerUnit(items);

        int sumOfWeights = 0;
        ArrayList<Item> selectedItems = new ArrayList<Item>();

        for(int i=0; i<n; i++){
            Item currItem = sortedItems.get(i);
            if(sumOfWeights + currItem.weight <= knapsackCapacity) {
                selectedItems.add(currItem);
                sumOfWeights += currItem.weight;
            }
        }
        return selectedItems;
    }

    // Function to return the item with maxProfit
    public Item getMaxBItem(Item[] items, int knapsackCapacity){
        Item maxB = new Item();
        for(int i=0; i<items.length; i++){
            if(items[i].profit > maxB.profit && maxB.weight <= knapsackCapacity){
                maxB = items[i];
            }
        }
        // System.out.println(maxB.profit+","+knapsackCapacity);
        return maxB;
    }

    public static void main(String[] args){

        // check for correct usage
        if(args.length != 1) {
            System.out.println("Wrong Usage! \nCorrect Usage : java igreedy <file_path>");
            return;
        }

        // get filepath from commandline argument 
        String filePath = args[0];

        // create a new object for this class
        igreedy greedyObj = new igreedy();
        bruteforce bruteforceObj = new bruteforce();

        // Read the input file
        greedyObj.readFile(filePath, greedyObj);

        // get Items with max profit by greeay algorithm
        ArrayList<Item> itemsWithgreedy4Profit = greedyObj.greedy4(greedyObj.items, greedyObj.N, greedyObj.W);
        int greedy4Profit = bruteforceObj.getSumOfProfits(itemsWithgreedy4Profit);

        // get Item with max profit
        Item maxBItem = greedyObj.getMaxBItem(greedyObj.items, greedyObj.W);

        ArrayList<Item> itemsWithMaxProfit = new ArrayList<Item>();

        // System.out.println(greedy4Profit+","+maxBItem.profit);

        // compare the profit of both the algorithms and return the max
        if(greedy4Profit > maxBItem.profit) {
            itemsWithMaxProfit = new ArrayList<Item>(itemsWithgreedy4Profit);

        } else {
            itemsWithMaxProfit.add(maxBItem);
        }

        // write the output to the file
        bruteforceObj.writeToFile("output3.txt", itemsWithMaxProfit);

        return ;
    }
}

        // System.out.println("Items with greedy4 profit");
        // for(int i=0; i<itemsWithgreedy4Profit.size(); i++){
        //     Item currItem = itemsWithgreedy4Profit.get(i);
        //     System.out.println(currItem.itemNumber+" "+currItem.profit+" "+currItem.weight+" "+currItem.profitPerUnit);
        // }

        // System.out.println("\nItems with MaxB profit");
        // System.out.println(maxBItem.itemNumber+" "+maxBItem.profit+" "+maxBItem.weight+" "+maxBItem.profitPerUnit);