import com.beust.jcommander.internal.Lists;
import org.rspeer.script.Script;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

abstract class ScriptHandler extends Script {
    private final Set<WorldInfo> info;
    private final ArrayDeque<ActionNode> nodes;

    ScriptHandler() {
        info = new HashSet<>();
        nodes = new ArrayDeque<>(Lists.newArrayList(new OpenShop(), new SellGold(), new HopWorld()));
    }



}
