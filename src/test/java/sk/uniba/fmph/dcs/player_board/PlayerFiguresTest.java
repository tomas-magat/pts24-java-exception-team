package sk.uniba.fmph.dcs.player_board;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerFiguresTest {
    @Test
    public void testAddNewFigure() {
        PlayerFigures figure = new PlayerFigures();
        assertEquals(figure.getTotalFigures(), 5);

        for(int i=6; i<=10; i++){
            figure.addNewFigure();
            assertEquals(figure.getTotalFigures(), i);
        }

        figure.addNewFigure();
        assertEquals(figure.getTotalFigures(), 10);

    }

    @Test
    public void testHasFigures(){
        PlayerFigures figure = new PlayerFigures();
        assertTrue(figure.hasFigures(5));
        assertFalse(figure.hasFigures(6));
        assertTrue(figure.hasFigures(4));
    }

    @Test
    public void testTakeFigures(){
        PlayerFigures figure = new PlayerFigures();
        assertTrue(figure.takeFigures(5));
        assertFalse(figure.takeFigures(6));
    }

    @Test
    public void testNewTurn(){
        PlayerFigures figure = new PlayerFigures();
        figure.takeFigures(5);
        figure.newTurn();
        assertEquals(figure.getTotalFigures(), 5);
    }

    @Test
    public void testState(){
        PlayerFigures figure = new PlayerFigures();
        String pomocna = figure.state();
        assertEquals(pomocna, "{\"figures\":\"5\",\"totalFigures\":\"5\"}");
    }
}
