package Game.Skill;

import Game.Characters.Character;
import Game.Map;
import Game.Utilitaries.KeyValuePair;
import Game.Utilitaries.Position;

public class Blink implements Skill{


    @Override
    public String getName() {
        return "Blink";
    }

    @Override
    public int getCost() {
        return 2;
    }

    @Override
    public String getDescription() {
        return "Teleport to another position";
    }


    public void use(Character cible, Position pos, Map map) {
        cible.setPosition(pos);
        map.put(new KeyValuePair<>(java.lang.Character.toString(cible.getReference()), cible), cible.getPosition());
    }

    @Override
    public String toString() {
        String message;
        message = getName();
        message += "\t" + getCost() + " PM\t\n";
        message += getDescription();

        return message;
    }
}
