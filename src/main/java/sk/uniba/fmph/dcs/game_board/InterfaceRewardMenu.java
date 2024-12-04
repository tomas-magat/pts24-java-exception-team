package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.InterfaceTakeReward;

import java.util.List;

public interface InterfaceRewardMenu extends InterfaceTakeReward {

    void initiate(final List<Effect> menu);
    String state();
}
