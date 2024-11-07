package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.PlayerOrder;
import sk.uniba.fmph.dcs.stone_age.InterfacePlayerBoardGameBoard;

//Maybe hashing of playerBoard doesn't work
public class Player {

    private final PlayerOrder playerOrder;
    private final InterfacePlayerBoardGameBoard playerBoard;

    public Player(PlayerOrder playerOrder, InterfacePlayerBoardGameBoard playerBoard){

        this.playerOrder = playerOrder;
        this.playerBoard = playerBoard;
    }

    public PlayerOrder getPlayerOrder() {
        return playerOrder;
    }

    public InterfacePlayerBoardGameBoard getPlayerBoard() {
        return playerBoard;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + playerOrder.hashCode();
        result = prime * result + playerBoard.hashCode();
        return result;

    }

    @Override
    public boolean equals(final Object obj) throws IllegalArgumentException {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Player other = (Player) obj;
        if (!playerOrder.equals(other.playerOrder)) {
            return false;
        }
        return playerBoard.equals(other.playerBoard);
    }
}
