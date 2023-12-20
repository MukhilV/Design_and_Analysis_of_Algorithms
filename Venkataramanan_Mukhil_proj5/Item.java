public class Item {
    String itemNumber;
    int profit, weight;
    String isIncluded;
    double profitPerUnit;

    Item(String itemNumber, int profit, int weight) {
        this.isIncluded = "no";
        this.itemNumber = itemNumber;
        this.profit = profit;
        this.weight = weight;
        this.profitPerUnit = (1.0*profit)/weight;
    }
}
