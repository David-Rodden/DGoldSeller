import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Shop;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.script.task.Task;

import java.util.Optional;

public class SellGold extends Task {
    private final int maxGold = 20;

    @Override
    public boolean validate() {
        return Inventory.contains("Gold bar") && Shop.isOpen() && Shop.getQuantity("Gold bar") < maxGold;
    }

    @Override
    public int execute() {
        Optional.ofNullable(Inventory.getFirst("Gold bar")).ifPresent(b -> {
            int toSell = maxGold - Shop.getQuantity("Gold bar");
            while (toSell != 0)
                if (toSell >= 5) {
                    Shop.sellFive("Gold bar");
                    toSell -= 5;
                } else {
                    Shop.sellOne("Gold bar");
                    toSell--;
                }
            Time.sleepUntil(() -> Shop.getQuantity("Gold bar") >= maxGold, 4000);
        });
        return Random.mid(600, 800);
    }

    @Override
    public String toString() {
        return "Selling gold";
    }
}
