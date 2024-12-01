package sk.uniba.fmph.dcs.game_board;

public final class Throw {

    private Throw() {
        throw new UnsupportedOperationException();
    }

    private static int ThrowDice() {
        return (int) (Math.random() * 6) + 1;
    }

    public static int[] hod(final int dices) {
        int[] hody = new int[dices];
        for (int i = 0; i < dices; i++) {
            hody[i] = ThrowDice();
        }
        return hody;
    }
}
