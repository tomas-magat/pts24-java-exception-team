package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.ArrayList;
import java.util.List;

public class GetSomethingFixed implements EvaluateCivilisationCardImmediateEffect {
    private final List<Effect> effects;

    public GetSomethingFixed(List<Effect> effect) {
        effects = new ArrayList<>();
        effects.addAll(effect);
    }

    @Override
    public boolean performEffect(Player player, Effect choice) {
        if (effects != null && !effects.isEmpty()) {
            boolean isValidChoice = false;
            for (Effect effect : effects) {
                if (effect == choice) {
                    isValidChoice = true;
                    break;
                }
            }
            if (!isValidChoice) {
                System.out.println("Invalid choice: " + choice);
                return false;
            }
            player.getPlayerBoard().giveEffect(new Effect[]{choice});
            System.out.println("Effect " + choice + " applied to player " + player.getPlayerOrder());
            return true;
        }
        return false;
    }
}
