package sk.uniba.fmph.dcs.game_board;

import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;
import sk.uniba.fmph.dcs.stone_age.InterfacePlayerBoardGameBoard;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import static org.junit.Assert.*;

import java.util.*;

class PlayerBoardMock implements InterfacePlayerBoardGameBoard {
    private int figureCount = 4;
    private HashMap<Effect, Integer> resources = new HashMap<>();

    public int getFigureCount() {
        return figureCount;
    }

    @Override
    public void giveEffect(Effect[] stuff) {
        for (Effect effect : stuff) {
            resources.put(effect, resources.getOrDefault(effect, 0) + 1);
        }
    }

    @Override
    public void giveEndOfGameEffect(EndOfGameEffect[] stuff) {
        // End game effects are not used in this test
    }

    @Override
    public boolean takeResources(Effect[] stuff) {
        for (Effect effect : stuff) {
            if (resources.getOrDefault(effect, 0) <= 0) {
                return false; // Not enough resources
            }
            resources.put(effect, resources.get(effect) - 1); // Subtract resource
        }
        return true;
    }

    @Override
    public boolean takeFigures(int count) {
        figureCount -= count;
        return figureCount >= 0;
    }

    @Override
    public boolean hasFigures(int count) {
        return count <= figureCount;
    }

    @Override
    public boolean hasSufficientTools(int goal) {
        return false; // Simplified for this test
    }

    @Override
    public OptionalInt useTool(int idx) {
        return OptionalInt.empty();
    }

    @Override
    public boolean addNewFigure() {
        figureCount++;
        return true;
    }
}

public class AllPlayersTakeRewardTest {

    @Test
    public void testRewardMenuInitialization() {
        Player player1 = new Player(new PlayerOrder(1, 4), new PlayerBoardMock());
        Player player2 = new Player(new PlayerOrder(2, 4), new PlayerBoardMock());
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Effect> rewards = new ArrayList<>();
        rewards.add(Effect.FOOD);
        rewards.add(Effect.WOOD);
        RewardMenu rewardMenu = new RewardMenu(players);
        rewardMenu.initiate(rewards);

        assertEquals("RewardMenu should have 2 rewards", 2, rewardMenu.getMenu().size());
        assertEquals("RewardMenu should have 2 players", 2, rewardMenu.getPlayers().size());
    }

    @Test
    public void testSelectReward() {
        // Setting up mock players and RewardMenu
        Player player1 = new Player(new PlayerOrder(1, 4), new PlayerBoardMock());
        Player player2 = new Player(new PlayerOrder(2, 4), new PlayerBoardMock());
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Effect> rewards = new ArrayList<>();
        rewards.add(Effect.FOOD);
        rewards.add(Effect.WOOD);
        RewardMenu rewardMenu = new RewardMenu(players);
        rewardMenu.initiate(rewards);

        AllPlayersTakeReward allPlayersTakeReward = new AllPlayersTakeReward();
        allPlayersTakeReward.initiate(rewardMenu);

        // Player 1 selects a reward
        assertTrue("Player 1 should be able to select a reward", allPlayersTakeReward.takeReward(player1.getPlayerOrder(), Effect.FOOD));

        // Player 2 selects a reward
        assertTrue("Player 2 should be able to select a reward", allPlayersTakeReward.takeReward(player2.getPlayerOrder(), Effect.WOOD));

        // Verify the selected rewards
        Map<Player, Effect> selectedRewards = allPlayersTakeReward.getSelectedRewards();
        assertEquals("Player 1 should have selected FOOD", Effect.FOOD, selectedRewards.get(player1));
        assertEquals("Player 2 should have selected WOOD", Effect.WOOD, selectedRewards.get(player2));
    }

    @Test
    public void testInvalidRewardSelection() {
        // Setting up mock players and RewardMenu
        Player player1 = new Player(new PlayerOrder(1, 4), new PlayerBoardMock());
        Player player2 = new Player(new PlayerOrder(2, 4), new PlayerBoardMock());
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Effect> rewards = new ArrayList<>();
        rewards.add(Effect.FOOD);
        rewards.add(Effect.WOOD);

        RewardMenu rewardMenu = new RewardMenu(players);
        rewardMenu.initiate(rewards);

        AllPlayersTakeReward allPlayersTakeReward = new AllPlayersTakeReward();
        allPlayersTakeReward.initiate(rewardMenu);

        // Player 1 selects a reward
        assertTrue("Player 1 should be able to select a reward", allPlayersTakeReward.takeReward(player1.getPlayerOrder(), Effect.FOOD));

        System.out.println(rewardMenu.state());

        // Player 2 attempts to select an invalid reward
        assertFalse("Player 2 should not be able to select a non-existing reward", allPlayersTakeReward.takeReward(player2.getPlayerOrder(), Effect.STONE));
    }

    @Test
    public void testAllPlayersHaveSelected() {
        // Setting up mock players and RewardMenu
        Player player1 = new Player(new PlayerOrder(1, 4), new PlayerBoardMock());
        Player player2 = new Player(new PlayerOrder(2, 4), new PlayerBoardMock());
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<Effect> rewards = new ArrayList<>();
        rewards.add(Effect.FOOD);
        rewards.add(Effect.WOOD);
        RewardMenu rewardMenu = new RewardMenu(players);
        rewardMenu.initiate(rewards);

        AllPlayersTakeReward allPlayersTakeReward = new AllPlayersTakeReward();
        allPlayersTakeReward.initiate(rewardMenu);

        // Player 1 and Player 2 make their selections
        allPlayersTakeReward.takeReward(player1.getPlayerOrder(), Effect.FOOD);
        allPlayersTakeReward.takeReward(player2.getPlayerOrder(), Effect.WOOD);

        // Verify that all players have selected their rewards
        assertTrue("All players should have selected their rewards", allPlayersTakeReward.allPlayersHaveSelected());
    }

    @Test
    public void testStateMethod() {
        // Setting up mock players and RewardMenu
        Player player1 = new Player(new PlayerOrder(1, 4), new PlayerBoardMock());
        Player player2 = new Player(new PlayerOrder(2, 4), new PlayerBoardMock());
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        RewardMenu rewardMenu = new RewardMenu(players);
        List<Effect> rewards = new ArrayList<>();
        rewards.add(Effect.FOOD);
        rewards.add(Effect.WOOD);
        rewardMenu.initiate(rewards);

        AllPlayersTakeReward allPlayersTakeReward = new AllPlayersTakeReward();
        allPlayersTakeReward.initiate(rewardMenu);

        // Player 1 selects a reward
        allPlayersTakeReward.takeReward(player1.getPlayerOrder(), Effect.FOOD);

        // Verify the state of the reward menu
        String expectedState = "AllPlayersTakeReward state:\n" +
                "Available Rewards: RewardMenu state: [FOOD, WOOD]\n" +
                "Selected Rewards:\n" +
                "PlayerOrder [order=1] -> FOOD\n" +
                "Players yet to select: PlayerOrder [order=2] ";
        assertEquals("State method should return the correct state", expectedState, allPlayersTakeReward.state());
    }
}
