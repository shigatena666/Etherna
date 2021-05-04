package Game.Utilitaries;

import java.util.Random;

//Inspired by Stackoverflow
public enum Direction {
    Up, Down, Left, Right;

    private static final Direction[] VALUES = values();
    private static final int LENGTH = VALUES.length;
    private static final Random RANDOM = new Random();

    public static Direction randomDirection() {
        return VALUES[RANDOM.nextInt(LENGTH)];
    }

    public String string(Direction direction) {
        return String.valueOf(direction);
    }
}
