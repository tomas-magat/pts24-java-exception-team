package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

public class GetChoice implements EvaluateCivilisationCardImmediateEffect {
    private boolean isUsed;
    private int numberOfResources;

    public GetChoice(int numberOfResources) {
        this.isUsed = false;
        this.numberOfResources = numberOfResources;
    }

    @Override
    public boolean performEffect(Player player, Effect choice) {
        if (this.isUsed){
            return false;
        }

        if (choice == null || !choice.isResource()) {
            return false;
        }

        player.getPlayerBoard().giveEffect(new Effect[] {choice});
        numberOfResources++;

        if (numberOfResources == 2) {
            this.isUsed = true;
        }

        return true;
    }

    public boolean isUsed() {
        return this.isUsed;
    }
}
