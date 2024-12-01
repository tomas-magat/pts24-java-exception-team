package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.*;

public class CivilizationCardPlace implements InterfaceFigureLocationInternal {

    private CivilizationCardDeck cardDeck;
    private CivilizationCardPlace nextCivilizationCard;
    private int requiredResources;
    private List<PlayerOrder> figures;
    private CivilisationCard civilisationCard;
    private boolean endOfGame = false;

    public CivilizationCardPlace(int requiredResources, CivilizationCardDeck cardDeck, CivilizationCardPlace nextCivilizationCard) {
        this.requiredResources = requiredResources;
        this.cardDeck = cardDeck;
        this.nextCivilizationCard = nextCivilizationCard;
        this.figures = new ArrayList<>();
        Optional<CivilisationCard> opt = cardDeck.getTop();
        this.civilisationCard = opt.get(); // assume there are at least 4 cards in deck
    }

    @Override
    public boolean placeFigures(Player player, int figureCount) {
        if (tryToPlaceFigures(player, figureCount) == HasAction.NO_ACTION_POSSIBLE) return false;

        figures.add(player.getPlayerOrder());
        player.getPlayerBoard().takeFigures(1);
        return true;
    }

    @Override
    public HasAction tryToPlaceFigures(Player player, int count) {
        return  (!figures.isEmpty() || count != 1 || !player.getPlayerBoard().hasFigures(count)) ? HasAction.NO_ACTION_POSSIBLE : HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    @Override
    public ActionResult makeAction(Player player, Collection<Effect> inputResources, Collection<Effect> outputResources) {
        if (inputResources.size() < requiredResources) return ActionResult.FAILURE;
        if (tryToMakeAction(player) == HasAction.NO_ACTION_POSSIBLE) return ActionResult.FAILURE;

        Effect[] payment = new Effect[inputResources.size()];
        inputResources.toArray(payment);
        boolean taken = player.getPlayerBoard().takeResources(payment);
        if (!taken) { // player didn't have enough resources
            return ActionResult.FAILURE;
        }

        figures.remove(player.getPlayerOrder());
        player.getPlayerBoard().takeFigures(-1);
        // TODO evaluate effects
        // TODO end of game effects


        return ActionResult.ACTION_DONE;
    }

    @Override
    public boolean skipAction(Player player) {
        if (!figures.contains(player.getPlayerOrder())) return false;

        figures.remove(player.getPlayerOrder());
        player.getPlayerBoard().takeFigures(-1);
        return true;
    }

    @Override
    public HasAction tryToMakeAction(Player player) {
        return !figures.contains(player.getPlayerOrder()) ? HasAction.NO_ACTION_POSSIBLE : HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    // assumes newTurn() is called from right to left (cardPlace with requiredResources=1 is called first, requiredResources=4 last)
    @Override
    public boolean newTurn() {
        figures.clear();
        if (nextCivilizationCard != null) {
            if (nextCivilizationCard.civilisationCard == null) {
                nextCivilizationCard.civilisationCard = this.civilisationCard;
            }
        }

        if (requiredResources == 4) { // this is the first card
            // fill all empty card places with cards from the deck (or null, in that case set endOfGame=true)
            if (nextCivilizationCard.nextCivilizationCard.nextCivilizationCard.civilisationCard == null)
                nextCivilizationCard.nextCivilizationCard.nextCivilizationCard.civilisationCard = getCardOrNull();

            if (nextCivilizationCard.nextCivilizationCard.civilisationCard == null)
                nextCivilizationCard.nextCivilizationCard.civilisationCard = getCardOrNull();

            if (nextCivilizationCard.civilisationCard == null)
                nextCivilizationCard.civilisationCard = getCardOrNull();

            if (civilisationCard == null)
                civilisationCard = getCardOrNull();

        }
        return endOfGame; // TODO
    }

    public String state() {
        Map<String, String> map = new HashMap<>();
        map.put("requiredResources", requiredResources+"");
        map.put("figures", figures.toString());
        return new JSONObject(map).toString();
    }

    // sets endOfGame flag when cardDeck is empty
    private CivilisationCard getCardOrNull() {
        Optional<CivilisationCard> opt = cardDeck.getTop();
        if (opt.isEmpty()) {
            endOfGame = true;
            return null;
        } else {
            return opt.get();
        }
    }
}
