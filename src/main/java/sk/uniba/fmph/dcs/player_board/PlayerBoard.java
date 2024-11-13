package sk.uniba.fmph.dcs.player_board;

public class PlayerBoard implements InterfaceGetState {
    private int points;
    private int houses;
    private PlayerCivilisationCards cards;
    private PlayerTools tools;
    private PlayerResourcesAndFood resourcesAndFood;
    private TribeFedStatus fedStatus;
    private PlayerFigures figures;

    public PlayerBoard() {
        // Initial situation when the game begins
        this.points = 0;
        this.houses = 0;
        this.cards = new PlayerCivilisationCards();
        this.tools = new PlayerTools();
        this.resourcesAndFood = new PlayerResourcesAndFood();
        this.fedStatus = new TribeFedStatus();
        this.figures = new PlayerFigures();
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
            figures.getFigures()
        );
    }

    @Override
    public String state() {
        // TODO
        return "";
    }
}
