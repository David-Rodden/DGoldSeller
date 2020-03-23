import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ScriptMeta(name = "DSeller", developer = "Dungeonqueer", desc = "Sells selected item", category = ScriptCategory.MONEY_MAKING)
public class DSeller extends TaskScript implements RenderListener {
    private Map<Integer, Integer> info;

    @Override
    public void onStart() {
        info = new HashMap<>();
        final Area shopArea = Area.rectangular(3024, 9849, 3041, 9842);
        submit(new WalkToShop(shopArea), new OpenShop(shopArea), new SellItem(), new HopWorld(this));
    }

    Map<Integer, Integer> getInfo() {
        return info;
    }

    void update(final int world, final int amount) {
        info.put(world, amount);
    }

    @Override
    public void notify(final RenderEvent renderEvent) {
        Optional.ofNullable(renderEvent.getSource()).ifPresent(graphics -> {
            graphics.setColor(Color.CYAN);
            final Task current = getCurrent();
            graphics.drawString("Current task: " + (current != null ? current : "None"), 30, 30);
            graphics.drawString("Accessed worlds: " + info.size(), 50, 50);
        });
    }
}
