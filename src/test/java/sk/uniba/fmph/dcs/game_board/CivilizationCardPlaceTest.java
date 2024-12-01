package sk.uniba.fmph.dcs.game_board;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Stack;

import static org.junit.Assert.*;

public class CivilizationCardPlaceTest {

    Player player1, player2;
    PlayerBoardMock playerBoardMock1, playerBoardMock2;
    PlayerOrder playerOrder1, playerOrder2;
    CivilizationCardPlace civilizationCardPlace1, civilizationCardPlace2, civilizationCardPlace3, civilizationCardPlace4;
    CivilizationCardDeck civilizationCardDeck;


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
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Clay}, new EndOfGameEffect[]{}));
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Stone}, new EndOfGameEffect[]{}));
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Gold}, new EndOfGameEffect[]{}));
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Food}, new EndOfGameEffect[]{}));
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Point}, new EndOfGameEffect[]{}));
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.Card}, new EndOfGameEffect[]{}));
        stack.add(new CivilisationCard(new ImmediateEffect[]{ImmediateEffect.AllPlayersTakeReward}, new EndOfGameEffect[]{}));
        civilizationCardDeck = new CivilizationCardDeck(stack);
        civilizationCardPlace1 = new CivilizationCardPlace(1, civilizationCardDeck, null);
        civilizationCardPlace2 = new CivilizationCardPlace(2, civilizationCardDeck, civilizationCardPlace1);
        civilizationCardPlace3 = new CivilizationCardPlace(3, civilizationCardDeck, civilizationCardPlace2);
        civilizationCardPlace4 = new CivilizationCardPlace(4, civilizationCardDeck, civilizationCardPlace3);
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
}
