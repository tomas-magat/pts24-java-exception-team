package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.InterfaceToolUse;

import java.util.*;

public class CurrentThrow implements InterfaceCurrentThrow, InterfaceToolUse {

    private Effect throwsFor;
    private int throwResult;
    private InterfaceThrow throwDices;
    private Player player;
    private int throwSum, divBy;
    private List<Integer> toolsUsed;

    public CurrentThrow(InterfaceThrow throwDices) {
        this.throwDices = throwDices;
        toolsUsed = new ArrayList<>();
    }

    @Override
    public void initiate(Player player, Effect effect, int dices) {
        toolsUsed.clear();
        this.throwsFor = effect;
        this.player = player;

        List<Integer> list = new ArrayList<>();
        int[] res = throwDices.throwDices(dices);
        int sum = 0;
        for (int i = 0; i < dices; i++) {
            sum += res[i];
        }

        int divBy = 1;
        switch (effect) {
            case FOOD:
                divBy = 2;
                break;
            case WOOD:
               divBy = 3;
                break;
            case CLAY:
                divBy = 4;
                break;
            case STONE:
                divBy = 5;
                break;
            case GOLD:
                divBy = 6;
                break;
            default:
                divBy = 1;
                break;
        }

        this.throwSum = sum;
        this.divBy = divBy;
        this.throwResult = sum / divBy;
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
        if (!canUseTools()) return false;
        if (!player.getPlayerBoard().hasSufficientTools(idx)) return false;

        player.getPlayerBoard().useTool(idx);
        toolsUsed.add(idx);

        this.throwSum += idx;
        this.throwResult = this.throwSum / this.divBy;

        return true;
    }

    @Override
    public boolean canUseTools() {
        return throwsFor.isResourceOrFood();
    }

    @Override
    public boolean finishUsingTools() {
        Effect[] result = new Effect[throwResult];
        for (int i = 0; i < throwResult; i++) {
            result[i] = throwsFor;
        }
        player.getPlayerBoard().giveEffect(result);
        return true;
    }
}
