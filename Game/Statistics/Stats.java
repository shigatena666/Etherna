package Game.Statistics;

import java.util.HashMap;
import java.util.Map;

public enum Stats {
    Agility(0), Resistance(1), Strength(2), Range(3), ActionPoints(4);

    private static final Map<Integer, Stats> CACHE_VALUE = new HashMap<>();

    static {
        for (Stats e : values()) {
            CACHE_VALUE.put(e.value, e);
        }
    }

    public final int value;

    Stats(int value) {
        this.value = value;
    }

    public static Map<Integer, Stats> getCache() {
        return CACHE_VALUE;
    }

    public static Stats valueOfInt(int value) {
        return CACHE_VALUE.get(value);
    }
}
