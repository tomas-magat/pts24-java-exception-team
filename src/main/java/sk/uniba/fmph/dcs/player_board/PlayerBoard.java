package sk.uniba.fmph.dcs.player_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;

import java.util.Map;

public class PlayerBoard implements InterfaceGetState {

    private int points;
    private int houses;
    private PlayerCivilisationCards cards;
    private PlayerTools tools;
    private PlayerResourcesAndFood resourcesAndFood;
    private TribeFedStatus fedStatus;
    private PlayerFigures figures;


    public PlayerBoard(
            int points,
            int houses,
            PlayerCivilisationCards cards,
            PlayerTools tools,
            PlayerResourcesAndFood resourcesAndFood,
            TribeFedStatus fedStatus,
            PlayerFigures figures
    ) {
        // Initial situation when the game begins
        this.points = points;
        this.houses = houses;
        this.cards = cards;
        this.tools = tools;
        this.resourcesAndFood = resourcesAndFood;
        this.fedStatus = fedStatus;
        this.figures = figures;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void addHouse() {
        this.houses++;
    }

    public void addEndOfGamePoints() {
        points += cards.calculateEndOfGameCivilisationCardPoints(
            houses,
            tools.getToolCount(),
            fedStatus.getFields(),
            figures.totalFigures()
        );
        points += resourcesAndFood.numberOfResourcesForFinalPoints();
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String state() {
        Map<String, String> state = Map.of(
                "points", String.valueOf(points),
                "houses", String.valueOf(houses),
                "civilisation cards", cards.state(),
                "tools", tools.state(),
                "resources and food", resourcesAndFood.state(),
                "tribe fed status", fedStatus.state(),
                "figures", figures.state()
        );
        return new JSONObject(state).toString();
    }
}
