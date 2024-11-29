package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.ArrayList;
import java.util.Collection;

public class GetSomethingThrow implements EvaluateCivilisationCardImmediateEffect {
    private ArrayList<Effect> resource;
    private CurrentThrow currentThrow;

    public GetSomethingThrow(final Collection<Effect> resources, CurrentThrow currentThrow) {
        this.resource = new ArrayList<Effect>();
        this.currentThrow = currentThrow;
        for (Effect resource : resources) {
            if (!resource.isResource()) {
                throw new IllegalArgumentException("Resources must be resource");
            }
            this.resource.add(resource);
        }
    }

    @Override
    public boolean performEffect(Player player, Effect choice) {
        if (resource != null && !resource.isEmpty()) {
            player.getPlayerBoard().giveEffect(new Effect[]{choice});
            System.out.println("Effect " + choice + " applied to player " + player.getPlayerOrder());
            return true;
        }

        return false;
    }
}
