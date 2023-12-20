import java.util.Comparator;

// Comparator to sort the items based on profit per unit
public class ProfitPerUnitCompare implements Comparator<Item> {
    public int compare(Item item1, Item item2)
    {
        if (item1.profitPerUnit > item2.profitPerUnit)
            return -1;
        if (item1.profitPerUnit < item2.profitPerUnit)
            return 1;
        else
            return 0;
    }
}