package sk.uniba.fmph.dcs.game_board;

import org.json.JSONArray;
import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.InterfaceTakeReward;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.ArrayList;
import java.util.List;

public class RewardMenu implements InterfaceRewardMenu {
    private final List<Effect> menu;
    private final List<Player> players;
    private final List<PlayerOrder> playersLeft; // players who haven't collected their reward yet

    public RewardMenu(final List<Player> players) {
        this.players = new ArrayList<>();
        this.players.addAll(players);

        this.menu = new ArrayList<>();
        playersLeft = new ArrayList<>();
//        initiate(menu);
    }

    @Override
    public void initiate(final List<Effect> menu) {
        this.menu.clear();
        this.menu.addAll(menu);
        playersLeft.clear();
        players.forEach(player -> playersLeft.add(player.getPlayerOrder()));
    }

    public int getPlayersLeftCount() {
        return playersLeft.size();
    }

    @Override
    public String state() {
        JSONObject state = new JSONObject();

        JSONArray menuJson = new JSONArray();
        for (Effect effect : menu) {
            menuJson.put(effect.toString());
        }
        state.put("menu", menuJson);

        JSONArray playersLeftJson = new JSONArray();
        for (Player player : players) {
            playersLeftJson.put(player.toString());
        }
        state.put("players_left", playersLeftJson);

        return state.toString();
    }

    private Player getPlayer(final PlayerOrder player) {
        Player p = null;
        for (Player i : players) {
            if (i.getPlayerOrder().equals(player)) {
                p = i;
                break;
            }
        }
        return p;
    }

    @Override
    public boolean takeReward(PlayerOrder player, Effect reward) {
        if (!menu.contains(reward)) {
            return false;
        }

        Player p = getPlayer(player);
        if (p == null || !playersLeft.contains(player)) {
            return false;
        }

        p.getPlayerBoard().giveEffect(new Effect[]{reward});
        playersLeft.remove(player);
        menu.remove(reward);
        return true;
    }

    @Override
    public HasAction tryMakeAction(PlayerOrder player) {
        if (!playersLeft.contains(player) || menu.isEmpty()) {
            return HasAction.NO_ACTION_POSSIBLE;
        }

        if (menu.size() == 1) { // only 1 effect available, apply it
            Player p = getPlayer(player);
            p.getPlayerBoard().giveEffect(menu.toArray(new Effect[0]));
            return HasAction.AUTOMATIC_ACTION_DONE;
        }

        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }
}
