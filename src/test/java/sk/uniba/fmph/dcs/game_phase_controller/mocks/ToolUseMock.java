package sk.uniba.fmph.dcs.game_phase_controller.mocks;

import sk.uniba.fmph.dcs.stone_age.InterfaceToolUse;

public class ToolUseMock implements InterfaceToolUse {

    public boolean expectedUseTool;
    public boolean expectedCanUseTools;
    public boolean expectedFinishUsingTools;
    public int maxToolsUses = Integer.MAX_VALUE;

    @Override
    public boolean useTool(int idx) {

        if(!expectedUseTool || maxToolsUses - 1 < 0) {
            return false;
        }

        maxToolsUses--;
        return true;
    }

    @Override
    public boolean canUseTools() {

        if(maxToolsUses <= 0){
            return false;
        }

        return expectedCanUseTools;
    }

    @Override
    public boolean finishUsingTools() {

        return expectedFinishUsingTools;
    }
}
