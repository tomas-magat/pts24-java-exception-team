package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

public interface InterfaceCurrentThrow extends InterfaceToolUse {
    void initiate(Player player, Effect effect, int dices);
    int getThrowResult();
    String state();
}
