import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Shop;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.script.task.Task;

import java.util.Optional;

public class OpenShop extends Task {
    @Override
    public boolean validate() {
        return Inventory.contains("Gold bar") && !Shop.isOpen();
    }

    @Override
    public int execute() {
        Optional.ofNullable(Npcs.getNearest("Drogo dwarf")).ifPresent(t -> {
            t.interact("Trade");
            Time.sleepUntil(Shop::isOpen, Random.mid(1500, 2000));
        });
        return Random.mid(400, 600);
    }

    @Override
    public String toString() {
        return "Opening shop";
    }
}
