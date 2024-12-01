package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.CivilisationCard;

import java.util.*;

public class CivilizationCardDeck {

    private Stack<CivilisationCard> stack;

    public CivilizationCardDeck(Stack<CivilisationCard> stack) {
        this.stack = stack;
    }

    public Optional<CivilisationCard> getTop() {
        if (stack.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(stack.pop());
        }
    }

    public String state() {
        Map<String, String> map = new HashMap<>();
        map.put("cardDeck", stack.toString());
        return new JSONObject(map).toString();
    }
}
