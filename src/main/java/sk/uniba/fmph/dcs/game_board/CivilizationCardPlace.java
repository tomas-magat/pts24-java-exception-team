package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.*;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import javax.swing.*;
import java.util.*;

public class CivilizationCardPlace implements InterfaceFigureLocationInternal {

    private CivilizationCardDeck cardDeck;
    private CivilizationCardPlace nextCivilizationCard, prevCivilizationCard;
    private int requiredResources;
    private List<PlayerOrder> figures;
    private CivilisationCard civilisationCard;
    private boolean endOfGame = false;
//    private Map<CivilisationCard, EvaluateCivilisationCardImmediateEffect> cardEffectMap;
    private InterfaceCurrentThrow currentThrow;
    private InterfaceRewardMenu rewardMenu;
    private InterfaceThrow throwDices;

    public CivilizationCardPlace(int requiredResources, CivilizationCardDeck cardDeck, InterfaceCurrentThrow currentThrow,
                                 InterfaceRewardMenu rewardMenu, InterfaceThrow throwDices) {
        this.requiredResources = requiredResources;
        this.cardDeck = cardDeck;
        this.figures = new ArrayList<>();
        Optional<CivilisationCard> opt = cardDeck.getTop();
        this.currentThrow = currentThrow;
        this.rewardMenu = rewardMenu;
        this.civilisationCard = opt.get(); // assume there are at least 4 cards in deck
        this.throwDices = throwDices;
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
            p++;
        }
        if (p != requiredResources) return ActionResult.FAILURE; // wrong amount of resources given
        boolean taken = player.getPlayerBoard().takeResources(payment);
        if (!taken) { // player didn't have enough resources
            return ActionResult.FAILURE;
        }

        figures.remove(player.getPlayerOrder());
        player.getPlayerBoard().takeFigures(-1);

        // end of game effects
        for (EndOfGameEffect endOfGameEffect : civilisationCard.getEndOfGameEffect()) {
            player.getPlayerBoard().giveEndOfGameEffect(new EndOfGameEffect[] {endOfGameEffect});
        }

        ActionResult returnValue = ActionResult.ACTION_DONE;
        for (ImmediateEffect immediateEffect : civilisationCard.getImmediateEffect()) {
            EvaluateCivilisationCardImmediateEffect eval;
            switch (immediateEffect) {
                case Wood:
                    eval = new GetSomethingFixed(Arrays.asList(Effect.WOOD));
                    eval.performEffect(player, Effect.WOOD);
                    break;
                case Clay:
                    eval = new GetSomethingFixed(Arrays.asList(Effect.CLAY));
                    eval.performEffect(player, Effect.CLAY);
                    break;
                case Stone:
                    eval = new GetSomethingFixed(Arrays.asList(Effect.STONE));
                    eval.performEffect(player, Effect.STONE);
                    break;
                case Gold:
                    eval = new GetSomethingFixed(Arrays.asList(Effect.GOLD));
                    eval.performEffect(player, Effect.GOLD);
                    break;
                case Food:
                    eval = new GetSomethingFixed(Arrays.asList(Effect.FOOD));
                    eval.performEffect(player, Effect.FOOD);
                    break;
                case Point:
                    eval = new GetSomethingFixed(Arrays.asList(Effect.BUILDING));
                    eval.performEffect(player, Effect.BUILDING); // TODO not Building
                    break;
                case ThrowWood:
                    eval = new GetSomethingThrow(Effect.WOOD, currentThrow);
                    eval.performEffect(player, Effect.WOOD);
                    returnValue = ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE;
                    break;
                case ThrowClay:
                    eval = new GetSomethingThrow(Effect.CLAY, currentThrow);
                    eval.performEffect(player, Effect.CLAY);
                    returnValue = ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE;
                    break;
                case ThrowStone:
                    eval = new GetSomethingThrow(Effect.STONE, currentThrow);
                    eval.performEffect(player, Effect.STONE);
                    returnValue = ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE;
                    break;
                case ThrowGold:
                    eval = new GetSomethingThrow(Effect.GOLD, currentThrow);
                    eval.performEffect(player, Effect.GOLD);
                    returnValue = ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE;
                    break;
                case Card:
                    eval = new GetCard(currentThrow, cardDeck);
                    eval.performEffect(player, null); // Effect is null
                    break;
                case ArbitraryResource:
                    eval = new GetChoice();
                    for (Effect e : outputResources) {
                        eval.performEffect(player, e); // check is done in GetChoice
                    }
                    break;
                case AllPlayersTakeReward:
                    eval = new AllPlayersTakeReward(player.getPlayerOrder().getPlayers(), rewardMenu, throwDices);
//                    eval.performEffect(player, null);
                    returnValue = ActionResult.ACTION_DONE_ALL_PLAYERS_TAKE_A_REWARD;
                    break;

            }
        }

        civilisationCard = null;

        return returnValue;
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
        if (civilisationCard == null) return HasAction.NO_ACTION_POSSIBLE;
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
