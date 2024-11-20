package sk.uniba.fmph.dcs.player_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;
import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;

import java.util.*;

public class PlayerTools implements InterfaceGetState {
    private List<Integer> tools;
    private List<Boolean> usedTools;
    private int singleUseTool;
    private final int maxToolCount = 3;

    public PlayerTools() {
        singleUseTool = 0;
        tools = new ArrayList<>();
        usedTools = new ArrayList<>();

        // At the beginning of the game, player has no tools on the 3 tool tiles
        for (int i = 0; i < maxToolCount; i++) {
            tools.add(0);
            usedTools.add(false);
        }
    }

    public void newTurn() {
        // Each Tool tile can only be used once per round
        for (int i = 0; i < maxToolCount; i++) {
            usedTools.set(i, false);
        }
    }

    public void addTool() {
        int minToolNum = Collections.min(tools);

        if (minToolNum < 4) {
            for (int i = 0; i < maxToolCount; i++) {
                if (tools.get(i) == minToolNum) {tools.set(i, minToolNum+1); break;}
            }
        } else {
            // temporary error handler
            System.out.println("Cannot add new tool, all tools already have the maximum value");
        }
    }

    public void addSingleUseTool(int strength) {
        singleUseTool = strength;
    }
    
    public void useTool(int index) {
        assert (index <= 3 && index >= 0);
        // useTool(3) will use singleUseTool
        if (index == 3) singleUseTool = 0;
        else {
            if (!usedTools.get(index)) {
                usedTools.set(index, true);
            } else {
                // temporary error handler
                System.out.println("This tool has been already used");
            }
        }
    }

    public boolean hasSufficientTools(int goal) {
        // primary uses single-use tools
        if (singleUseTool == goal) return true;
        for (int i = 0; i < maxToolCount; i++) {
            if (!usedTools.get(i) && tools.get(i) == goal) return true;
        }
        return false;
    }

    public int getToolCount() {
        // return value of total tool value count (for the final scoring)
        int resultCount = 0;
        for (int i = 0; i < maxToolCount; i++) {
            resultCount += tools.get(i);
        }
        return resultCount;
    }

    @Override
    public String state() {
        Map<String, String> state = new HashMap<>();
        for (int i = 0; i < maxToolCount; i++) {
            state.put("tool slot "+i, tools.get(i)+" "+(usedTools.get(i) ? "used" : "unused"));
        }
        return new JSONObject(state).toString();
    }
}
