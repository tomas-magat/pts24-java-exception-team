package sk.uniba.fmph.dcs.player_board;

public interface InterfaceTribeFedStatus {
    default int getFields() {
        return 7;
    }
    String state();
}
