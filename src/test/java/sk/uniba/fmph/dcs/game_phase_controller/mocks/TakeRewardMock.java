package sk.uniba.fmph.dcs.game_phase_controller.mocks;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.InterfaceTakeReward;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.HashSet;
import java.util.Set;

public class TakeRewardMock implements InterfaceTakeReward {

    public Set<PlayerOrder> takenReward = new HashSet<>();
    public boolean expectedTakeReward;
    public HasAction expectedHasAction;
    @Override
    public boolean takeReward(PlayerOrder player, Effect reward) {

        if(takenReward.contains(player)) {
            return false;
        }

        takenReward.add(player);
        return expectedTakeReward;
    }

    @Override
    public HasAction tryMakeAction(PlayerOrder player) {

        if(takenReward.contains(player)) {
            return HasAction.NO_ACTION_POSSIBLE;
        }

        if(player.getPlayers() - 1 == takenReward.size()) {
            return HasAction.AUTOMATIC_ACTION_DONE;
        }
        return expectedHasAction;
    }
}
