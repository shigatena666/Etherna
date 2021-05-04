package Game.Characters;

import java.util.HashMap;
import java.util.Map;

//Inspired by Stackoverflow
public enum WoundState {
    None(0), Shallow(1), Slightly(2), Wounded(3), Deep(4), Unconscious(5), Dead(6);

    private static final Map<Integer, WoundState> CACHE_VALUE = new HashMap<>();

    static {
        for (WoundState e : values()) {
            CACHE_VALUE.put(e.value, e);
        }
    }

    public final int value;

    WoundState(int value) {
        this.value = value;
    }

    public static WoundState valueOfInt(int value) {
        if(value > 6)
            value = 6;
        return CACHE_VALUE.get(value);
    }

}