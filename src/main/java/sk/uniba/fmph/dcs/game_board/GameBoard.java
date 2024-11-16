package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;

public class GameBoard implements InterfaceGetState {

    private String state;
    private ToolMakerHutFields toolMakerHutFields;
    private ResourceSource resourceSource;
    private CurrentThrow currentThrow;
    private CivilizationCardPlace civilizationCardPlace;
    private CivilizationCardDeck civilizationCardDeck;
    private RewardMenu rewardMenu;
    private BuildingTile buildingTile;

    public GameBoard(String state) {
        this.state = state;

        toolMakerHutFields = new ToolMakerHutFields();
        resourceSource = new ResourceSource();
        currentThrow = new CurrentThrow();
        civilizationCardPlace = new CivilizationCardPlace();
        civilizationCardDeck = new CivilizationCardDeck();
        rewardMenu = new RewardMenu();
        buildingTile = new BuildingTile();
    }

    @Override
    public String state() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ToolMakerHutFields getToolMakerHutFields() {
        return toolMakerHutFields;
    }

    public ResourceSource getResourceSource() {
        return resourceSource;
    }

    public CurrentThrow getCurrentThrow() {
        return currentThrow;
    }

    public CivilizationCardPlace getCivilizationCardPlace() {
        return civilizationCardPlace;
    }

    public CivilizationCardDeck getCivilizationCardDeck() {
        return civilizationCardDeck;
    }

    public RewardMenu getRewardMenu() {
        return rewardMenu;
    }

    public BuildingTile getBuildingTile() {
        return buildingTile;
    }
}
