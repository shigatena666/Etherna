package ServerAPI.Packets;

import Game.Map;
import ServerAPI.EventCode;
import com.google.gson.Gson;

public class MapPacket extends Packet {
    private Map map;
    private String mapAsJson;

    //Constructor when receiving data
    public MapPacket(byte[] data) {
        super(EventCode.UpdateMap);
        this.mapAsJson = read(data);
        this.map = new Gson().fromJson(mapAsJson, Map.class);
    }

    //Constructor when sending data
    public MapPacket(Map map) {
        super(EventCode.UpdateMap);
        this.mapAsJson = new Gson().toJson(map);
    }

    @Override
    public byte[] getData() {
        return (code.value + mapAsJson).getBytes();
    }

    public Map getMap() {
        return map;
    }
}
