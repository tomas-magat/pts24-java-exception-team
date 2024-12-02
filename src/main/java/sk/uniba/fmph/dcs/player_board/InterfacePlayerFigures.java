package sk.uniba.fmph.dcs.player_board;

public interface InterfacePlayerFigures {
    boolean addNewFigure();
    int getFigures();
    boolean hasFigures(int count);
    int getTotalFigures();
    boolean takeFigures(int count);
    void newTurn();
    String state();

}
