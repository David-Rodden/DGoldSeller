import org.rspeer.runetek.api.Worlds;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.WorldHopper;
import org.rspeer.runetek.providers.RSWorld;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HopWorld extends ActionNode {
    private static ActionNode created;
    private Set<Integer> worlds;

    HopWorld() {
        worlds = Arrays.stream(Worlds.getLoaded()).filter(worlds -> !worlds.isMembers()).map(RSWorld::getId).collect(Collectors.toSet());
    }

    static ActionNode create() {
        return created == null ? new HopWorld() : created;
    }

    private void invoke() {
        if (!info.containsAll(worlds)) {
            final Set<Integer> freeWorlds = new HashSet<>(worlds);
            freeWorlds.removeAll(info.stream().map(WorldInfo::getWorld).collect(Collectors.toSet()));
            final int nextWorld = Random.nextElement(freeWorlds);
            WorldHopper.hopTo(nextWorld);
            Time.sleepUntil(() -> Worlds.getCurrent() == nextWorld, 4000);
        }
    }
}
