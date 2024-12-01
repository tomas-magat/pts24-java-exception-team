package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

public class GetChoice implements EvaluateCivilisationCardImmediateEffect {
    private boolean isUsedUp;
    private int numberOfResources;

    public GetChoice() {
        this.isUsedUp = false;
        this.numberOfResources = 0;
    }

    @Override
    public boolean performEffect(Player player, Effect choice) {
        if (isUsedUp()) {
            throw new IllegalStateException("GetChoice is already used");
        }

        if (choice == null || !choice.isResource()) {
            throw new IllegalStateException("Resources must be resources");
        }

        player.getPlayerBoard().giveEffect(new Effect[]{choice});
        System.out.println("Effect: " + choice + " applied to player" + player.getPlayerOrder());
        numberOfResources++;

        if (numberOfResources == 2) {
            this.isUsedUp = true;
        }

        return true;
    }

    public boolean isUsedUp() {
        return this.isUsedUp;
    }
}
