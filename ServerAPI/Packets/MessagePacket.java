package ServerAPI.Packets;

import ServerAPI.EventCode;

public class MessagePacket extends Packet {
    private String message;

    //Constructor when server receives data
    public MessagePacket(byte[] data) {
        super(EventCode.Message);
        this.message = read(data);
    }

    //Constructor when client sends data
    public MessagePacket(String message) {
        super(EventCode.Message);
        this.message = message;
    }

    @Override
    public byte[] getData() {
        return (code.value + message).getBytes();
    }

    public String getMessage() {
        return message;
    }
}
