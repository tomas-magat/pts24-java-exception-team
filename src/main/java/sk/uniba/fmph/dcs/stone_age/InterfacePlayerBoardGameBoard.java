package sk.uniba.fmph.dcs.stone_age;

import java.util.Collection;
import java.util.OptionalInt;

// TODO maybe need to implement some meaningful hash for Player Datatype
public interface InterfacePlayerBoardGameBoard {
    void giveEffect(Effect[] stuff);

    void giveEndOfGameEffect(EndOfGameEffect[] stuff);

    boolean takeResources(Effect[] stuff);

    boolean takeFigures(int count);

    boolean hasFigures(int count);

    boolean hasSufficientTools(int goal);

    OptionalInt useTool(int idx);

    boolean addNewFigure(); // increment totalFigures
}
