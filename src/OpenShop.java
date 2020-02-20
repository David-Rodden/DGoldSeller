import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Shop;
import org.rspeer.runetek.api.scene.Npcs;

import java.util.Optional;

public class OpenShop extends ActionNode {
    private void invoke() {
        Optional.ofNullable(Npcs.getNearest("Drogo dwarf")).ifPresent(t -> {
            t.interact("Trade");
            Time.sleepUntil(Shop::isOpen, 1000);
        });
    }
}
