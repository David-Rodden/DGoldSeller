import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Shop;
import org.rspeer.runetek.api.component.tab.Inventory;

import java.util.Optional;

public class SellItem extends SellTask {
    private final int maxStock = 20;

    @Override
    public boolean validate() {
        return Inventory.contains(focusedItem) && Shop.isOpen() && Shop.getQuantity(focusedItem) < maxStock;
    }

    @Override
    public int execute() {
        Optional.ofNullable(Inventory.getFirst(focusedItem)).ifPresent(b -> {
            int toSell = maxStock - Shop.getQuantity(focusedItem);
            while (toSell != 0)
                if (toSell >= 5) {
                    Shop.sellFive(focusedItem);
                    toSell -= 5;
                } else {
                    Shop.sellOne(focusedItem);
                    toSell--;
                }
            Time.sleepUntil(() -> Shop.getQuantity(focusedItem) >= maxStock, 4000);
        });
        return Random.mid(600, 800);
    }

    @Override
    public String toString() {
        return "Selling " + focusedItem;
    }
}
