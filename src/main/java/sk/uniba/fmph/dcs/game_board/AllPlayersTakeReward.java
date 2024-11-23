package sk.uniba.fmph.dcs.game_board;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;


import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;


public class AllPlayersTakeReward {
    private RewardMenu rewardMenu;
    private Map<Player, Effect> selectedRewards;
    private List<Player> remainingPlayers;

    public AllPlayersTakeReward() {
        this.selectedRewards = new HashMap<>();
    }

    public void initiate(RewardMenu menu) {
        this.rewardMenu = menu;
        this.remainingPlayers = new ArrayList<>();
        for (Player player : menu.getPlayers()) {
            remainingPlayers.add(player);
        }
    }

    public boolean takeReward(PlayerOrder playerOrder, Effect reward) {
        Player player = findPlayerByOrder(playerOrder);
        if (!remainingPlayers.contains(player)) {
            System.out.println(player.getPlayerOrder() + " has already selected a reward.");
            return false;
        }
        if (!rewardMenu.getMenu().contains(reward)) {
            System.out.println("Reward " + reward + " is not in menu.");
            return false;
        }
        if (selectedRewards.containsValue(reward)) {
            System.out.println("Reward " + reward + " is already taken.");
            return false;
        }
        selectedRewards.put(player, reward);
        remainingPlayers.remove(player);
        System.out.println(player.getPlayerOrder() + " took reward: " + reward);
        return true;
    }

    public HasAction tryMakeAction(PlayerOrder playerOrder) {
        Player player = findPlayerByOrder(playerOrder);
        if (!remainingPlayers.contains(player)) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    public boolean allPlayersHaveSelected() {
        return remainingPlayers.isEmpty();
    }

    private Player findPlayerByOrder(PlayerOrder playerOrder) {
        for (Player player : rewardMenu.getPlayers()) {
            if (player.getPlayerOrder().equals(playerOrder)) {
                return player;
            }
        }
        throw new IllegalArgumentException("PlayerOrder not found in RewardMenu");
    }

    public Map<Player, Effect> getSelectedRewards() {
        return selectedRewards;
    }

    public String state() {
        StringBuilder state = new StringBuilder("AllPlayersTakeReward state:\n");

        state.append("Available Rewards: ").append(rewardMenu.state());

        state.append("Selected Rewards:\n");
        for (Map.Entry<Player, Effect> entry : selectedRewards.entrySet()) {
            state.append(entry.getKey().getPlayerOrder()).append(" -> ").append(entry.getValue()).append("\n");
        }

        state.append("Players yet to select: ");
        for (Player player : remainingPlayers) {
            state.append(player.getPlayerOrder()).append(" ");
        }
        return state.toString();
    }

}
