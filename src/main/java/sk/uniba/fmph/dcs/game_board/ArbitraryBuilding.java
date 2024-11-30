package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.Collection;
import java.util.OptionalInt;

public class ArbitraryBuilding implements Building {
    private final int maxNumberOfResources;

    public ArbitraryBuilding(int maxNumberOfResources) {
        this.maxNumberOfResources = Math.min(maxNumberOfResources, 7);
    }

    @Override
    public OptionalInt build(Collection<Effect> resources) {
        if (resources == null || resources.isEmpty() || resources.size() < 1 || resources.size() > maxNumberOfResources) {
            return OptionalInt.empty();
        }

        int totalPoints = 0;
        for (Effect resource : resources) {
            if (!resource.isResource()) {
                return OptionalInt.empty();
            }
            totalPoints += resource.points();
        }
        return OptionalInt.of(totalPoints);
    }

    @Override
    public String state() {
        return "";
    }
}
