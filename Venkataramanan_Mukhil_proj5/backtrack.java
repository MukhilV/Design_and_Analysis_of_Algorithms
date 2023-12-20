import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class backtrack {

    // data members
     int N, W, visitedNodes=1;
     float maxProfit;

     Item[] items;
     ArrayList<Item> optimalSolItems;
     ArrayList<String> entryList;

    // Constructor
    backtrack() {
        this.entryList = new ArrayList<String>();
        this.optimalSolItems = new ArrayList<Item>();
    }

    // Function to readfile
     void readFile(String filePath){
        try {

            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);

            String firstLine = myReader.nextLine();
            String strArr[] = firstLine.split("\s+",0); 

            N = Integer.parseInt(strArr[0]);
            W = Integer.parseInt(strArr[1]);
            items = new Item[N];

            for(int i=0;i<N;i++){

                if(myReader.hasNextLine()) {

                    String line = myReader.nextLine();
                    String row[] = line.split("\s+",0); 

                    items[i] = new Item(row[0], Integer.parseInt(row[1]), Integer.parseInt(row[2]));

                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while reading the file!");
            e.printStackTrace();
        }
    }

    // Function to update the optimal solution achieved so far
     void updateOptimalSolItems() {
        optimalSolItems = new ArrayList<Item>();
        for (Item item : items) if (item.isIncluded == "yes") optimalSolItems.add(item); 
    }


     void writeToOutputFile(String filePath) {
        int n = optimalSolItems.size();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(n+ " " + maxProfit + " " + getSumOfWeights(optimalSolItems) + "\n");
            for (int i=0; i< n; i++) {
                Item item = optimalSolItems.get(i);
                writer.write(item.itemNumber + "\t" + item.profit + "\t" + item.weight + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // function to write entries into entries file
     void writeEntryList(String filePath) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            for (int i=0; i<entryList.size(); i++) myWriter.write(entryList.get(i) + "\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // function to sum up the weights of included items
     int getSumOfWeights(ArrayList<Item> itemList) {
        int totalWeight = 0;
        for (int i=0; i<itemList.size(); i++) totalWeight += itemList.get(i).weight;
        return totalWeight;
    }

    // function to sort items based on profit per unit
     void profitPerUnitSort(){
        for(int i=0; i<N-1; i++) {
            for(int j=i+1; j<N; j++){
                if( items[i].profitPerUnit < items[j].profitPerUnit ){
                    Item temp = items[i];
                    items[i] = items[j];
                    items[j] = temp; 
                }
            }
        }
    }

    // Fractional knapsack for given items to find upper bound
     float getUpperBound(int depth, int profit, int weight) {
        if (weight >= W) {
            return 0;
        } 

        float bound = profit;
        int totalWeight = weight;

        for (int i=depth; i<N; i++) {
            if (totalWeight + items[i].weight <= W) {
                bound += items[i].profit;
                totalWeight+=items[i].weight;
            } else {
                bound += ((1.0*items[i].profit)/items[i].weight)*(W - totalWeight);
                return bound;
            }
        }
        return bound;
    }

    // returns whether a node is promising or not
     boolean ispromising(int depth, int profit, int weight){

        double bound = getUpperBound(depth, profit, weight);

        entryList.add(visitedNodes++ + "\t" + profit + "\t" + weight + "\t" + String.format("%.6f", bound));

        if (weight >= W) return false;

        return bound > maxProfit;
        
    }

    // Backtracking function to get the optimal solution
     void knapsackBacktrack(int depth, int profit, int weight) {
        if(!ispromising(depth, profit, weight)) return;

        if (depth == N) {
            if (profit > maxProfit && weight <= W) {
                maxProfit = profit;
                updateOptimalSolItems();
                return;
            }
            else {
                return;
            }
        }

        items[depth].isIncluded = "yes";
        knapsackBacktrack(depth+1, profit+items[depth].profit, weight+items[depth].weight);

        items[depth].isIncluded = "no";
        knapsackBacktrack(depth+1, profit, weight);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong Usage!! \nUsage: java backtrack <file_path>");
            return;
        }

        // get filepath
        String filepath = args[0];

        backtrack Obj = new backtrack();

        //read the input file
        Obj.readFile(filepath);

        // sort the input based on profit per unit
        Obj.profitPerUnitSort();

        // get solution
        Obj.knapsackBacktrack(0, 0, 0);

        // write into entries file
        Obj.writeEntryList("entries3_1.txt");

        // write output
        Obj.writeToOutputFile("output3_1.txt");
    }
}