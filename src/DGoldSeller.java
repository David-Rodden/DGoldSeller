import org.rspeer.script.task.TaskScript;

import java.util.HashMap;
import java.util.Map;

public class DGoldSeller extends TaskScript {
    private Map<Integer, Integer> info;

    @Override
    public void onStart() {
        info = new HashMap<>();
        submit(new OpenShop(), new SellGold(), new HopWorld(this));
    }

    Map<Integer, Integer> getInfo() {
        return info;
    }

    void update(final int world, final int amount) {
        info.put(world, amount);
    }
}
