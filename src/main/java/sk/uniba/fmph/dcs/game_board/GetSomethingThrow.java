package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.ArrayList;
import java.util.Collection;

public class GetSomethingThrow implements EvaluateCivilisationCardImmediateEffect {
    private Effect[] resources;

    public GetSomethingThrow(Effect[] resources) {
        for (Effect resource : resources) {
            if (!resource.isResource()) {
                throw new IllegalArgumentException("Resources must be resource");
            }
        this.resources = resources;
        }
    }

    @Override
    public boolean performEffect(Player player, Effect choice) {
        CurrentThrow currentThrow = new CurrentThrow();

        currentThrow.initiate(player, choice, 2);

        int throwResult = currentThrow.getThrowResult();

        int resourcesGained = throwResult / choice.points();

        Effect[] gainedResources = createEffectArray(choice, resourcesGained);

        player.getPlayerBoard().giveEffect(gainedResources);

        System.out.println("Effect " + choice + " applied to player " + player.getPlayerOrder());

        return false;
    }

    private Effect[] createEffectArray(Effect effect, int count) {
        Effect[] effects = new Effect[count];
        for (int i = 0; i < count; i++) {
            effects[i] = effect;
        }
        return effects;
    }

    private boolean isAllowedResource(Effect choice) {
        for(Effect resource : resources) {
            if (resource == choice) {
                return true;
            }
        }
        return false;
    }
}
