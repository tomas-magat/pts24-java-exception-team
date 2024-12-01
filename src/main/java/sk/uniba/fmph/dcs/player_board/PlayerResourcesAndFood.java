package sk.uniba.fmph.dcs.player_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerResourcesAndFood implements InterfacePlayerResourcesAndFood {

    private Map<Effect, Integer> resources;

    public PlayerResourcesAndFood() {
        resources = new HashMap<>();
    }

    public int getNumberOfResources(Effect effect) {
        if(!resources.containsKey(effect)) {
            return -1;    
        }
        return resources.get(effect);
    }

    @Override
    public boolean hasResources(List<Effect> resources) {
        Map<Effect, Integer> res = makeMap(resources);
        for (Map.Entry<Effect, Integer> entry : res.entrySet()) {
            Integer hasCount = this.resources.get(entry.getKey());
            int requiredCount = entry.getValue();
            if (hasCount == null || hasCount < requiredCount) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean takeResources(List<Effect> resources) {
        if (!hasResources(resources)) return false;

        Map<Effect, Integer> res = makeMap(resources);
        for (Map.Entry<Effect, Integer> entry : res.entrySet()) {
            int currentCount = this.resources.get(entry.getKey());
            int takeCount = entry.getValue();
            if (currentCount == takeCount)
                this.resources.remove(entry.getKey());
            else
                this.resources.put(entry.getKey(), currentCount - takeCount);
        }
        return true;
    }

    @Override
    public boolean giveResources(List<Effect> resources) {
        Map<Effect, Integer> res = makeMap(resources);

        for (Map.Entry<Effect, Integer> entry : res.entrySet()) {
            if (!this.resources.containsKey(entry.getKey())) {
                this.resources.put(entry.getKey(), entry.getValue());
            } else {
                this.resources.put(entry.getKey(), this.resources.get(entry.getKey()) + entry.getValue());
            }
        }
        return true;
    }

    @Override
    public int numberOfResourcesForFinalPoints() {
        int totalPoints = 0;
        for (Map.Entry<Effect, Integer> entry : this.resources.entrySet()) {
            Effect e = entry.getKey();
            Integer count = entry.getValue();
            if (e.isResource()) {
                totalPoints += count;
            }
        }
        return totalPoints;
    }

    @Override
    public String state() {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<Effect, Integer> entry : this.resources.entrySet()) {
            map.put(entry.getKey().toString(), entry.getValue().toString());
        }

        return new JSONObject(map).toString();
    }

    private Map<Effect, Integer> makeMap(List<Effect> list) {
        Map<Effect, Integer> res = new HashMap<>();
        for (Effect e : list) {
            if (!res.containsKey(e))
                res.put(e, 1);
            else
                res.put(e, res.get(e) + 1);
        }
        return res;
    }

    public void setResources(Map<Effect, Integer> resources) {
        this.resources = resources;
    }
}
