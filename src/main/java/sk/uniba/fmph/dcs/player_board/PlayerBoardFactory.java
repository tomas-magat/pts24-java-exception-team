package sk.uniba.fmph.dcs.player_board;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public class PlayerBoardFactory {
    public Map.Entry<PlayerBoard, PlayerBoardGameBoardFacade> createPlayerBoard() {
        PlayerFigures figures = new PlayerFigures();
        PlayerCivilisationCards cards = new PlayerCivilisationCards();
        PlayerTools tools = new PlayerTools();
        PlayerResourcesAndFood resourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus fedStatus = new TribeFedStatus(figures, resourcesAndFood);

        PlayerBoard board = new PlayerBoard(
                0,
                0,
                cards,
                tools,
                resourcesAndFood,
                fedStatus,
                figures
        );

        PlayerBoardGameBoardFacade facade = new PlayerBoardGameBoardFacade(board);
        return Map.entry(board, facade);
    }
}
