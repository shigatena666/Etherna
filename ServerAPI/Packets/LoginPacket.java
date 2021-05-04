package ServerAPI.Packets;

import ServerAPI.EventCode;

public class LoginPacket extends Packet {
    private String username;
    private int ID;

    //Constructor when server receives data
    public LoginPacket(byte[] data) {
        super(EventCode.Connect);
        this.username = read(data);
    }

    public LoginPacket(String username) {
        super(EventCode.Connect);
        this.username = username;
        this.ID = -1;
    }

    //Constructor when client sends data
    public LoginPacket(String username, int ID) {
        super(EventCode.Connect);
        this.username = username;
        this.ID = ID;
    }

    @Override
    public byte[] getData() {
        return (code.value + username).getBytes();
    }

    public String getUsername() {
        return username;
    }
}
