package sk.uniba.fmph.dcs.player_board;

import java.util.Map;

public class PlayerBoardFactory {
    public Map.Entry<PlayerBoard, PlayerBoardFacade> createPlayerBoard() {
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


    }
}
