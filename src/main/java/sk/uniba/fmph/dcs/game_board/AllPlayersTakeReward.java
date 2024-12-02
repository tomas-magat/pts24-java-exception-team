package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

public class AllPlayersTakeReward implements EvaluateCivilisationCardImmediateEffect {
    private final RewardMenu menu;

    public AllPlayersTakeReward(final RewardMenu menu) {
        this.menu = menu;
    }

    @Override
    public boolean performEffect(Player player, Effect choice) {
        PlayerOrder playerOrder = player.getPlayerOrder();
        return menu.takeReward(playerOrder, choice);
    }
}
