package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;

public class VariableBuilding implements Building {
    private final int numberOfResources;
    private final int numberOfResourceTypes;

    public VariableBuilding(final int numberOfResources, final int numberOfResourceTypes) {
        this.numberOfResources = numberOfResources;
        this.numberOfResourceTypes = numberOfResourceTypes;
    }

    @Override
    public OptionalInt build(Collection<Effect> resources) {
        if (resources == null || resources.size() != numberOfResources) {
            return OptionalInt.empty();
        }

        Map<Effect, Integer> resourceCounts = new HashMap<>();
        for (Effect resource : resources) {
            if (!resource.isResource()) {
                return OptionalInt.empty();
            }
            resourceCounts.put(resource, resourceCounts.getOrDefault(resource, 0) + 1);
        }

        if (resourceCounts.size() != numberOfResourceTypes) {
            return OptionalInt.empty();
        }

        int totalPoints = 0;
        for (Map.Entry<Effect, Integer> entry : resourceCounts.entrySet()) {
            totalPoints += entry.getKey().points() * entry.getValue();
        }

        return OptionalInt.of(totalPoints);
    }

    @Override
    public String state() {
        Map<String, String> state = Map.of(
                "building type", "VariableBuilding",
                "required resources", String.valueOf(numberOfResources),
                "required types", String.valueOf(numberOfResourceTypes)
        );
        return new JSONObject(state).toString();
    }
}
