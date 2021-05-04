package Game.Objects;

import java.util.HashMap;

public class Armor implements Item, Equipable {
    private Slot slot;
    private final HashMap<Attribute, Integer> attributes;

    public Armor() {
        slot = Slot.None;
        attributes = new HashMap<>() { {
                put(Attribute.Defense, 6);
                put(Attribute.Evasion, -4);
                put(Attribute.Initiative, -2);
            }
        };
    }

    public Armor(Slot spot) {
        this();
        this.slot = spot;
    }

    @Override
    public String getDescription() {
        return "An armor that protects its wielder.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getName() {
        return "Armor";
    }

    @Override
    public int getCost() {
        return 20;
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
    public Slot getSlot() {
        return slot;
    }

    @Override
    public void setSlot(Slot slot) {
        this.slot = slot;
    }
}
