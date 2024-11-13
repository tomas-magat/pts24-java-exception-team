package sk.uniba.fmph.dcs.player_board;

import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                if (tools.get(i) == minToolNum) tools.set(i, minToolNum++);
            }
        } else {
            // temporary error handler
            System.out.println("Cannot add new tool, all tools already have the maximum value");
        }
    }

    public void addSingleUseTool(int strength) {
        // TODO: resolve how to store the single-use tool to be able to recognize
        //  whether it was used or not (how do we know the client - player/test wants
        //  to use THIS tool)
        singleUseTool = strength;
    }
    
    public void useTool(int index) {
        assert (index <= 3 && index >= 0);
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
        for (int i = 0; i < maxToolCount; i++) {
            if (!usedTools.get(i) && tools.get(i) == goal) return true;
        }
        return false;
    }

    public int getToolCount() {
        int resultCount = 0;
        for (int i = 0; i < maxToolCount; i++) {
            resultCount += tools.get(i);
        }
        return resultCount;
    }

    @Override
    public String state() {
        // TODO: what should state strings look like?
        return "";
    }
}
