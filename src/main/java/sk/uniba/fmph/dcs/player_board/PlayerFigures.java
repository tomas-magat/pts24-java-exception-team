package sk.uniba.fmph.dcs.player_board;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PlayerFigures {
    private int totalFigures;
    private int figures;

    public PlayerFigures(){
        totalFigures = 5;
        figures = 5;
    }

    public void addNewFigure(){
        if(totalFigures < 10) {
            totalFigures++;
        }
    }

    public boolean hasFigures(int count){
        return figures >= count;
    }

    public int getTotalFigures(){
        return totalFigures;
    }

    public boolean takeFigures(int count){
        if(!hasFigures(count)){
            return false;
        }
        figures -= count;
        return true;
    }

    public void newTurn(){
        figures = totalFigures;
    }

    public String state(){
        Map<String, String> pomocna = new HashMap<>();
        pomocna.put("figures", Integer.toString(figures));
        pomocna.put("totalFigures", Integer.toString(totalFigures));
        return new JSONObject(pomocna).toString();
    }




}
