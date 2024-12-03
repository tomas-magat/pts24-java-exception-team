package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GetSomethingThrow implements EvaluateCivilisationCardImmediateEffect {
    private final Effect resource;
    private final CurrentThrow currentThrow;

    public GetSomethingThrow(Effect resource, CurrentThrow currentThrow) {
        if (!resource.isResource()) {
            throw new IllegalArgumentException("Resources must be resource");
        }
        this.resource = resource;
        this.currentThrow = currentThrow;
    }

    @Override
    public boolean performEffect(Player player, Effect choice) {
        if (!isValidChoice(choice)) {
            return false;
        }

        currentThrow.initiate(player, choice, 2);

        return true;
    }

    private boolean isValidChoice(Effect choice) {
        return resource == choice;
    }
}
