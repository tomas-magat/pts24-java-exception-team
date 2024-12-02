package sk.uniba.fmph.dcs.player_board;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlayerFigures implements InterfacePlayerFigures{
    private int totalFigures;
    private int figures;

    public PlayerFigures(){
        totalFigures = 5;
        figures = 5;
    }

    @Override
    public void addNewFigure(){
        if(totalFigures < 10) {
            totalFigures++;
        }
    }

    @Override
    public int getFigures(){
        return figures;
    }

    @Override
    public boolean hasFigures(int count){
        return figures >= count;
    }

    @Override
    public int getTotalFigures(){
        return totalFigures;
    }

    @Override
    public boolean takeFigures(int count){
        if(!hasFigures(count)){
            return false;
        }
        figures -= count;
        return true;
    }

    @Override
    public void newTurn(){
        figures = totalFigures;
    }

    @Override
    public String state(){
        Map<String, String> pomocna = new HashMap<>();
        pomocna.put("figures", Integer.toString(figures));
        pomocna.put("totalFigures", Integer.toString(totalFigures));
        return new JSONObject(pomocna).toString();
    }




}
