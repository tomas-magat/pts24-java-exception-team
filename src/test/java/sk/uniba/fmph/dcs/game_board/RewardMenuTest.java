package sk.uniba.fmph.dcs.game_board;

import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.List;
import static org.junit.Assert.*;


public class RewardMenuTest {

    @Test
    public void testState() {
        Player player1 = new Player(new PlayerOrder(1, 4), new PlayerBoardMock());
        Player player2 = new Player(new PlayerOrder(2, 4), new PlayerBoardMock());
        List<Player> players = List.of(player1, player2);

        List<Effect> rewards = List.of(Effect.FOOD, Effect.WOOD);
        RewardMenu rewardMenu = new RewardMenu(players);
        rewardMenu.initiate(rewards);

        String menuState = rewardMenu.state();
        assertTrue("State should include FOOD", menuState.contains("FOOD"));
        assertTrue("State should include WOOD", menuState.contains("WOOD"));
        assertTrue("State should include players", menuState.contains(player1.toString()));
        assertTrue("State should include players", menuState.contains(player2.toString()));
    }

    @Test
    public void testTakeReward() {
        Player player1 = new Player(new PlayerOrder(1, 4), new PlayerBoardMock());
        Player player2 = new Player(new PlayerOrder(2, 4), new PlayerBoardMock());
        List<Player> players = List.of(player1, player2);

        List<Effect> rewards = List.of(Effect.FOOD, Effect.WOOD);
        RewardMenu rewardMenu = new RewardMenu(players);
        rewardMenu.initiate(rewards);

        // Player 1 takes FOOD
        assertTrue("Player 1 should be able to take FOOD", rewardMenu.takeReward(player1.getPlayerOrder(), Effect.FOOD));
        assertFalse("Player 1 should not take FOOD again", rewardMenu.takeReward(player1.getPlayerOrder(), Effect.FOOD));

        // Player 2 takes WOOD
        assertTrue("Player 2 should be able to take WOOD", rewardMenu.takeReward(player2.getPlayerOrder(), Effect.WOOD));
        assertFalse("Player 2 should not take GOLD", rewardMenu.takeReward(player2.getPlayerOrder(), Effect.GOLD));

        assertEquals("No players left to take rewards", 0, rewardMenu.getPlayersLeftCount());
    }

    @Test
    public void testTryMakeAction() {
        Player player1 = new Player(new PlayerOrder(1, 4), new PlayerBoardMock());
        Player player2 = new Player(new PlayerOrder(2, 4), new PlayerBoardMock());
        List<Player> players = List.of(player1, player2);

        List<Effect> rewards = List.of(Effect.FOOD, Effect.WOOD);
        RewardMenu rewardMenu = new RewardMenu(players);
        rewardMenu.initiate(rewards);

        // Case 1: Player 1 has options available
        assertEquals(
                "Player 1 should be waiting for an action",
                HasAction.WAITING_FOR_PLAYER_ACTION,
                rewardMenu.tryMakeAction(player1.getPlayerOrder())
        );

        // Case 2: Player 1 takes a reward (FOOD)
        rewardMenu.takeReward(player1.getPlayerOrder(), Effect.FOOD);

        // Player 1 should no longer have any actions available
        assertEquals(
                "Player 1 should have no actions possible after taking a reward",
                HasAction.NO_ACTION_POSSIBLE,
                rewardMenu.tryMakeAction(player1.getPlayerOrder())
        );

        // Case 3: Player 2 has one reward left
        assertEquals(
                "Player 2 should automatically perform the only available action",
                HasAction.AUTOMATIC_ACTION_DONE,
                rewardMenu.tryMakeAction(player2.getPlayerOrder())
        );

        // After Player 2's automatic action, verify state
        rewardMenu.takeReward(player2.getPlayerOrder(), Effect.WOOD);

        assertEquals(
                "Player 2 should have no actions possible after taking the last reward",
                HasAction.NO_ACTION_POSSIBLE,
                rewardMenu.tryMakeAction(player2.getPlayerOrder())
        );

        // Case 4: When the reward menu is empty
        assertEquals(
                "Players should have no actions possible when the reward menu is empty",
                HasAction.NO_ACTION_POSSIBLE,
                rewardMenu.tryMakeAction(player1.getPlayerOrder())
        );

        assertEquals(
                "Players should have no actions possible when the reward menu is empty",
                HasAction.NO_ACTION_POSSIBLE,
                rewardMenu.tryMakeAction(player2.getPlayerOrder())
        );
    }

}
