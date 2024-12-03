package sk.uniba.fmph.dcs.game_board;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.*;

import static org.junit.Assert.*;

public class CivilizationCardPlaceTest {

    Player player1, player2;
    PlayerBoardMock playerBoardMock1, playerBoardMock2;
    PlayerOrder playerOrder1, playerOrder2;
    CivilizationCardPlace civilizationCardPlace1, civilizationCardPlace2, civilizationCardPlace3, civilizationCardPlace4;
    CivilizationCardDeck civilizationCardDeck;
    Map<CivilisationCard, EvaluateCivilisationCardImmediateEffect> cardEffectMap;


    @Before
    public void setup() {
        playerBoardMock1 = new PlayerBoardMock();
        playerBoardMock2 = new PlayerBoardMock();
        playerOrder1 = new PlayerOrder(0, 4);
        playerOrder2 = new PlayerOrder(1, 4);
        player1 = new Player(playerOrder1, playerBoardMock1);
        player2 = new Player(playerOrder2, playerBoardMock2);
        Stack<CivilisationCard> stack = new Stack<>();
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Wood}, new EndOfGameEffect[]{}));
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Card}, new EndOfGameEffect[]{}));
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Food}, new EndOfGameEffect[]{}));
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Point}, new EndOfGameEffect[]{}));
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.ArbitraryResource}, new EndOfGameEffect[]{}));
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.AllPlayersTakeReward}, new EndOfGameEffect[]{}));
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.ThrowStone}, new EndOfGameEffect[]{}));
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Clay}, new EndOfGameEffect[]{EndOfGameEffect.Pottery}));
        civilizationCardDeck = new CivilizationCardDeck(stack);

        cardEffectMap = new HashMap<>();
        for (int i = 0; i < 8; i++)
            cardEffectMap.put(stack.get(i), new EvaluateMock());

        civilizationCardPlace1 = new CivilizationCardPlace(1, civilizationCardDeck, cardEffectMap);
        civilizationCardPlace2 = new CivilizationCardPlace(2, civilizationCardDeck, cardEffectMap);
        civilizationCardPlace3 = new CivilizationCardPlace(3, civilizationCardDeck, cardEffectMap);
        civilizationCardPlace4 = new CivilizationCardPlace(4, civilizationCardDeck, cardEffectMap);

        civilizationCardPlace1.setup(null, civilizationCardPlace2);
        civilizationCardPlace2.setup(civilizationCardPlace1, civilizationCardPlace3);
        civilizationCardPlace3.setup(civilizationCardPlace2, civilizationCardPlace4);
        civilizationCardPlace4.setup(civilizationCardPlace3, null);
    }

    @Test
    public void testPlaceFiguresNotEnoughFigures() {
        playerBoardMock1.expectedHasFigures = false;
        assertFalse(civilizationCardPlace1.placeFigures(player1, 1));
        assertEquals(playerBoardMock1.takeFiguresAmount, 0);

        playerBoardMock1.expectedHasFigures = true;
        playerBoardMock1.takeFiguresAmount = 0;
        assertFalse(civilizationCardPlace1.placeFigures(player1, 2));
        assertEquals(playerBoardMock1.takeFiguresAmount, 0);

        playerBoardMock2.expectedHasFigures = false;
        assertFalse(civilizationCardPlace1.placeFigures(player2, 1));
        assertEquals(playerBoardMock2.takeFiguresAmount, 0);
    }

    @Test
    public void testPlaceFigures() {
        playerBoardMock1.expectedHasFigures = true;
        playerBoardMock2.expectedHasFigures = true;
        assertTrue(civilizationCardPlace1.placeFigures(player1, 1));
        assertFalse(civilizationCardPlace1.placeFigures(player1, 1));
        assertFalse(civilizationCardPlace1.placeFigures(player2, 1));
    }

    @Test
    public void testSkipAction() {
        assertFalse(civilizationCardPlace1.skipAction(player1));

        playerBoardMock1.expectedHasFigures = true;
        assertTrue(civilizationCardPlace1.placeFigures(player1, 1));
        assertFalse(civilizationCardPlace1.skipAction(player2));
        assertTrue(civilizationCardPlace1.skipAction(player1));
    }

    @Test
    public void testMakeAction() {
        Collection<Effect> inputResources = new ArrayList<>(Arrays.asList(Effect.WOOD));
        Collection<Effect> outputResources = new ArrayList<>();

        assertEquals(civilizationCardPlace1.makeAction(player1, inputResources, outputResources), ActionResult.FAILURE);

        playerBoardMock1.expectedHasFigures = true;
        assertTrue(civilizationCardPlace1.placeFigures(player1, 1));
        assertEquals(civilizationCardPlace1.makeAction(player1, inputResources, outputResources), ActionResult.FAILURE); // player has no resources

        playerBoardMock1.expectedHasResources = true;
        CivilisationCard card1 = civilizationCardPlace1.getCivilisationCard();
        assertEquals(civilizationCardPlace1.makeAction(player2, inputResources, outputResources), ActionResult.FAILURE);
        assertEquals(civilizationCardPlace1.makeAction(player1, inputResources, outputResources), ActionResult.ACTION_DONE);
        assertEquals(playerBoardMock1.takeFiguresAmount, -1);
        assertArrayEquals(playerBoardMock1.takenResources, new Effect[] {Effect.WOOD});

        EvaluateMock eval = (EvaluateMock) cardEffectMap.get(card1);
        assertEquals(eval.effect, Effect.CLAY);
        assertArrayEquals(playerBoardMock1.givenEndOfGameEffects, new EndOfGameEffect[] {EndOfGameEffect.Pottery});
    }

    @Test
    public void testNewTurnTaken_1_2() {
        Collection<Effect> inputResources1 = new ArrayList<>(Arrays.asList(Effect.WOOD));
        Collection<Effect> outputResources1 = new ArrayList<>();
        Collection<Effect> inputResources2 = new ArrayList<>(Arrays.asList(Effect.CLAY, Effect.STONE));
        Collection<Effect> outputResources2 = new ArrayList<>();

        playerBoardMock1.expectedHasFigures = true;
        playerBoardMock1.expectedHasResources = true;
        civilizationCardPlace1.placeFigures(player1, 1);

        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.expectedHasResources = true;
        civilizationCardPlace2.placeFigures(player2, 1);

        assertEquals(civilizationCardPlace1.makeAction(player1, inputResources1, outputResources1), ActionResult.ACTION_DONE);
        assertEquals(civilizationCardPlace2.makeAction(player2, inputResources2, outputResources2), ActionResult.ACTION_DONE);

        assertFalse(civilizationCardPlace1.newTurn());
        assertFalse(civilizationCardPlace2.newTurn());
        assertFalse(civilizationCardPlace3.newTurn());
        assertFalse(civilizationCardPlace4.newTurn());

        assertEquals(civilizationCardPlace1.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.AllPlayersTakeReward}, new EndOfGameEffect[]{}));
        assertEquals(civilizationCardPlace2.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.ArbitraryResource}, new EndOfGameEffect[]{}));
        assertEquals(civilizationCardPlace3.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Point}, new EndOfGameEffect[]{}));
        assertEquals(civilizationCardPlace4.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Food}, new EndOfGameEffect[]{}));
    }

    @Test
    public void testNewTurnTaken_2_3() {
        Collection<Effect> inputResources1 = new ArrayList<>(Arrays.asList(Effect.WOOD, Effect.GOLD));
        Collection<Effect> outputResources1 = new ArrayList<>();
        Collection<Effect> inputResources2 = new ArrayList<>(Arrays.asList(Effect.CLAY, Effect.STONE, Effect.WOOD));
        Collection<Effect> outputResources2 = new ArrayList<>();

        playerBoardMock1.expectedHasFigures = true;
        playerBoardMock1.expectedHasResources = true;
        civilizationCardPlace2.placeFigures(player1, 1);

        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.expectedHasResources = true;
        civilizationCardPlace3.placeFigures(player2, 1);

        assertEquals(civilizationCardPlace2.makeAction(player1, inputResources1, outputResources1), ActionResult.ACTION_DONE);
        assertEquals(civilizationCardPlace3.makeAction(player2, inputResources2, outputResources2), ActionResult.ACTION_DONE);

        assertFalse(civilizationCardPlace1.newTurn());
        assertFalse(civilizationCardPlace2.newTurn());
        assertFalse(civilizationCardPlace3.newTurn());
        assertFalse(civilizationCardPlace4.newTurn());

        assertEquals(civilizationCardPlace1.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Clay}, new EndOfGameEffect[]{EndOfGameEffect.Pottery}));
        assertEquals(civilizationCardPlace2.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.ArbitraryResource}, new EndOfGameEffect[]{}));
        assertEquals(civilizationCardPlace3.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Point}, new EndOfGameEffect[]{}));
        assertEquals(civilizationCardPlace4.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Food}, new EndOfGameEffect[]{}));
    }

    @Test
    public void testNewTurnTaken_All() {
        Collection<Effect> inputResources1 = new ArrayList<>(Arrays.asList(Effect.WOOD));
        Collection<Effect> outputResources1 = new ArrayList<>();
        Collection<Effect> inputResources2 = new ArrayList<>(Arrays.asList(Effect.CLAY, Effect.STONE));
        Collection<Effect> outputResources2 = new ArrayList<>();
        Collection<Effect> inputResources3 = new ArrayList<>(Arrays.asList(Effect.WOOD, Effect.GOLD, Effect.CLAY));
        Collection<Effect> outputResources3 = new ArrayList<>();
        Collection<Effect> inputResources4 = new ArrayList<>(Arrays.asList(Effect.CLAY, Effect.STONE, Effect.WOOD, Effect.WOOD));
        Collection<Effect> outputResources4 = new ArrayList<>();

        playerBoardMock1.expectedHasFigures = true;
        playerBoardMock1.expectedHasResources = true;
        civilizationCardPlace1.placeFigures(player1, 1);
        civilizationCardPlace3.placeFigures(player1, 1);

        playerBoardMock2.expectedHasFigures = true;
        playerBoardMock2.expectedHasResources = true;
        civilizationCardPlace2.placeFigures(player2, 1);
        civilizationCardPlace4.placeFigures(player2, 1);


        assertEquals(civilizationCardPlace3.makeAction(player1, inputResources3, outputResources3), ActionResult.ACTION_DONE);
        assertEquals(civilizationCardPlace4.makeAction(player2, inputResources4, outputResources4), ActionResult.ACTION_DONE);
        assertEquals(civilizationCardPlace1.makeAction(player1, inputResources1, outputResources1), ActionResult.ACTION_DONE);
        assertEquals(civilizationCardPlace2.makeAction(player2, inputResources2, outputResources2), ActionResult.ACTION_DONE);

        assertFalse(civilizationCardPlace1.newTurn());
        assertFalse(civilizationCardPlace2.newTurn());
        assertFalse(civilizationCardPlace3.newTurn());
        assertFalse(civilizationCardPlace4.newTurn());

        assertEquals(civilizationCardPlace1.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Point}, new EndOfGameEffect[]{}));
        assertEquals(civilizationCardPlace2.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Food}, new EndOfGameEffect[]{}));
        assertEquals(civilizationCardPlace3.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Card}, new EndOfGameEffect[]{}));
        assertEquals(civilizationCardPlace4.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Wood}, new EndOfGameEffect[]{}));
    }

    @Test
    public void testEndOfGame() {
        testNewTurnTaken_All();

        Collection<Effect> inputResources1 = new ArrayList<>(Arrays.asList(Effect.WOOD, Effect.STONE));
        Collection<Effect> outputResources1 = new ArrayList<>();

        playerBoardMock1.expectedHasFigures = true;
        playerBoardMock1.expectedHasResources = true;
        civilizationCardPlace2.placeFigures(player1, 1);
        assertEquals(civilizationCardPlace2.makeAction(player1, inputResources1, outputResources1), ActionResult.ACTION_DONE);

        assertFalse(civilizationCardPlace1.newTurn());
        assertFalse(civilizationCardPlace2.newTurn());
        assertFalse(civilizationCardPlace3.newTurn());
        assertTrue(civilizationCardPlace4.newTurn());

        assertEquals(civilizationCardPlace1.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Point}, new EndOfGameEffect[]{}));
        assertEquals(civilizationCardPlace2.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Card}, new EndOfGameEffect[]{}));
        assertEquals(civilizationCardPlace3.getCivilisationCard(), new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Wood}, new EndOfGameEffect[]{}));
        assertNull(civilizationCardPlace4.getCivilisationCard());
    }


    private static class PlayerBoardMock implements InterfacePlayerBoardGameBoard {
        public boolean expectedHasFigures;
        public int takeFiguresAmount = 0;
        public Effect[] givenEffects;
        public EndOfGameEffect[] givenEndOfGameEffects;
        public boolean expectedHasResources;
        public Effect[] takenResources;

        @Override
        public void giveEffect(Effect[] stuff) {
            this.givenEffects = stuff;
        }

        @Override
        public void giveEndOfGameEffect(EndOfGameEffect[] stuff) {
            this.givenEndOfGameEffects = stuff;
        }

        @Override
        public boolean takeResources(Effect[] stuff) {
            this.takenResources = stuff;
            return expectedHasResources;
        }

        @Override
        public boolean takeFigures(int count) {
            takeFiguresAmount = count;
            return expectedHasFigures;
        }

        @Override
        public boolean hasFigures(int count) {
            return expectedHasFigures;
        }

        @Override
        public boolean hasSufficientTools(int goal) {
            return false;
        }

        @Override
        public OptionalInt useTool(int idx) {
            return null;
        }

        @Override
        public boolean addNewFigure() {
            return false;
        }
    }

    private static class EvaluateMock implements EvaluateCivilisationCardImmediateEffect {
        public Effect effect;
        public boolean expectedPerformEffect = true;
        @Override
        public boolean performEffect(Player player, Effect choice) {
            this.effect = choice;
            return expectedPerformEffect;
        }
    }
}
