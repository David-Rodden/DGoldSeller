import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.Players;

public class WalkToShop extends SellTask {
    private final Area shopArea;

    WalkToShop(final Area shopArea) {
        this.shopArea = shopArea;
    }

    @Override
    public boolean validate() {
        return !shopArea.contains(Players.getLocal()) && Inventory.contains(focusedItem);
    }

    @Override
    public int execute() {
        Movement.walkTo(Random.nextElement(shopArea.getTiles()));
        Time.sleepUntil(() -> shopArea.contains(Players.getLocal()) || Movement.getDestinationDistance() < 5, Random.mid(1200, 1800));
        return Random.mid(600, 800);
    }

    @Override
    public String toString() {
        return "Walking to shop";
    }
}
