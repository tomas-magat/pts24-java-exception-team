package sk.uniba.fmph.dcs.game_board;

import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import static org.junit.Assert.*;

import java.util.*;

public class AllPlayersTakeRewardTest {

    @Test
    public void testSelectReward() {
        Player player1 = new Player(new PlayerOrder(1, 4), new PlayerBoardMock());
        Player player2 = new Player(new PlayerOrder(2, 4), new PlayerBoardMock());
        Player player3 = new Player(new PlayerOrder(3, 4), new PlayerBoardMock());
        List<Player> players = List.of(player1, player2, player3);

        List<Effect> rewards = List.of(Effect.FOOD, Effect.WOOD, Effect.STONE);
        RewardMenu rewardMenu = new RewardMenu(rewards, players);

        AllPlayersTakeReward allPlayersTakeReward = new AllPlayersTakeReward(rewardMenu);

        // Player 1 selects a reward
        assertTrue("Player 1 should be able to select a reward", allPlayersTakeReward.performEffect(player1, Effect.WOOD));

        // Player 2 selects a reward
        assertTrue("Player 2 should be able to select a reward", allPlayersTakeReward.performEffect(player2, Effect.FOOD));

        // Player 3 selects a reward
        assertFalse("Player 3 should be able to select a reward", allPlayersTakeReward.performEffect(player3, Effect.GOLD));
        assertTrue("Player 3 should be able to select a reward", allPlayersTakeReward.performEffect(player3, Effect.STONE));

        // Verify that all players have selected their rewards
        assertEquals("All players should have selected their rewards", 0, rewardMenu.getPlayersLeftCount());
    }

}
