package sk.uniba.fmph.dcs.player_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;
import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;

import java.util.*;

public class PlayerCivilisationCards implements InterfaceGetState {
    private Map<EndOfGameEffect, Integer> endEffectCards; // stores cardEffects which are applied at the end of a game

    public PlayerCivilisationCards() {
        endEffectCards = new HashMap<>();
    }

    public void addEndOfGameEffects(List<EndOfGameEffect> effects) {
        for (EndOfGameEffect effect : effects) {
            endEffectCards.merge(effect, 1, Integer::sum);
        }
    }

    public int calculateEndOfGameCivilisationCardPoints(int buildings, int tools, int fields, int figures) {
        int resultPoints = 0;
        List<Integer> greenCards = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            greenCards.add(0);
        }

        for (Map.Entry<EndOfGameEffect, Integer> card : endEffectCards.entrySet()) {
            switch (card.getKey()) {
                case EndOfGameEffect.Farmer: resultPoints += card.getValue()*fields; break;
                case EndOfGameEffect.ToolMaker: resultPoints += card.getValue()*tools; break;
                case EndOfGameEffect.Builder: resultPoints += card.getValue()*buildings; break;
                case EndOfGameEffect.Shaman: resultPoints += card.getValue()*figures; break;
                case EndOfGameEffect.Art: greenCards.set(0, card.getValue()); break;
                case EndOfGameEffect.Medicine: greenCards.set(1, card.getValue()); break;
                case EndOfGameEffect.Music: greenCards.set(2, card.getValue()); break;
                case EndOfGameEffect.Pottery: greenCards.set(3, card.getValue()); break;
                case EndOfGameEffect.Sundial: greenCards.set(4, card.getValue()); break;
                case EndOfGameEffect.Transport: greenCards.set(5, card.getValue()); break;
                case EndOfGameEffect.Weaving: greenCards.set(6, card.getValue()); break;
                case EndOfGameEffect.Writing: greenCards.set(7, card.getValue()); break;
            }
        }

        int cardSetSize;
        do {
            cardSetSize = 0;
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
        Map<String, String> state = new HashMap<>();
        for (Map.Entry<EndOfGameEffect, Integer> card : endEffectCards.entrySet()) {
            switch (card.getKey()) {
                case EndOfGameEffect.Farmer: state.put("farmers", String.valueOf(card.getValue()));
                case EndOfGameEffect.ToolMaker: state.put("toolmakers", String.valueOf(card.getValue()));
                case EndOfGameEffect.Builder: state.put("builders", String.valueOf(card.getValue()));
                case EndOfGameEffect.Shaman: state.put("shamans", String.valueOf(card.getValue()));
                case EndOfGameEffect.Art: state.put("art", String.valueOf(card.getValue()));
                case EndOfGameEffect.Medicine: state.put("medicine", String.valueOf(card.getValue()));
                case EndOfGameEffect.Music: state.put("music", String.valueOf(card.getValue()));
                case EndOfGameEffect.Pottery: state.put("pottery", String.valueOf(card.getValue()));
                case EndOfGameEffect.Sundial: state.put("sundial", String.valueOf(card.getValue()));
                case EndOfGameEffect.Transport: state.put("transport", String.valueOf(card.getValue()));
                case EndOfGameEffect.Weaving: state.put("weaving", String.valueOf(card.getValue()));
                case EndOfGameEffect.Writing: state.put("writing", String.valueOf(card.getValue()));
            }
        }
        return new JSONObject(state).toString();
    }
}
