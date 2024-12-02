package sk.uniba.fmph.dcs.game_phase_controller.mocks;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.InterfaceFeedTribe;

public class FeedTribeMock implements InterfaceFeedTribe {

    public boolean expectedFeedTribe;
    public boolean expectedDoNotFeedThisTurn;
    public boolean expectedIsTribeFed;
    public boolean expectedFeedTribeIfEnoughFood;
    @Override
    public boolean feedTribeIfEnoughFood() {

        return expectedFeedTribeIfEnoughFood;
    }

    @Override
    public boolean feedTribe(Effect[] resources) {

        return expectedFeedTribe;
    }

    @Override
    public boolean doNotFeedThisTurn() {

        return expectedDoNotFeedThisTurn;
    }

    @Override
    public boolean isTribeFed() {

        return expectedIsTribeFed;
    }
}
