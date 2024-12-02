package sk.uniba.fmph.dcs.game_board;

import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.Optional;
import java.util.OptionalInt;

import static org.junit.Assert.*;

public class GetCardTest {
    InterfaceCurrentThrow currentThrowMock;
    InterfaceCivilizationCardDeck civilizationCardDeckMock;

    public GetCardTest() {
        currentThrowMock = new InterfaceCurrentThrow() {
            @Override
            public void initiate(Player player, Effect effect, int dices) {}
            @Override
            public String state() {return null;}
            @Override
            public boolean useTool(int idx) {return false;}
            @Override
            public boolean canUseTools() {return false;}
            @Override
            public boolean finishUsingTools() {
                return false;
            }
        };
    }

    @Test
    public void testFromEmptyDeck() {
        civilizationCardDeckMock = Optional::empty;
        GetCard getCard = new GetCard(currentThrowMock, civilizationCardDeckMock);

        Player player = new Player(new PlayerOrder(1, 1), new InterfacePlayerBoardGameBoard() {
            public void giveEffect(Effect[] stuff) {}
            public void giveEndOfGameEffect(EndOfGameEffect[] stuff) {}
            public boolean takeResources(Effect[] stuff) {return false;}
            public boolean takeFigures(int count) {return false;}
            public boolean hasFigures(int count) {return false;}
            public boolean hasSufficientTools(int goal) {return false;}
            public OptionalInt useTool(int idx) {return OptionalInt.empty();}
            public boolean addNewFigure() {return false;}
        });
        ImmediateEffect choice = ImmediateEffect.Clay; // toto je uplne jedno podla game rules
                                     // ("The upper section of the card is ignored...")
        assertFalse(getCard.performEffect(player, choice));
    }

    @Test
    public void testFromNonEmptyDeck() {
        civilizationCardDeckMock = () ->
                Optional.of(new CivilisationCard(
                        new ImmediateEffect[]{ImmediateEffect.Gold},
                        new EndOfGameEffect[]{EndOfGameEffect.Shaman}));
        GetCard getCard = new GetCard(currentThrowMock, civilizationCardDeckMock);

        Player player = new Player(new PlayerOrder(1, 1), new InterfacePlayerBoardGameBoard() {
            public void giveEffect(Effect[] stuff) {}
            public void giveEndOfGameEffect(EndOfGameEffect[] stuff) {}
            public boolean takeResources(Effect[] stuff) {return false;}
            public boolean takeFigures(int count) {return false;}
            public boolean hasFigures(int count) {return false;}
            public boolean hasSufficientTools(int goal) {return false;}
            public OptionalInt useTool(int idx) {return OptionalInt.empty();}
            public boolean addNewFigure() {return false;}
        });
        ImmediateEffect choice = ImmediateEffect.Clay; // toto je uplne jedno podla game rules
                                     // ("The upper section of the card is ignored...")
        assertTrue(getCard.performEffect(player, choice));
    }
}
