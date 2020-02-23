import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Shop;
import org.rspeer.runetek.api.component.WorldHopper;
import org.rspeer.runetek.providers.RSWorld;
import org.rspeer.script.task.Task;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class HopWorld extends Task {
    private final Set<Integer> worlds;
    private final DGoldSeller handler;

    HopWorld(final DGoldSeller handler) {
        this.handler = handler;
        worlds = Arrays.stream(Worlds.getLoaded()).filter(worlds -> !worlds.isMembers()).map(RSWorld::getId).collect(Collectors.toSet());
    }

    @Override
    public boolean validate() {
        return Shop.isOpen() && Shop.getQuantity("Gold bar") >= 20;
    }

    @Override
    public int execute() {
        final int currentWorld = Worlds.getCurrent();
        handler.update(currentWorld, Shop.getQuantity("Gold bar"));
        final Map<Integer, Integer> info = handler.getInfo();
        final Set<Integer> recordedWorlds = info.keySet();
        WorldHopper.hopTo(recordedWorlds.containsAll(worlds) ? info.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey() : Random.nextElement(getUnrecordedWorlds(recordedWorlds)));
        Time.sleepUntil(() -> Worlds.getCurrent() != currentWorld, 4000);
        return Random.mid(400, 600);
    }

    private Set<Integer> getUnrecordedWorlds(final Set<Integer> recordedWorlds) {
        final Set<Integer> freeWorlds = new HashSet<>(worlds);
        freeWorlds.removeAll(recordedWorlds);
        return freeWorlds;
    }

    @Override
    public String toString() {
        return "Hopping world";
    }
}
