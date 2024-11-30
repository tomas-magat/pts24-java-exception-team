package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

public class GetChoice implements EvaluateCivilisationCardImmediateEffect {
    private boolean isUsed;

    public GetChoice() {
        this.isUsed = false;
    }


    @Override
    public boolean performEffect(Player player, Effect choice) {

        return false;
    }

    public boolean isUsed() {
        return this.isUsed;
    }
}
