package sk.uniba.fmph.dcs.game_phase_controller.mocks;

import sk.uniba.fmph.dcs.stone_age.InterfaceToolUse;

public class ToolUseMock implements InterfaceToolUse {

    public boolean useToolsExpectedBoolean;
    public boolean expectedCanUseTools;
    public boolean expectedFinishUsingTools;

    @Override
    public boolean useTool(int idx) {

        return useToolsExpectedBoolean;
    }

    @Override
    public boolean canUseTools() {

        return expectedCanUseTools;
    }

    @Override
    public boolean finishUsingTools() {

        return expectedFinishUsingTools;
    }
}
