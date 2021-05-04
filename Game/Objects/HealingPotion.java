package Game.Objects;

import Game.Characters.WoundState;
import Game.Characters.Character;

import java.util.HashMap;

public class HealingPotion implements Item {
    private final HashMap<Attribute, Integer> attributes;

    public HealingPotion() {
        attributes = new HashMap<>() { {
                put(Attribute.Heal, 10);
            }
        };
    }

    @Override
    public String getDescription() {
        return "Potion that heals the target by 1 HP.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getName() {
        return "Healing Potion";
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public int getPrice() {return 10;}

    @Override
    public HashMap<Attribute, Integer> getAttributes() {
        return attributes;
    }

    @Override
    public Slot getSlot() {
        return null;
    }

    @Override
    public Integer getValueOfAttribute(Attribute attribute) {
        return attributes.get(attribute);
    }

    public void use(Character cible) {
        cible.getHealth().setValue(cible.getHealth().getValue() + attributes.get(Attribute.Heal));
    }

}
