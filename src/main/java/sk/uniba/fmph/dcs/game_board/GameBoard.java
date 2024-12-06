package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard implements InterfaceGetState {

    private ToolMakerHutFields toolMakerHutFields;
    private ResourceSource resourceSourceForest, resourceSourceClay, resourceSourceQuarry, resourceSourceRiver, resourceSourceHuntingGrounds;
    private InterfaceCurrentThrow currentThrow;
    private CivilizationCardPlace civilizationCardPlace1, civilizationCardPlace2, civilizationCardPlace3, civilizationCardPlace4;
    private CivilizationCardDeck civilizationCardDeck;
    private InterfaceRewardMenu rewardMenu;
    private BuildingTile buildingTile1, buildingTile2, buildingTile3, buildingTile4;

    private List<Player> players;

    private Map<Location, InterfaceFigureLocation> locations;

    public GameBoard(List<Player> players,
                     ToolMakerHutFields toolMakerHutFields,
                     ResourceSource resourceSourceForest,
                     ResourceSource resourceSourceClay,
                     ResourceSource resourceSourceQuarry,
                     ResourceSource resourceSourceRiver,
                     ResourceSource resourceSourceHuntingGrounds,
                     InterfaceCurrentThrow currentThrow,
                     CivilizationCardPlace civilizationCardPlace1,
                     CivilizationCardPlace civilizationCardPlace2,
                     CivilizationCardPlace civilizationCardPlace3,
                     CivilizationCardPlace civilizationCardPlace4,
                     CivilizationCardDeck civilizationCardDeck,
                     RewardMenu rewardMenu,
                     BuildingTile buildingTile1,
                     BuildingTile buildingTile2,
                     BuildingTile buildingTile3,
                     BuildingTile buildingTile4) {
        this.players = players;
        this.toolMakerHutFields = toolMakerHutFields;
        this.resourceSourceForest = resourceSourceForest;
        this.resourceSourceClay = resourceSourceClay;
        this.resourceSourceQuarry = resourceSourceQuarry;
        this.resourceSourceRiver = resourceSourceRiver;
        this.resourceSourceHuntingGrounds = resourceSourceHuntingGrounds;
        this.currentThrow = currentThrow;
        this.civilizationCardPlace1 = civilizationCardPlace1;
        this.civilizationCardPlace2 = civilizationCardPlace2;
        this.civilizationCardPlace3 = civilizationCardPlace3;
        this.civilizationCardPlace4 = civilizationCardPlace4;
        this.civilizationCardDeck = civilizationCardDeck;
        this.rewardMenu = rewardMenu;
        this.buildingTile1 = buildingTile1;
        this.buildingTile2 = buildingTile2;
        this.buildingTile3 = buildingTile3;
        this.buildingTile4 = buildingTile4;

        // make map of all locations using FigureLocationAdaptor, because game controller uses InterfaceFigureLocation
        locations = new HashMap<>();
        locations.put(Location.TOOL_MAKER, new FigureLocationAdaptor(new PlaceOnToolMakerAdaptor(toolMakerHutFields), players));
        locations.put(Location.HUT, new FigureLocationAdaptor(new PlaceOnHutAdaptor(toolMakerHutFields), players));
        locations.put(Location.FIELD, new FigureLocationAdaptor(new PlaceOnFieldsAdaptor(toolMakerHutFields), players));
        locations.put(Location.HUNTING_GROUNDS, new FigureLocationAdaptor(resourceSourceHuntingGrounds, players));
        locations.put(Location.FOREST, new FigureLocationAdaptor(resourceSourceForest, players));
        locations.put(Location.CLAY_MOUND, new FigureLocationAdaptor(resourceSourceClay, players));
        locations.put(Location.QUARY, new FigureLocationAdaptor(resourceSourceQuarry, players));
        locations.put(Location.RIVER, new FigureLocationAdaptor(resourceSourceRiver, players));
        locations.put(Location.CIVILISATION_CARD1, new FigureLocationAdaptor(civilizationCardPlace1, players));
        locations.put(Location.CIVILISATION_CARD2, new FigureLocationAdaptor(civilizationCardPlace2, players));
        locations.put(Location.CIVILISATION_CARD3, new FigureLocationAdaptor(civilizationCardPlace3, players));
        locations.put(Location.CIVILISATION_CARD4, new FigureLocationAdaptor(civilizationCardPlace4, players));
        locations.put(Location.BUILDING_TILE1, new FigureLocationAdaptor(buildingTile1, players));
        locations.put(Location.BUILDING_TILE2, new FigureLocationAdaptor(buildingTile2, players));
        locations.put(Location.BUILDING_TILE3, new FigureLocationAdaptor(buildingTile3, players));
        locations.put(Location.BUILDING_TILE4, new FigureLocationAdaptor(buildingTile4, players));
    }

    @Override
    public String state() {
        Map<String, String> state = new HashMap<>();
        state.put("CurrentThrow", currentThrow.state());
        state.put("CivilizationCardDeck", civilizationCardDeck.state());
        state.put("CivilizationCardPlace1", civilizationCardPlace1.state());
        state.put("CivilizationCardPlace2", civilizationCardPlace2.state());
        state.put("CivilizationCardPlace3", civilizationCardPlace3.state());
        state.put("CivilizationCardPlace4", civilizationCardPlace4.state());
        state.put("RewardMenu", rewardMenu.state());
        state.put("BuildingTile1", buildingTile1.state());
        state.put("BuildingTile2", buildingTile2.state());
        state.put("BuildingTile3", buildingTile3.state());
        state.put("BuildingTile4", buildingTile4.state());
        state.put("ToolMakerHutFields", toolMakerHutFields.state());
        state.put("ResourceSourceForest", resourceSourceForest.state());
        state.put("ResourceSourceClay", resourceSourceClay.state());
        state.put("ResourceSourceQuarry", resourceSourceQuarry.state());
        state.put("ResourceSourceRiver", resourceSourceRiver.state());
        state.put("ResourceSourceHuntingGrounds", resourceSourceHuntingGrounds.state());
        return new JSONObject(state).toString();
    }

    public InterfaceFigureLocation getLocation(Location loc) {
        return locations.getOrDefault(loc, null);
    }

    public Map<Location, InterfaceFigureLocation> getAllLocations() {
        return locations;
    }

    public ToolMakerHutFields getToolMakerHutFields() {
        return toolMakerHutFields;
    }

    public ResourceSource getResourceSourceForest() {
        return resourceSourceForest;
    }

    public ResourceSource getResourceSourceClay() {
        return resourceSourceClay;
    }

    public ResourceSource getResourceSourceQuarry() {
        return resourceSourceQuarry;
    }

    public ResourceSource getResourceSourceRiver() {
        return resourceSourceRiver;
    }

    public ResourceSource getResourceSourceHuntingGrounds() {
        return resourceSourceHuntingGrounds;
    }

    public InterfaceCurrentThrow getCurrentThrow() {
        return currentThrow;
    }

    public CivilizationCardPlace getCivilizationCardPlace1() {
        return civilizationCardPlace1;
    }

    public CivilizationCardPlace getCivilizationCardPlace2() {
        return civilizationCardPlace2;
    }

    public CivilizationCardPlace getCivilizationCardPlace3() {
        return civilizationCardPlace3;
    }

    public CivilizationCardPlace getCivilizationCardPlace4() {
        return civilizationCardPlace4;
    }

    public CivilizationCardDeck getCivilizationCardDeck() {
        return civilizationCardDeck;
    }

    public InterfaceRewardMenu getRewardMenu() {
        return rewardMenu;
    }

    public BuildingTile getBuildingTile1() {
        return buildingTile1;
    }

    public BuildingTile getBuildingTile2() {
        return buildingTile2;
    }

    public BuildingTile getBuildingTile3() {
        return buildingTile3;
    }

    public BuildingTile getBuildingTile4() {
        return buildingTile4;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
