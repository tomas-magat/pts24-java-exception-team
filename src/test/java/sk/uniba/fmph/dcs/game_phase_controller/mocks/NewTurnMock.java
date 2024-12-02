package sk.uniba.fmph.dcs.game_phase_controller.mocks;

import sk.uniba.fmph.dcs.stone_age.InterfaceNewTurn;

public class NewTurnMock implements InterfaceNewTurn {

    public boolean newTurnCalled = false;
    @Override
    public void newTurn() {

        newTurnCalled = true;
    }
}
