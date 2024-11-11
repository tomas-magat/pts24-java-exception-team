package sk.uniba.fmph.dcs.stone_age;

public interface InterfaceTakeReward {
    boolean takeReward(PlayerOrder player, Effect reward);

    HasAction tryMakeAction(PlayerOrder player);
}
