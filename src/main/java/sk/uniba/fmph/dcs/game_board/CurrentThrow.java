package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.*;

public class CurrentThrow {

    private Effect throwsFor;
    private int throwResult;

    public CurrentThrow() {

    }

    public void initiate(Player player, Effect effect, int dices) {
        this.throwsFor = effect;
        Random rnd = new Random();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < dices; i++) {
            list.add(rnd.nextInt(1, 7)); // upper bound is exclusive
        }

        
    }

    public String state() {
        Map<String, String> map = new HashMap<>();
        map.put("throwsFor", throwsFor.toString());
        map.put("throwResult", throwResult+"");
        return new JSONObject(map).toString();
    }
}
