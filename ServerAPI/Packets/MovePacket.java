package ServerAPI.Packets;

import Game.Utilitaries.Direction;
import ServerAPI.EventCode;

public class MovePacket extends Packet {
    private Direction direction;

    public MovePacket(byte[] data) {
        super(EventCode.Move);
        this.direction = Direction.valueOf(read(data));
    }

    public MovePacket(Direction direction) {
        super(EventCode.Move);
        this.direction = direction;
    }

    @Override
    public byte[] getData() {
        return (code.value + direction.name()).getBytes();
    }

    public Direction getDirection() {
        return direction;
    }
}
