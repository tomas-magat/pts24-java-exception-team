package sk.uniba.fmph.dcs.game_board;

import org.json.JSONTokener;
import sk.uniba.fmph.dcs.stone_age.CivilisationCard;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;
import sk.uniba.fmph.dcs.stone_age.ImmediateEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameBoardFactory {

    public static GameBoard createGameBoard(List<Player> players, InterfaceThrow throwDices, CivilizationCardDeck cardDeck, List<BuildingTile> buildingTiles) {
        ToolMakerHutFields toolMakerHutFields = new ToolMakerHutFields();

        CurrentThrow currentThrow = new CurrentThrow(throwDices);
        int maxFigureColorsResourceSource;
        switch (players.get(0).getPlayerOrder().getPlayers()) {
            case 3:
                maxFigureColorsResourceSource = 2;
                break;
            case 2:
                maxFigureColorsResourceSource = 1;
                break;
            default:
                maxFigureColorsResourceSource = 4;
                break;
        }
        ResourceSource forest = new ResourceSource(currentThrow, "Forest", Effect.WOOD, 7, maxFigureColorsResourceSource);
        ResourceSource clay = new ResourceSource(currentThrow, "Clay mould", Effect.WOOD, 7, maxFigureColorsResourceSource);
        ResourceSource quarry = new ResourceSource(currentThrow, "Quarry", Effect.WOOD, 7, maxFigureColorsResourceSource);
        ResourceSource river = new ResourceSource(currentThrow, "River", Effect.WOOD, 7, maxFigureColorsResourceSource);

        RewardMenu rewardMenu = new RewardMenu(players);
        CivilizationCardPlace civilizationCardPlace1 = new CivilizationCardPlace(1, cardDeck, currentThrow, rewardMenu, throwDices);
        CivilizationCardPlace civilizationCardPlace2 = new CivilizationCardPlace(2, cardDeck, currentThrow, rewardMenu, throwDices);
        CivilizationCardPlace civilizationCardPlace3 = new CivilizationCardPlace(3, cardDeck, currentThrow, rewardMenu, throwDices);
        CivilizationCardPlace civilizationCardPlace4 = new CivilizationCardPlace(4, cardDeck, currentThrow, rewardMenu, throwDices);
        civilizationCardPlace1.setup(null, civilizationCardPlace2);
        civilizationCardPlace2.setup(civilizationCardPlace1, civilizationCardPlace3);
        civilizationCardPlace3.setup(civilizationCardPlace2, civilizationCardPlace4);
        civilizationCardPlace4.setup(civilizationCardPlace3, null);



        return new GameBoard(players, toolMakerHutFields, forest, clay, quarry, river, currentThrow,
                civilizationCardPlace1, civilizationCardPlace2, civilizationCardPlace3, civilizationCardPlace4,
                cardDeck, rewardMenu, buildingTiles);
    }

    public static CivilizationCardDeck createCardDeck1() {
        Stack<CivilisationCard> stack = new Stack<>();
        stack.push(new CivilisationCard(new ImmediateEffect[] {ImmediateEffect.Gold}, new EndOfGameEffect[]{EndOfGameEffect.Music}));
        stack.push(new CivilisationCard(new ImmediateEffect[] {ImmediateEffect.Food}, new EndOfGameEffect[]{EndOfGameEffect.Writing}));
        stack.push(new CivilisationCard(new ImmediateEffect[] {ImmediateEffect.Point}, new EndOfGameEffect[]{EndOfGameEffect.Builder}));
        stack.push(new CivilisationCard(new ImmediateEffect[] {ImmediateEffect.AllPlayersTakeReward}, new EndOfGameEffect[]{EndOfGameEffect.Shaman}));
        stack.push(new CivilisationCard(new ImmediateEffect[] {ImmediateEffect.Gold}, new EndOfGameEffect[]{EndOfGameEffect.Medicine}));
        stack.push(new CivilisationCard(new ImmediateEffect[] {ImmediateEffect.ArbitraryResource}, new EndOfGameEffect[]{EndOfGameEffect.Art}));
        stack.push(new CivilisationCard(new ImmediateEffect[] {ImmediateEffect.Card}, new EndOfGameEffect[]{EndOfGameEffect.Farmer}));
        stack.push(new CivilisationCard(new ImmediateEffect[] {ImmediateEffect.ThrowStone}, new EndOfGameEffect[]{EndOfGameEffect.ToolMaker}));
        stack.push(new CivilisationCard(new ImmediateEffect[] {ImmediateEffect.Wood}, new EndOfGameEffect[]{EndOfGameEffect.Pottery}));
        return new CivilizationCardDeck(stack);
    }

    public static List<BuildingTile> createBuildingTiles1(int players) { // TODO when BuildingTile fix merged
        List<BuildingTile> buildingTiles = new ArrayList<>();
//        for (int i = 0; i < players; i++)
//            buildingTiles.add(new BuildingTile(new ArrayList<>(), )); // TODO cakat kym sa opravi BuildingTile
        return buildingTiles;
    }


    public static class ThrowMock implements InterfaceThrow {
        public int[] result = new int[]{};
        @Override
        public int[] throwDices(int dices) {
            return result;
        }
    }
}
