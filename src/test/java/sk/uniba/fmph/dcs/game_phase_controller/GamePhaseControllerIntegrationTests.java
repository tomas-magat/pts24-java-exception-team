package sk.uniba.fmph.dcs.game_phase_controller;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.Before;
import sk.uniba.fmph.dcs.game_phase_controller.mocks.*;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GamePhaseControllerIntegrationTests {

    private InterfaceGamePhaseController gamePhaseController;
    private List<PlayerOrder> players;
    private Map<Location, InterfaceFigureLocation> places;
    private Map<PlayerOrder, InterfaceFeedTribe> playerFeedTribe;
    private ToolUseMock toolUse;
    private TakeRewardMock takeReward;
    private PlayerOrder player1;
    private PlayerOrder player2;

    @Before
    public void setUp() {

        player1 = new PlayerOrder(0,2);
        player2 = new PlayerOrder(1,2);
        players = List.of(player1,player2);
        places = Map.of(Location.HUNTING_GROUNDS, new FigureLocationMock(), Location.CLAY_MOUND, new FigureLocationMock());
        playerFeedTribe = Map.of(players.get(0), new FeedTribeMock(), players.get(1), new FeedTribeMock());
        Map<PlayerOrder, InterfaceNewTurn> playerNewTurn = Map.of(players.get(0), new NewTurnMock(), players.get(1), new NewTurnMock());
        toolUse = new ToolUseMock();
        takeReward = new TakeRewardMock();
        gamePhaseController = GamePhaseControllerFactory.createGamePhaseController(places, playerFeedTribe,
                playerNewTurn,toolUse,takeReward, players.get(0));

    }

    private void checkStateString(String expectedString) {
        JSONObject obj = new JSONObject(gamePhaseController.state());
        String stateString = String.format("%s,%s/%s/%s", obj.getString("game phase"),
                obj.getString("round starting player"), obj.getString("current_player"),
                obj.getString("player taking a reward"));
        assertEquals(expectedString, stateString);
    }

    private boolean placeFigures(int id, int count, String place){

        return switch (place) {
            case "H" -> gamePhaseController.placeFigures(players.get(id), Location.HUNTING_GROUNDS,count);
            default -> gamePhaseController.placeFigures(players.get(id), Location.CLAY_MOUND, count);
        };

    }

    private void setPlaceFiguresMaxCount(int count, String place){

        InterfaceFigureLocation figureLocation =  switch (place) {
            case "H" -> places.get(Location.HUNTING_GROUNDS);
            default -> places.get(Location.CLAY_MOUND);
        };

        if(figureLocation instanceof FigureLocationMock current){
            current.expectedTryToPlaceFigures = HasAction.WAITING_FOR_PLAYER_ACTION;
            current.expectedPlaceFigures = true;

           current.maxFigures = count;
        }
    }
    @Test
    public void testPlayersSwappingPlacingFigures(){

        setPlaceFiguresMaxCount(1, "H");
        setPlaceFiguresMaxCount(2, "R");
        assertTrue(placeFigures(0, 1, "H"));
        assertFalse(placeFigures(1, 1, "H"));
        checkStateString("PLACE_FIGURES,0/1/None");
        assertTrue(placeFigures(1, 1, "R"));
        checkStateString("PLACE_FIGURES,0/0/None");
        assertTrue(placeFigures(0, 1, "R"));
        checkStateString("MAKE_ACTION,0/0/None");
        assertFalse(placeFigures(0, 1, "H"));
        assertFalse(placeFigures(0, 1, "R"));

    }
    @Test
    public void testOnly1PlayerCanPlaceFigures(){

        setPlaceFiguresMaxCount(2, "H");
        setPlaceFiguresMaxCount(0, "R");
        assertTrue(placeFigures(0, 2, "H"));
        assertFalse(placeFigures(0, 1, "R"));
        checkStateString("MAKE_ACTION,0/0/None");
    }

    private void goToMakeActionState(){

        setPlaceFiguresMaxCount(0, "H");
        setPlaceFiguresMaxCount(0, "R");
        placeFigures(0, 0, "H");

    }

    private boolean makeAction(int id, String place){

        return switch (place) {
            case "H" -> gamePhaseController.makeAction(players.get(id), Location.HUNTING_GROUNDS,List.of(),
                    List.of());
            default -> gamePhaseController.makeAction(players.get(id), Location.CLAY_MOUND, List.of(),
                    List.of());
        };

    }

    private void setPlaceActionsMaxCount(int count, String place, int targetPlayer){

        InterfaceFigureLocation figureLocation =  switch (place) {
            case "H" -> places.get(Location.HUNTING_GROUNDS);
            default -> places.get(Location.CLAY_MOUND);
        };

        if(figureLocation instanceof FigureLocationMock current){
            current.expectedTryToMakeAction = HasAction.WAITING_FOR_PLAYER_ACTION;
            current.expectedMakeAction = ActionResult.ACTION_DONE;

            if(targetPlayer != -1){

                current.limitOnlyTargetPlayer = true;
                current.targetPlayer = players.get(targetPlayer);
            }
            else{
                current.limitOnlyTargetPlayer = false;
            }

            current.maxActions = count;
        }
    }

    @Test
    public void testMakeActionsAndGoToFeedTribeState(){

        goToMakeActionState();
        checkStateString("MAKE_ACTION,0/0/None");
        setPlaceActionsMaxCount(1, "H", 0);
        setPlaceActionsMaxCount(1, "R", 0);
        assertTrue(makeAction(0,"H"));
        assertFalse(makeAction(0,"H"));
        assertTrue(makeAction(0,"R"));
        checkStateString("MAKE_ACTION,0/1/None");
        setPlaceActionsMaxCount(1, "H", -1);
        setPlaceActionsMaxCount(0, "R", -1);
        assertTrue(makeAction(1,"H"));
        checkStateString("FEED_TRIBE,0/0/None");
        assertFalse(makeAction(0,"H"));
    }
    @Test
    public void testOnly1PlayerCanMakeActions(){

        goToMakeActionState();
        setPlaceActionsMaxCount(2, "H", -1);
        setPlaceActionsMaxCount(1, "R", -1);
        assertTrue(makeAction(0, "H"));
        assertTrue(makeAction(0,"H"));
        assertTrue(makeAction(0, "R"));
        checkStateString("FEED_TRIBE,0/0/None");
    }

    public void setPlaceToolUse(String place){

        InterfaceFigureLocation figureLocation =  switch (place) {
            case "H" -> places.get(Location.HUNTING_GROUNDS);
            default -> places.get(Location.CLAY_MOUND);
        };

        if(figureLocation instanceof FigureLocationMock current){
            current.expectedTryToMakeAction = HasAction.WAITING_FOR_PLAYER_ACTION;
            current.expectedMakeAction = ActionResult.ACTION_DONE_WAIT_FOR_TOOL_USE;

        }
    }

    @Test
    public void testTakeRewardAndGoToFeedTribeState(){
        goToMakeActionState();

        setPlaceActionsMaxCount(0, "H", -1);
        setPlaceActionsMaxCount(1, "R", -1);

        if(places.get(Location.CLAY_MOUND) instanceof FigureLocationMock figureLocationMock){
            figureLocationMock.expectedMakeAction = ActionResult.ACTION_DONE_ALL_PLAYERS_TAKE_A_REWARD;
        }

        takeReward.expectedHasAction = HasAction.WAITING_FOR_PLAYER_ACTION;
        makeAction(0, "R");

        takeReward.expectedTakeReward = true;
        gamePhaseController.makeAllPlayersTakeARewardChoice(players.get(0), Effect.WOOD);

        checkStateString("FEED_TRIBE,0/0/None");
        assertFalse(gamePhaseController.makeAllPlayersTakeARewardChoice(players.get(0), Effect.WOOD));

    }
    @Test
    public void testUseToolsAndGoToFeedTribeState(){

        goToMakeActionState();
        setPlaceActionsMaxCount(0, "H", -1);
        setPlaceActionsMaxCount(1, "R", -1);

        setPlaceToolUse("R");
        toolUse.expectedUseTool = true;
        toolUse.expectedCanUseTools = true;
        toolUse.maxToolsUses = 2;

        makeAction(0, "R");
        checkStateString("WAITING_FOR_TOOL_USE,0/0/None");
        assertTrue(gamePhaseController.useTools(players.get(0), 1));
        assertTrue(gamePhaseController.useTools(players.get(0), 1));
        checkStateString("FEED_TRIBE,0/0/None");
        assertFalse(gamePhaseController.useTools(players.get(0), 1));
    }

    @Test
    public void testUseToolsDecideToStopAndGoToFeedTribeState(){

        goToMakeActionState();
        setPlaceActionsMaxCount(0, "H", -1);
        setPlaceActionsMaxCount(1, "R", -1);

        setPlaceToolUse("R");
        if(toolUse instanceof ToolUseMock toolUseMock){
            toolUseMock.expectedUseTool = true;
            toolUseMock.expectedCanUseTools = true;
            toolUseMock.expectedFinishUsingTools = true;
            toolUseMock.maxToolsUses = 2;
        }

        makeAction(0, "R");
        checkStateString("WAITING_FOR_TOOL_USE,0/0/None");
        assertTrue(gamePhaseController.useTools(players.get(0), 1));
        assertTrue(gamePhaseController.noMoreToolsThisThrow(players.get(0)));
        checkStateString("FEED_TRIBE,0/0/None");
        assertFalse(gamePhaseController.useTools(players.get(0), 1));
    }

    public void goToFeedTribeState(){

        setPlaceActionsMaxCount(0, "H", -1);
        setPlaceActionsMaxCount(0, "R", -1);
        goToMakeActionState();

    }

    @Test
    public void testFeedTribeAndGoToNewRound(){

        if(playerFeedTribe.get(player1) instanceof FeedTribeMock feedTribeMock1){
            feedTribeMock1.expectedFeedTribeIfEnoughFood = false;
        }

        goToFeedTribeState();
        checkStateString("FEED_TRIBE,0/0/None");


        if(playerFeedTribe.get(player1) instanceof FeedTribeMock feedTribeMock1){
            feedTribeMock1.expectedDoNotFeedThisTurn = true;
            feedTribeMock1.expectedIsTribeFed = true;
        }
        if(playerFeedTribe.get(player2) instanceof FeedTribeMock feedTribeMock2){

            feedTribeMock2.expectedIsTribeFed = true;
        }

        setPlaceFiguresMaxCount(1, "H");
        gamePhaseController.doNotFeedThisTurn(players.get(0));
        checkStateString("PLACE_FIGURES,1/1/None");
        assertFalse(gamePhaseController.doNotFeedThisTurn(players.get(0)));


    }
    @Test
    public void testFeedTribeAndEndGame(){


        if(playerFeedTribe.get(player1) instanceof FeedTribeMock feedTribeMock1){
            feedTribeMock1.expectedIsTribeFed = true;
        }

        if(playerFeedTribe.get(player2) instanceof FeedTribeMock feedTribeMock2){

            feedTribeMock2.expectedIsTribeFed = true;
        }

        if(places.get(Location.HUNTING_GROUNDS) instanceof FigureLocationMock figureLocationMock1){
            figureLocationMock1.expectedNewTurn = true;
        }
        if(places.get(Location.CLAY_MOUND) instanceof FigureLocationMock figureLocationMock2){
            figureLocationMock2.expectedNewTurn = false;
        }

        goToFeedTribeState();
        checkStateString("GAME_END,0/0/None");
    }

}
