import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Shop;
import org.rspeer.runetek.api.component.tab.Inventory;

import java.util.Optional;

public class SellGold extends ActionNode {
    private final int maxGold = 20;
    private boolean check() {
        return Inventory.contains("Gold bar") && Shop.isOpen() && Shop.getQuantity("Gold bar") < maxGold;
    }

    private void invoke() {
        Optional.ofNullable(Inventory.getFirst("Gold bar")).ifPresent(b -> {
            final int toSell = maxGold - Shop.getQuantity("Gold bar");
            Shop.sell(item -> item.getId() == b.getId(), toSell);
            Time.sleepUntil(() -> Shop.getQuantity("Gold bar") >= maxGold, 4000);
        });
    }
}
