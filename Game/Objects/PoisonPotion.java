package Game.Objects;

import Game.Characters.Character;
import Game.Characters.EffectState;

import java.util.HashMap;

public class PoisonPotion implements Item {
    private final HashMap<Attribute, Integer> attributes;

    public PoisonPotion() {
        attributes = new HashMap<>() { {
                put(Attribute.Damage, 1);
            }
        };
    }

    @Override
    public String getDescription() {
        return "A potion that poisons the ennemy.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public int getCost() {
        return 3;
    }

    @Override
    public String getName() {
        return "Poison potion";
    }

    @Override
    public Slot getSlot() {
        return null;
    }

    @Override
    public HashMap<Attribute, Integer> getAttributes() {
        return attributes;
    }

    @Override
    public Integer getValueOfAttribute(Attribute attribute) {
        return attributes.get(attribute);
    }

    public void use(Character cible) {
        cible.setEffectState(EffectState.Poisoned);
    }
}
