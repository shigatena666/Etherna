package Game.Objects;

import java.util.HashMap;

public class Shield implements Equipable {
    private final HashMap<Attribute, Integer> attributes;
    private Slot slot;

    public Shield() {
        this.slot = Slot.None;
        attributes = new HashMap<>() { {
            put(Attribute.Damage,3);
            put(Attribute.Defense, 5);
            put(Attribute.Initiative, -3);
        } };
    }

    public Shield(Slot slot) {
        this();
        this.slot = slot;
    }

    @Override
    public String getDescription() {
        return "Mostly to protect yourself, but why not attacking with it ?";
    }

    @Override
    public String getName() {
        return "Shield";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public int getCost() {
        return 15;
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
