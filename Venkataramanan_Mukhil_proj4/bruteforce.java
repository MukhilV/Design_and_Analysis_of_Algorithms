import java.util.*;
import java.io.*;

public class bruteforce{

    int N;
    int W;
    Item items[];

    // function to get the sum of weights of the given items
    public int getSumOfWeights(ArrayList<Item> items) {
        int sum = 0;
        for(int i=0; i<items.size(); i++){
            Item item = items.get(i);
            sum += item.weight;
        }
        return sum;
    }

    // function to get the sum of profits of the given items
    public int getSumOfProfits(ArrayList<Item> items) {
        int sum = 0;
        for(int i=0; i<items.size(); i++){
            Item item = items.get(i);
            sum += item.profit;
        }
        return sum;
    }

    // Function to read the input file
    public void readFile(String filePath, bruteforce obj){
        try {

            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);

            String firstLine = myReader.nextLine();
            String strArr[] = firstLine.split("\s+",0); 

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

                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file!");
            e.printStackTrace();
        }
    }

    // Function to write into the file
    public void writeToFile(String filePath, ArrayList<Item> itemsWithMaxProfit) {
        int numberOfItems = itemsWithMaxProfit.size();
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(numberOfItems+" "+getSumOfProfits(itemsWithMaxProfit)+" "+getSumOfWeights(itemsWithMaxProfit)+"\n");
            for(int i=0;i<numberOfItems;i++){
                Item currItem = itemsWithMaxProfit.get(i);
                myWriter.write(currItem.itemNumber+" "+currItem.profit+" "+currItem.weight+"\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing into file!");
            e.printStackTrace();
        }
    }

    // Function to return the items with max profit by bruteforce approach
    public ArrayList<Item> knapSackBruteForce(Item items[], int n, int knapsackCapacity){

        ArrayList<Item> itemsWithMaxProfit = new ArrayList<Item>();
        int maxProfit = 0;
        for (int j = 0; j < (int)Math.pow(2,n); j++){
            int sumOfWeights = 0;
            int sumOfProfits = 0;
            ArrayList<Item> currentlySelectedItems = new ArrayList<Item>();
            for(int i=0; i<n; i++){
                // generating all possible combinations of items using bitwise operator
                if (((j >> i) & 1)==1) {
                    {  
                        if ((sumOfWeights + items[i].weight) <= knapsackCapacity)
                        {
                            sumOfWeights += items[i].weight;
                            sumOfProfits += items[i].profit;
                            currentlySelectedItems.add(items[i]);
                        } else {
                            break;
                        }
                    }
                }
            }

            // For every combination of items, if the sumOfProfits is greater that maxProfit so far,
            // then update the maxProfit and the itemsWithMaxProfit
            if(maxProfit < sumOfProfits) {
                maxProfit = sumOfProfits;
                itemsWithMaxProfit = new ArrayList<Item>(currentlySelectedItems);
            }    
        }

        return itemsWithMaxProfit;
    }

    public static void main(String args[]){

        // check for correct usage of the program
        if(args.length != 1) {
            System.out.println("Wrong Usage! \nCorrect Usage : java bruteforce <file_path>");
            return;
        }

        // getting input file path from the command line argument
        String filePath = args[0];

        // creating object for this class
        bruteforce obj = new bruteforce();

        // reading input file
        obj.readFile(filePath, obj);

        // Getting items with max-profit, by bruteforce approach
        ArrayList<Item> itemsWithMaxProfit = obj.knapSackBruteForce(obj.items, obj.N, obj.W);

        // Writing output to file.
        obj.writeToFile("output1.txt", itemsWithMaxProfit);

        return ;
    }
}


// 9 64
// item1 13 19
// item2 23 14
// item3 13 16
// item4 19 10
// item5 18 9
// item6 23 10
// item7 19 11
// item8 22 6
// item9 19 12

// 3 30 
// item1 50 5
// item2 60 10
// item3 140 20

// if((((j >> i) & 1)==1)) System.out.print(1);
                // else System.out.print(0);
