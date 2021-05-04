package Game.Objects;


import Game.Characters.Character;
import Game.Map;
import Game.Utilitaries.*;
import Game.Characters.*;
import java.util.HashMap;

public class FirePotion implements Item {
    private final HashMap<Attribute, Integer> attributes;

    public FirePotion() {
        attributes = new HashMap<>() { {
                put(Attribute.Damage, 8);
            }
        };
    }

    @Override
    public String getDescription() {
        return "A fire potion that burns ennemies around.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getName() {
        return "Fire potion";
    }

    @Override
    public int getCost() {
        return 2;
    }

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
        cible.getHealth().setValue(cible.getHealth().getValue() - attributes.get(Attribute.Damage));

    }
}
