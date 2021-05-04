package Game.Managers;

import Game.Characters.Character;
import Game.Characters.Monster;
import Game.Characters.Player;
import Game.Utilitaries.Position;

import java.util.ArrayList;

public class CharacterManager {
    public static final ArrayList<Character> characters = new ArrayList<>();

    public static Character[] getCharacters() {
        return characters.toArray(new Character[0]);
    }

    //TODO: Correct this ?
    public static Monster[] getMonsters() {
        ArrayList<Monster> monsters = new ArrayList<>();
        for (Character character : characters) {
            if (character instanceof Monster) {
                monsters.add((Monster) character);
            }
        }
        return monsters.toArray(new Monster[0]);
    }

    public static Player[] getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for (Character character : characters) {
            if (character instanceof Player) {
                players.add((Player) character);
            }
        }
        return players.toArray(new Player[0]);
    }


    public static Character[] nearbyCharacter(Position pos, int range) {
        ArrayList<Character> nearbyCharact = new ArrayList<>();
        for(Character character: characters){
            if( ( character.getPosition().getX() >= (pos.getX()-range) ) || (character.getPosition().getX() <= pos.getX()+range) &&( (character.getPosition().getY() >= pos.getY()-range) || (character.getPosition().getY() <= pos.getY()+range) ) ){
                nearbyCharact.add(character);
            }
        }
        return nearbyCharact.toArray(new Character[0]);
    }

}
