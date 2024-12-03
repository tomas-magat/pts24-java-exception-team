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

    @Override
    public boolean equals(final Object obj) throws IllegalArgumentException {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CivilisationCard other = (CivilisationCard) obj;
        if (!Arrays.equals(immediateEffect, other.immediateEffect)) {
            return false;
        }
        if (!Arrays.equals(endOfGameEffect, other.endOfGameEffect)) {
            return false;
        }
        return true;
    }

}
