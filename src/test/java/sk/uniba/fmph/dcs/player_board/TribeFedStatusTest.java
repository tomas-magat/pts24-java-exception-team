package sk.uniba.fmph.dcs.player_board;

import org.junit.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;
import static org.junit.Assert.*;
import java.util.*;

public class TribeFedStatusTest {
    @Test
    public void addFieldTest(){
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus tribeFedStatus = new TribeFedStatus(playerFigures, playerResourcesAndFood);

        for(int i=0; i<10; i++) {
            assertEquals(tribeFedStatus.getFields(), i);
            tribeFedStatus.addField();
        }

        tribeFedStatus.addField();
        assertEquals(tribeFedStatus.getFields(), 10);
    }

    @Test
    public void testSetTribeFed(){
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus tribeFedStatus = new TribeFedStatus(playerFigures, playerResourcesAndFood);

        assertFalse(tribeFedStatus.isTribeFed()); // tribe should not be fed at the start

        assertTrue(tribeFedStatus.setTribeFed());

        assertTrue(tribeFedStatus.isTribeFed()); // we dont need to remove 10 points now

        assertFalse(tribeFedStatus.setTribeFed());
    }

    @Test

    public void testNewTurn(){
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus tribeFedStatus = new TribeFedStatus(playerFigures, playerResourcesAndFood);


        // at the start of new turn tribe should not be fed
        tribeFedStatus.setTribeFed();
        tribeFedStatus.newTurn();
        assertFalse(tribeFedStatus.isTribeFed());


        // check if we started new turn in PlayerFigures aswell
        int maxCount = playerFigures.getTotalFigures();
        if(maxCount >= 1) {
            playerFigures.takeFigures(1);
            tribeFedStatus.newTurn();
//            assertTrue(playerFigures.hasFigures(maxCount));
        }
    }

    @Test
    public void test1FeedTribeIfEnoughFood(){ // test if we use only food and no fields
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus tribeFedStatus = new TribeFedStatus(playerFigures, playerResourcesAndFood);

        List<Effect> food = new ArrayList<>();
        for(int i=0; i<5; i++){
            food.add(Effect.FOOD);
        }
        playerResourcesAndFood.giveResources(food);

        assertTrue(tribeFedStatus.feedTribeIfEnoughFood());
    }

    @Test
    public void test2FeedTribeIfEnoughFood(){ // test if we use only fields
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus tribeFedStatus =  new TribeFedStatus(playerFigures, playerResourcesAndFood);

        for(int i=0; i<5; i++){
            tribeFedStatus.addField();
        }

        assertTrue(tribeFedStatus.feedTribeIfEnoughFood());
    }

    @Test
    public void test3FeedTribeIfEnoughFood(){ // test if we use mix of fields and food
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus tribeFedStatus =  new TribeFedStatus(playerFigures, playerResourcesAndFood);

        // 3 fields
        for(int i=0; i<3; i++){
            tribeFedStatus.addField();
        }

        // 2 food
        List<Effect> food = new ArrayList<>();
        for(int i=0; i<3; i++){
            food.add(Effect.FOOD);
        }
        playerResourcesAndFood.giveResources(food);

        assertTrue(tribeFedStatus.feedTribeIfEnoughFood());
    }

    @Test
    public void test4FeedTribeIfEnoughFood(){ // test if we use only food and no fields, but we dont have enough food
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus tribeFedStatus = new TribeFedStatus(playerFigures, playerResourcesAndFood);

        List<Effect> food = new ArrayList<>();
        for(int i=0; i<4; i++){
            food.add(Effect.FOOD);
        }
        playerResourcesAndFood.giveResources(food);

        assertFalse(tribeFedStatus.feedTribeIfEnoughFood());
    }

    @Test
    public void test5FeedTribeIfEnoughFood(){ // test if we use only fields, but we dont have enough fields
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus tribeFedStatus =  new TribeFedStatus(playerFigures, playerResourcesAndFood);

        for(int i=0; i<4; i++){
            tribeFedStatus.addField();
        }

        assertFalse(tribeFedStatus.feedTribeIfEnoughFood());
    }

    @Test
    public void test6FeedTribeIfEnoughFood(){ // test if we use mix of fields and food, but we don't have enough
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus tribeFedStatus =  new TribeFedStatus(playerFigures, playerResourcesAndFood);

        // 3 fields
        for(int i=0; i<2; i++){
            tribeFedStatus.addField();
        }

        // 2 food
        List<Effect> food = new ArrayList<>();
        for(int i=0; i<2; i++){
            food.add(Effect.FOOD);
        }
        playerResourcesAndFood.giveResources(food);

        assertFalse(tribeFedStatus.feedTribeIfEnoughFood());
    }

    @Test
    public void testState(){
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus tribeFedStatus =  new TribeFedStatus(playerFigures, playerResourcesAndFood);

        assertEquals(tribeFedStatus.state(), "{\"tribeFed\":\"false\",\"fields\":\"0\"}");
    }

    @Test
    public void test1FeedTribe(){ // test when FOOD is in the resources ( as only resource in resources ) if it makes a problem
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus tribeFedStatus =  new TribeFedStatus(playerFigures, playerResourcesAndFood);

        List<Effect> resources = new ArrayList<>();
        for(int i=0; i<5; i++){
            resources.add(Effect.FOOD);
        }
        playerResourcesAndFood.giveResources(resources);

        assertTrue(tribeFedStatus.feedTribe(resources));
    }

    @Test
    public void test2FeedTribe(){ // test when FOOD is in the resources ( mixed with other resources ) if it makes a problem
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus tribeFedStatus =  new TribeFedStatus(playerFigures, playerResourcesAndFood);

        List<Effect> resources = new ArrayList<>();
        for(int i=0; i<5; i++) {
            resources.add(Effect.WOOD);
            resources.add(Effect.FOOD);
        }
        playerResourcesAndFood.giveResources(resources);

        assertTrue(tribeFedStatus.feedTribe(resources));
    }

    @Test
    public void test3FeedTribe(){ // test when we use mix of food, fields and resources
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        TribeFedStatus tribeFedStatus =  new TribeFedStatus(playerFigures, playerResourcesAndFood);

        // 3 wood
        List<Effect> resources = new ArrayList<>();
        for(int i=0; i<4; i++) {
            resources.add(Effect.WOOD);
        }
        playerResourcesAndFood.giveResources(resources);

        // 1 food
        List<Effect> food = new ArrayList<>();
        food.add(Effect.FOOD);
        playerResourcesAndFood.giveResources(food);

        // 1 field
        tribeFedStatus.addField();

        assertTrue(tribeFedStatus.feedTribe(resources));

    }


}
