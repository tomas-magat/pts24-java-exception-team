package sk.uniba.fmph.dcs.game_board;

import org.junit.Before;
import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

import static org.junit.Assert.*;

public class GameBoardIntegrationTest {

    PlayerBoardMock playerBoardMock1, playerBoardMock2, playerBoardMock3, playerBoardMock4;
    Player player1, player2, player3, player4;
    List<Player> players;
    GameBoard gameBoard;
    GameBoardFactory.ThrowMock throwMock;

    @Before
    public void setup() {
        playerBoardMock1 = new PlayerBoardMock();
        playerBoardMock2 = new PlayerBoardMock();
        playerBoardMock3 = new PlayerBoardMock();
        playerBoardMock4 = new PlayerBoardMock();
        player1 = new Player(new PlayerOrder(0, 4), playerBoardMock1);
        player2 = new Player(new PlayerOrder(1, 4), playerBoardMock2);
        player3 = new Player(new PlayerOrder(2, 4), playerBoardMock3);
        player4 = new Player(new PlayerOrder(3, 4), playerBoardMock4);
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        throwMock = new GameBoardFactory.ThrowMock();
        gameBoard = GameBoardFactory.createGameBoard(players, throwMock, GameBoardFactory.createCardDeck1(), GameBoardFactory.createBuildingTiles1(4));
    }

    @Test
    public void test1() {
        assertTrue(gameBoard.getCivilizationCardPlace1().placeFigures(player1, 1));
        assertTrue(gameBoard.getCivilizationCardPlace2().placeFigures(player2, 1));
        assertTrue(gameBoard.getCivilizationCardPlace3().placeFigures(player3, 1));
        assertTrue(gameBoard.getCivilizationCardPlace4().placeFigures(player4, 1));

        List<Effect> inputResources;
        List<Effect> outputResources;

        // player1 GetSomethingFixed 1 WOOD
        inputResources = new ArrayList<>(Arrays.asList(Effect.CLAY));
        outputResources = new ArrayList<>();
        assertEquals(ActionResult.ACTION_DONE, gameBoard.getCivilizationCardPlace1().makeAction(player1, inputResources, outputResources));
        assertEquals(new ArrayList<>(Arrays.asList(Effect.WOOD)), playerBoardMock1.givenEffects);
        assertEquals(new ArrayList<>(Arrays.asList(EndOfGameEffect.Pottery)), playerBoardMock1.givenEndOfGameEffects);

        // player2 GetSomethingThrow STONE
        throwMock.result = new int[] {5, 5};
        inputResources = new ArrayList<>(Arrays.asList(Effect.WOOD, Effect.CLAY));
        outputResources = new ArrayList<>();
        assertEquals(ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE, gameBoard.getCivilizationCardPlace2().makeAction(player2, inputResources, outputResources));
        assertTrue(gameBoard.getCurrentThrow().useTool(6));
        assertTrue(gameBoard.getCurrentThrow().finishUsingTools());
        assertEquals(new ArrayList<>(Arrays.asList(Effect.STONE, Effect.STONE, Effect.STONE)), playerBoardMock2.givenEffects);
        assertEquals(new ArrayList<>(Arrays.asList(EndOfGameEffect.ToolMaker)), playerBoardMock2.givenEndOfGameEffects);

        // player3 GetCard
        inputResources = new ArrayList<>(Arrays.asList(Effect.WOOD, Effect.CLAY, Effect.WOOD));
        outputResources = new ArrayList<>();
        assertEquals(ActionResult.ACTION_DONE, gameBoard.getCivilizationCardPlace3().makeAction(player3, inputResources, outputResources));
        assertEquals(new ArrayList<>(Arrays.asList(EndOfGameEffect.Farmer, EndOfGameEffect.Medicine)), playerBoardMock3.givenEndOfGameEffects);

        // player4 GetChoice
        inputResources = new ArrayList<>(Arrays.asList(Effect.WOOD, Effect.CLAY, Effect.WOOD, Effect.WOOD));
        outputResources = new ArrayList<>(Arrays.asList(Effect.GOLD, Effect.STONE));
        assertEquals(ActionResult.ACTION_DONE, gameBoard.getCivilizationCardPlace4().makeAction(player4, inputResources, outputResources));
        assertEquals(new ArrayList<>(Arrays.asList(Effect.GOLD, Effect.STONE)), playerBoardMock4.givenEffects);
        assertEquals(new ArrayList<>(Arrays.asList(EndOfGameEffect.Art)), playerBoardMock4.givenEndOfGameEffects);


        System.out.println(gameBoard.state());

        // ROUND 2
        playerBoardMock1.givenEffects.clear();
        playerBoardMock1.givenEndOfGameEffects.clear();
        playerBoardMock1.usedToolID = 0;
        playerBoardMock1.takeFiguresAmount = 0;
        playerBoardMock2.givenEffects.clear();
        playerBoardMock2.givenEndOfGameEffects.clear();
        playerBoardMock2.usedToolID = 0;
        playerBoardMock2.takeFiguresAmount = 0;
        playerBoardMock3.givenEffects.clear();
        playerBoardMock3.givenEndOfGameEffects.clear();
        playerBoardMock3.usedToolID = 0;
        playerBoardMock3.takeFiguresAmount = 0;
        playerBoardMock4.givenEffects.clear();
        playerBoardMock4.givenEndOfGameEffects.clear();
        playerBoardMock4.usedToolID = 0;
        playerBoardMock4.takeFiguresAmount = 0;

        assertFalse(gameBoard.getCivilizationCardPlace1().newTurn());
        assertFalse(gameBoard.getCivilizationCardPlace2().newTurn());
        assertFalse(gameBoard.getCivilizationCardPlace3().newTurn());
        assertFalse(gameBoard.getCivilizationCardPlace4().newTurn());

        gameBoard.getCivilizationCardPlace1().placeFigures(player1, 1);
        gameBoard.getCivilizationCardPlace2().placeFigures(player2, 1);
        gameBoard.getCivilizationCardPlace3().placeFigures(player3, 1);
        gameBoard.getCivilizationCardPlace4().placeFigures(player4, 1);

        // player1 AllPlayersTakeReward (RewardMenu test)
        throwMock.result = new int[] {4, 6, 5, 3}; // RewardMenu should contain Stone, Gold, Tool, Field
        inputResources = new ArrayList<>(Arrays.asList(Effect.CLAY));
        outputResources = new ArrayList<>();
        assertEquals(ActionResult.ACTION_DONE_ALL_PLAYERS_TAKE_A_REWARD, gameBoard.getCivilizationCardPlace1().makeAction(player1, inputResources, outputResources));
        assertEquals(new ArrayList<>(Arrays.asList(EndOfGameEffect.Shaman)), playerBoardMock1.givenEndOfGameEffects);

        assertTrue(gameBoard.getRewardMenu().takeReward(player1.getPlayerOrder(), Effect.GOLD));
        assertTrue(gameBoard.getRewardMenu().takeReward(player2.getPlayerOrder(), Effect.STONE));
        assertFalse(gameBoard.getRewardMenu().takeReward(player2.getPlayerOrder(), Effect.TOOL));
        assertTrue(gameBoard.getRewardMenu().takeReward(player3.getPlayerOrder(), Effect.TOOL));
        assertTrue(gameBoard.getRewardMenu().takeReward(player4.getPlayerOrder(), Effect.FIELD));
        assertFalse(gameBoard.getRewardMenu().takeReward(player4.getPlayerOrder(), Effect.GOLD));
        assertEquals(0, ((RewardMenu)gameBoard.getRewardMenu()).getPlayersLeftCount());

        // SKIP further actions
        assertFalse(gameBoard.getCivilizationCardPlace1().skipAction(player1));
        assertTrue(gameBoard.getCivilizationCardPlace2().skipAction(player2));
        assertTrue(gameBoard.getCivilizationCardPlace3().skipAction(player3));
        assertTrue(gameBoard.getCivilizationCardPlace4().skipAction(player4));

        // try ROUND 3
        assertFalse(gameBoard.getCivilizationCardPlace1().newTurn());
        assertFalse(gameBoard.getCivilizationCardPlace2().newTurn());
        assertFalse(gameBoard.getCivilizationCardPlace3().newTurn());
        assertTrue(gameBoard.getCivilizationCardPlace4().newTurn()); // should indicate end of game
    }



    private static class PlayerBoardMock implements InterfacePlayerBoardGameBoard {
        public boolean expectedHasFigures = true;
        public int takeFiguresAmount = 0;
        public List<Effect> givenEffects = new ArrayList<>();
        public List<EndOfGameEffect> givenEndOfGameEffects = new ArrayList<>();
        public boolean expectedHasResources = true;
        public Effect[] takenResources;
        public boolean expectedHasTools = true;
        public int usedToolID;

        @Override
        public void giveEffect(Effect[] stuff) {
            for (Effect e : stuff)
                givenEffects.add(e);
        }

        @Override
        public void giveEndOfGameEffect(EndOfGameEffect[] stuff) {
            for (EndOfGameEffect e : stuff)
                givenEndOfGameEffects.add(e);
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
            return expectedHasTools;
        }

        @Override
        public OptionalInt useTool(int idx) {
            usedToolID = idx;
            return null;
        }

        @Override
        public boolean addNewFigure() {
            return false;
        }
    }
}
