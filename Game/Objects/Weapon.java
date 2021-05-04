package Game.Objects;

import java.util.HashMap;

public class Weapon implements Equipable {
    private Slot slot;
    private final HashMap<Attribute, Integer> attributes;

    public Weapon() {
        this.slot = Slot.None;
        attributes = new HashMap<>() { {
                put(Attribute.Damage, 7);
                put(Attribute.Precision, -4);
            }
        };
    }

    public Weapon(Slot slot) {
        this();
        this.slot = slot;
    }

    @Override
    public String getDescription() {
        return "Weapon to fight with";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getName() {
        return "Weapon";
    }

    @Override
    public int getCost() {
        return 25;
    }

    @Override
    public HashMap<Attribute, Integer> getAttributes() {
        return attributes;
    }

    @Override
    public Integer getValueOfAttribute(Attribute attribute) {
        return attributes.get(attribute);
    }

    @Override
    public Slot getSlot() { return slot; }

    @Override
    public void setSlot(Slot value) { this.slot = value; }

}
