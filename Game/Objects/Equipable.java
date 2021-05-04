package Game.Objects;

public interface Equipable extends Item {
    Slot getSlot();
    void setSlot(Slot slot);
}
