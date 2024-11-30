package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.InterfaceGamePhaseController;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.ArrayList;
import java.util.List;

public class AllPlayersTakeReward implements EvaluateCivilisationCardImmediateEffect {
    private final RewardMenu menu;
    private final InterfaceGamePhaseController controller;

    public AllPlayersTakeReward(final RewardMenu menu, final InterfaceGamePhaseController controller) {
        this.menu = menu;
        this.controller = controller;
        int[] t = Throw.hod(menu.getPlayersCount());
        List<Effect> m = new ArrayList<>();
        for (int i : t) {
            switch (i) {
            case 1: // WOOD
                m.add(Effect.WOOD);
                break;
            case 2: // CLAY
                m.add(Effect.CLAY);
                break;
            case 3: // STONE
                m.add(Effect.STONE);
                break;
            case 4: // GOLD
                m.add(Effect.GOLD);
                break;
            case 5: // TOOL
                m.add(Effect.TOOL);
                break;
            default: // FIELD
                m.add(Effect.FIELD);
                break;
            }
        }
        menu.initiate(m);
    }

    @Override
    public boolean performEffect(Player player, Effect choice) {
        PlayerOrder playerOrder = player.getPlayerOrder();

        if (!menu.takeReward(playerOrder, choice)) { // try to take reward
            return false;
        }

        player.getPlayerBoard().giveEffect(new Effect[]{choice});
        return true;
    }
}
