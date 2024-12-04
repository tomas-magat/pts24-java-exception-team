package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.InterfaceTakeReward;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllPlayersTakeReward implements EvaluateCivilisationCardImmediateEffect {
    private final InterfaceRewardMenu menu;

    public AllPlayersTakeReward(final int players, final InterfaceRewardMenu menu, InterfaceThrow throwDices) {
        this.menu = menu;
        List<Effect> list = new ArrayList<>();
        int[] result = throwDices.throwDices(players);
        for (int h : result) {
            switch (h) {
                case 1:
                    list.add(Effect.WOOD);
                    break;
                case 2:
                    list.add(Effect.CLAY);
                    break;
                case 3:
                    list.add(Effect.STONE);
                    break;
                case 4:
                    list.add(Effect.GOLD);
                    break;
                case 5:
                    list.add(Effect.TOOL);
                    break;
                case 6:
                    list.add(Effect.FIELD);
                    break;
            }
        }
        menu.initiate(list);
    }

    @Override
    public boolean performEffect(Player player, Effect choice) {
//        PlayerOrder playerOrder = player.getPlayerOrder();
//        return menu.takeReward(playerOrder, choice);
        return false;
    }
}
