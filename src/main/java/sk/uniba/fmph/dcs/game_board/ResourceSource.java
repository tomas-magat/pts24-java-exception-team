package sk.uniba.fmph.dcs.game_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceSource {

    private String name;
    private Effect resource;
    private int maxFigures;
    private int maxFigureColours;
    private List<PlayerOrder> figures;

    public ResourceSource() {
        figures = new ArrayList<>();
    }

    public String state() {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("resource", resource.toString());
        map.put("maxFigures", maxFigures+"");
        map.put("maxFigureColours", maxFigureColours+"");
        map.put("figures", figures.toString());
        return new JSONObject(map).toString();
    }
}
