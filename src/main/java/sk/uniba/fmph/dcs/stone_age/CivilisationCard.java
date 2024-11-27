package sk.uniba.fmph.dcs.stone_age;

import java.util.Arrays;

public class CivilisationCard {
    private ImmediateEffect[] immediateEffect;
    private EndOfGameEffect[] endOfGameEffect;

    public CivilisationCard(ImmediateEffect[] immediateEffect, EndOfGameEffect[] endOfGameEffect) {
        this.immediateEffect = immediateEffect;
        this.endOfGameEffect = endOfGameEffect;
        /* Deep copy
        int immediateEffectLength = immediateEffect.length;
        int endOfGameEffectLength = endOfGameEffect.length;
        this.immediateEffect = Arrays.copyOf(immediateEffect, immediateEffectLength);
        this.endOfGameEffect = Arrays.copyOf(endOfGameEffect, endOfGameEffectLength);
         */
    }

    public ImmediateEffect[] getImmediateEffect() {
        return immediateEffect;
    }

    public EndOfGameEffect[] getEndOfGameEffect() {
        return endOfGameEffect;
    }

}
