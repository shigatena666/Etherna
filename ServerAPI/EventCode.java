package ServerAPI;

import java.util.HashMap;
import java.util.Map;

public enum EventCode {
    Unknown(0), Move(1), UpdateMap(2), Message(3), Disconnect(254), Connect(255);

    public static final Map<Integer, EventCode> CACHE_VALUE = new HashMap<>();

    static {
        for (EventCode e : values()) {
            CACHE_VALUE.put(e.value, e);
        }
    }

    public final int value;

    EventCode(int value) {
        this.value = value;
    }

    public static EventCode valueOfInt(int value) {
        return CACHE_VALUE.get(value);
    }
}
