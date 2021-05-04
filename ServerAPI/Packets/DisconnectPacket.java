package ServerAPI.Packets;

import ServerAPI.EventCode;

public class DisconnectPacket extends Packet {
    private String username;

    public DisconnectPacket(byte[] data) {
        super(EventCode.Disconnect);
        this.username = read(data);
    }

    public DisconnectPacket(String username) {
        super(EventCode.Disconnect);
        this.username = username;
    }

    @Override
    public byte[] getData() {
        return (code.value + username).getBytes();
    }

    public String getUsername() {
        return username;
    }
}
