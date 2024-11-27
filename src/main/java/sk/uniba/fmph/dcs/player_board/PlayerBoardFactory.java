package sk.uniba.fmph.dcs.player_board;

public class PlayerBoardFactory {
    public PlayerBoard createEmptyPlayerBoard() {
        PlayerBoard board = new PlayerBoard(
                0,
                0,
                new PlayerCivilisationCards(),
                new PlayerTools(),
                new PlayerResourcesAndFood(),
                new TribeFedStatus(),
                new PlayerFigures()
        );

    }
}
