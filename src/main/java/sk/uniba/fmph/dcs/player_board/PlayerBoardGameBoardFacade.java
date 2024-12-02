package sk.uniba.fmph.dcs.player_board;

import sk.uniba.fmph.dcs.stone_age.*;

import java.util.List;
import java.util.OptionalInt;

public class PlayerBoardGameBoardFacade implements InterfaceFeedTribe, InterfaceNewTurn, InterfacePlayerBoardGameBoard {
    private final PlayerBoard playerBoard;
    private final int pointsToRemoveIfNotUsingFoodThisTurn = -10;

    public PlayerBoardGameBoardFacade(PlayerBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    @Override
    public boolean feedTribeIfEnoughFood() {
        return playerBoard.getTribeFedStatus().feedTribeIfEnoughFood();
    }

    @Override
    public boolean feedTribe(Effect[] resources) {
        return playerBoard.getTribeFedStatus().feedTribe(List.of(resources));
    }

    @Override
    public boolean doNotFeedThisTurn() {
        if(playerBoard.getTribeFedStatus().setTribeFed()){
            playerBoard.addPoints(pointsToRemoveIfNotUsingFoodThisTurn);
            return true;
        }
        return false;
    }

    @Override
    public boolean isTribeFed() {
        return playerBoard.getTribeFedStatus().isTribeFed();
    }

    @Override
    public void newTurn() {
        playerBoard.getPlayerTools().newTurn();
        playerBoard.getPlayerFigures().newTurn();
        playerBoard.getTribeFedStatus().newTurn();
    }

    @Override
    public void giveEffect(Effect[] stuff) {
        playerBoard.getPlayerResourcesAndFood().giveResources(List.of(stuff));
    }

    @Override
    public void giveEndOfGameEffect(EndOfGameEffect[] stuff) {
        playerBoard.getPlayerCivilisationCards().addEndOfGameEffects(List.of(stuff));
    }

    @Override
    public boolean takeResources(Effect[] stuff) {
        if(playerBoard.getPlayerResourcesAndFood().hasResources(List.of(stuff))){
            playerBoard.getPlayerResourcesAndFood().takeResources(List.of(stuff));
            return true;
        }
        return false;
    }

    @Override
    public boolean takeFigures(int count) {
        if(playerBoard.getPlayerFigures().hasFigures(count)){
            playerBoard.getPlayerFigures().takeFigures(count);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasFigures(int count) {
        return playerBoard.getPlayerFigures().hasFigures(count);
    }

    @Override
    public boolean hasSufficientTools(int goal) {
        return playerBoard.getPlayerTools().hasSufficientTools(goal);
    }

    @Override
    public OptionalInt useTool(int idx) {
        return playerBoard.getPlayerTools().useTool(idx);
    }

    @Override
    public boolean addNewFigure() {
        return playerBoard.getPlayerFigures().addNewFigure();
    }
}