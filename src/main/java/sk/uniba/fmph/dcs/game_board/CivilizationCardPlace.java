package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.*;

public class CivilizationCardPlace implements InterfaceFigureLocationInternal {

    private CivilizationCardDeck cardDeck;
    private CivilizationCardPlace nextCivilizationCard, prevCivilizationCard;
    private int requiredResources;
    private List<PlayerOrder> figures;
    private CivilisationCard civilisationCard;
    private boolean endOfGame = false;
    private Map<CivilisationCard, EvaluateCivilisationCardImmediateEffect> cardEffectMap;
    private boolean used;

    public CivilizationCardPlace(int requiredResources, CivilizationCardDeck cardDeck,
                                 Map<CivilisationCard, EvaluateCivilisationCardImmediateEffect> cardEffectMap) {
        this.requiredResources = requiredResources;
        this.cardDeck = cardDeck;
        this.figures = new ArrayList<>();
        Optional<CivilisationCard> opt = cardDeck.getTop();
        this.civilisationCard = opt.get(); // assume there are at least 4 cards in deck
        this.cardEffectMap = cardEffectMap;
        used = false;
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

        Effect[] payment = new Effect[requiredResources];
        int p = 0;
        // take the right amount of inputResources as payment
        for (Effect e : inputResources) {
            if (p >= requiredResources) break;
            payment[p] = e;
        }
        boolean taken = player.getPlayerBoard().takeResources(payment);
        if (!taken) { // player didn't have enough resources
            return ActionResult.FAILURE;
        }

        figures.remove(player.getPlayerOrder());
        player.getPlayerBoard().takeFigures(-1);
        // TODO evaluate effects
        // TODO end of game effects
        if (cardEffectMap.containsKey(civilisationCard)) {
            EvaluateCivilisationCardImmediateEffect eval = cardEffectMap.get(civilisationCard);

            if (civilisationCard.getImmediateEffect()[0] == ImmediateEffect.ArbitraryResource) {
//                eval.performEffect(player, new Effect[] {})
                // inputResources nemoze byt Collection
            }

            Effect e;
            switch (civilisationCard.getImmediateEffect()[0]) {
                case Wood:
                    e = Effect.WOOD;
                    break;
                case Clay:
                    e = Effect.CLAY;
                    break;
                case Stone:
                    e = Effect.STONE;
                    break;
                case Gold:
                    e = Effect.GOLD;
                    break;
                case ThrowWood:
                    e = Effect.WOOD;
                    break;
                case ThrowClay:
                    e = Effect.CLAY;
                    break;
                case ThrowStone:
                    e = Effect.STONE;
                    break;
                case ThrowGold:
                    e = Effect.GOLD;
                    break;
                case Food:
                    e = Effect.FOOD;
                    break;
                default:
                    e = null;
                    break;

            }
            if (!eval.performEffect(player, e)) {
                // problem
                throw new RuntimeException("nepodarilo sa performEffect(): ");
            }
            player.getPlayerBoard().giveEndOfGameEffect(civilisationCard.getEndOfGameEffect());

        } else {
            // problem
            throw new RuntimeException("CivilizationCard not found in map: " + civilisationCard.toString());
        }

        civilisationCard = null;

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
        if (used) return HasAction.NO_ACTION_POSSIBLE;
        return !figures.contains(player.getPlayerOrder()) ? HasAction.NO_ACTION_POSSIBLE : HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    // assumes newTurn() is called from right to left (cardPlace with requiredResources=1 is called first, requiredResources=4 last)
    @Override
    public boolean newTurn() {
        figures.clear();
        if (civilisationCard == null) {
            CivilizationCardPlace place = this;
            while (place.prevCivilizationCard != null && place.prevCivilizationCard.civilisationCard == null) {
                place = place.prevCivilizationCard;
            }

            if (place.prevCivilizationCard != null) {
                this.civilisationCard = place.prevCivilizationCard.civilisationCard;
                place.prevCivilizationCard.civilisationCard = null;
            }

        }

        if (requiredResources == 4) { // this is the left-most card
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
        return endOfGame;
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

    public CivilisationCard getCivilisationCard() {
        return civilisationCard;
    }

    public void setup(CivilizationCardPlace next, CivilizationCardPlace prev) {
        this.nextCivilizationCard = next;
        this.prevCivilizationCard = prev;
    }
}
