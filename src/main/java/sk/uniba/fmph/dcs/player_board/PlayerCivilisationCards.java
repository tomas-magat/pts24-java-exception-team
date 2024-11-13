package sk.uniba.fmph.dcs.player_board;

import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;
import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;

import java.util.*;

public class PlayerCivilisationCards implements InterfaceGetState {
    private Map<EndOfGameEffect, Integer> endEffectCards; // stores cardEffects which are applied at the end of a game

    public PlayerCivilisationCards() {
        endEffectCards = new HashMap<EndOfGameEffect, Integer>();
    }

    public void addEndOfGameEffects(List<EndOfGameEffect> effects) {
        for (EndOfGameEffect effect : effects) {
            endEffectCards.merge(effect, 1, Integer::sum);
        }
    }

    public int calculateEndOfGameCivilisationCardPoints(int buildings, int tools, int fields, int figures) {
        // TODO: write detailed test for this method
        int resultPoints = 0;
        List<Integer> greenCards = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            greenCards.add(0);
        }

        for (Map.Entry<EndOfGameEffect, Integer> card : endEffectCards.entrySet()) {
            switch (card.getKey()) {
                case EndOfGameEffect.Farmer: resultPoints += card.getValue()*fields;
                case EndOfGameEffect.ToolMaker: resultPoints += card.getValue()*tools;
                case EndOfGameEffect.Builder: resultPoints += card.getValue()*buildings;
                case EndOfGameEffect.Shaman: resultPoints += card.getValue()*figures;
                case EndOfGameEffect.Art: greenCards.set(0, greenCards.get(0)+1);
                case EndOfGameEffect.Medicine: greenCards.set(1, greenCards.get(1)+1);
                case EndOfGameEffect.Music: greenCards.set(2, greenCards.get(2)+1);
                case EndOfGameEffect.Pottery: greenCards.set(3, greenCards.get(3)+1);
                case EndOfGameEffect.Sundial: greenCards.set(4, greenCards.get(4)+1);
                case EndOfGameEffect.Transport: greenCards.set(5, greenCards.get(5)+1);
                case EndOfGameEffect.Weaving: greenCards.set(6, greenCards.get(6)+1);
                case EndOfGameEffect.Writing: greenCards.set(7, greenCards.get(7)+1);
            }
        }

        int cardSetSize = 0;
        do {
            for (int i = 0; i < greenCards.size(); i++) {
                if (greenCards.get(i) > 0) {
                    cardSetSize++;
                    greenCards.set(i, greenCards.get(i)-1);
                }
            }
            resultPoints += cardSetSize*cardSetSize;
        } while (cardSetSize > 0);

        return resultPoints;
    }

    @Override
    public String state() {
        // TODO
        return "";
    }
}
