package ServerAPI.Packets;

import ServerAPI.EventCode;

import java.util.Arrays;

public abstract class Packet {
    public EventCode code;

    public Packet(EventCode code) {
        this.code = code;
    }

    public abstract byte[] getData();

    public String read(byte[] data) {
        return new String(data).trim().substring(Integer.toString(code.value).length());
    }

    public String read2(byte[] data) {
        return new String(data).trim().split(",")[2];
    }

    public static EventCode getPacket2(String message) {
        String header = message.trim().split(",")[0];
        return Arrays.stream(EventCode.values())
                .filter(x -> Integer.toString(x.value).equals(header))
                .findFirst()
                .orElse(EventCode.Unknown);
    }

    public static int getID2(String message) {
        return Integer.parseInt(message.trim().split(",")[1]);
    }

    //TODO: Upgrade this, pretty bad knowing if message contains another int inside regex will extract it as well.
    public static EventCode getPacket(String message) {
        message = message.trim().substring(0, 3).replaceAll("[^0-9]", ""); //Extract integer part from whole string
        for (int i = 0; i < EventCode.values().length; i++) {
            EventCode code = EventCode.values()[i];
            if (message.equals(Integer.toString(code.value))) {
                return code;
            }
        }
        return EventCode.Unknown;
    }

    //TODO: Upgrade this for the same reason as above.
    public static int getID(String message) {
        message = message.trim().substring(3, message.length()).replaceAll("[^0-9]", ""); //Avoid grabbing eventcode.
        //foreach player in game
        //return player / ID
        return -1;
    }
}
