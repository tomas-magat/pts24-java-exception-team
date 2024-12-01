package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.InterfaceToolUse;

public interface InterfaceCurrentThrow extends InterfaceToolUse {

    void initiate(Player player, Effect effect, int dices);
    String state();
}
