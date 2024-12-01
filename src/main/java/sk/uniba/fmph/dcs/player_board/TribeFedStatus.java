package sk.uniba.fmph.dcs.player_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import org.json.JSONObject;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class TribeFedStatus implements InterfaceTribeFedStatus{
    private boolean tribeFed;
    private int fields;
    private PlayerFigures playerFigures;
    private PlayerResourcesAndFood playerResourcesAndFood;

    public TribeFedStatus(PlayerFigures playerFigures, PlayerResourcesAndFood playerResourcesAndFood) {
        fields = 0;
        tribeFed = false;
        this.playerFigures = playerFigures;
        this.playerResourcesAndFood = playerResourcesAndFood;
    }

    public void addField(){
        if(fields < 10){
            fields++;
        }
    }

    public int getFields(){
        return fields;
    }

    public void newTurn() {
        tribeFed = false;
        playerFigures.newTurn();
    }

    public boolean feedTribeIfEnoughFood(){
        if(tribeFed){
            return true;
        }

        int foodNeeded = playerFigures.getTotalFigures() - fields;

        if(foodNeeded <= 0){ // we don't need to use any food
            tribeFed = true;
            return true;
        }

        // food will contain all Effect.FOOD we will need to use
        List<Effect> food = new ArrayList<>();
        for (int i = 0; i < foodNeeded; i++) {
            food.add(Effect.FOOD);
        }

        if (!playerResourcesAndFood.hasResources(food)) {
            return false;
        }

        playerResourcesAndFood.takeResources(food);
        return true;
    }

    public boolean feedTribe(List<Effect> resources){
        if(tribeFed){
            return true;
        }

        if(!playerResourcesAndFood.hasResources(resources)){ // if we give resources we don't have
            return false;
        }

        // separate food from the resources
        List<Effect> nonFoodResources = new ArrayList<>();
        for(Effect effect : resources){
            if(effect != Effect.FOOD){
                nonFoodResources.add(effect);
            }
        }

        // if we don't have enough resources we return false
        int howMuchFoodWeHave = playerResourcesAndFood.getNumberOfResources(Effect.FOOD);
        int nonFoodResourcesNeeded = playerFigures.getTotalFigures() - fields - howMuchFoodWeHave;

        if(nonFoodResources.size() < nonFoodResourcesNeeded){
            return false;
        }

        // make new list which contains the resources we need, we can left out any spare ones
        List<Effect> nonFoodResourcesToRemove = new ArrayList<>();
        for (int i = 0; i < nonFoodResourcesNeeded; i++) {
            nonFoodResourcesToRemove.add(nonFoodResources.get(i));
        }

        // make a new list which contains all food we will remove
        List<Effect> foodResourcesToRemove = new ArrayList<>();
        for(int i=0; i < howMuchFoodWeHave; i++){
            foodResourcesToRemove.add(Effect.FOOD);
        }

        playerResourcesAndFood.takeResources(foodResourcesToRemove);
        playerResourcesAndFood.takeResources(nonFoodResourcesToRemove);
        tribeFed = true;
        return true;


    }

    public boolean setTribeFed(){
        if(tribeFed){
            return false;
        }

        tribeFed = true;
        return true;
    }

    public boolean isTribeFed(){
        return tribeFed;
    }


    @Override
    public String state() {
        Map<String, String> pomocna = new HashMap<>();
        pomocna.put("fields", Integer.toString(fields));
        pomocna.put("tribeFed", Boolean.toString(tribeFed));
        return new JSONObject(pomocna).toString();
    }
}
