package sk.uniba.fmph.dcs.game_phase_controller.mocks;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.InterfaceTakeReward;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

public class TakeRewardMock implements InterfaceTakeReward {

    public HasAction expectedTryMakeAction;
    public boolean expectedTakeReward;
    @Override
    public boolean takeReward(PlayerOrder player, Effect reward) {

        return expectedTakeReward;
    }

    @Override
    public HasAction tryMakeAction(PlayerOrder player) {

        return expectedTryMakeAction;
    }
}
