import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Shop;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

import java.util.Optional;

public class OpenShop extends Task {
    private final Area shopArea;

    OpenShop(final Area shopArea) {
        this.shopArea = shopArea;
    }

    @Override
    public boolean validate() {
        return shopArea.contains(Players.getLocal()) && Inventory.contains("Gold bar") && !Shop.isOpen();
    }

    @Override
    public int execute() {
        if (!Movement.isRunEnabled() && Movement.getRunEnergy() >= 10) Movement.toggleRun(true);
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
