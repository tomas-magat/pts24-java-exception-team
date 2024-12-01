package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

public class GetChoice implements EvaluateCivilisationCardImmediateEffect {
    private boolean isUsedUp;
    private final int numberOfResources;

    public GetChoice(int numberOfResources) {
        this.isUsedUp = false;
        this.numberOfResources = numberOfResources;
    }

    @Override
    public boolean performEffect(Player player, Effect choice) {
        if (isUsedUp()) {
            throw new IllegalStateException("GetChoice is already used");
        }

        if (choice == null || !choice.isResource()) {
            throw new IllegalStateException("Resources must be resources");
        }

        int timesUsed = 0;

        player.getPlayerBoard().giveEffect(new Effect[]{choice});
        timesUsed++;

        if (timesUsed == numberOfResources) {
            this.isUsedUp = true;
        }

        return true;
    }

    public boolean isUsedUp() {
        return this.isUsedUp;
    }
}
