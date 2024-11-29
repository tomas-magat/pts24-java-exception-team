package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.InterfaceToolUse;

import java.util.*;

public class CurrentThrow implements InterfaceCurrentThrow, InterfaceToolUse {

    private Effect throwsFor;
    private int throwResult;

    public CurrentThrow() {

    }

    @Override
    public void initiate(Player player, Effect effect, int dices) {
        this.throwsFor = effect;
        Random rnd = new Random();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < dices; i++) {
            list.add(rnd.nextInt(1, 7)); // upper bound is exclusive
        }

        
    }

    @Override
    public int getThrowResult() {
        return throwResult;
    }

    @Override
    public String state() {
        Map<String, String> map = new HashMap<>();
        map.put("throwsFor", throwsFor.toString());
        map.put("throwResult", throwResult+"");
        return new JSONObject(map).toString();
    }

    @Override
    public boolean useTool(int idx) {
        return false;
    }

    @Override
    public boolean canUseTools() {
        return false;
    }

    @Override
    public boolean finishUsingTools() {
        return false;
    }
}
