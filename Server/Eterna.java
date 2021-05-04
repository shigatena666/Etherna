package Server;

import Game.Characters.Inventory;
import Game.Characters.Monster;
import Game.Characters.Player;
import Game.Characters.WoundState;
import Game.Managers.CharacterManager;
import Game.Map;
import Game.Utilitaries.KeyValuePair;
import Game.Utilitaries.Position;
import ServerAPI.NetworkPlayer;

import java.util.Arrays;

public class Eterna {
    //TODO: Profiles with saves.
    //TODO: Implement game logic here.

    private Map map;
    private int ALD = 60; //Action-limit date. 60s

    public Eterna() {
        //Initialize the map
        map = new Map();
        for (int i = 0; i < 15; i++) {
            map.randomWall(1);
        }
        for (int i = 0; i < 2; i++) {
            Monster monster = new Monster();
            monster.setReference('M');

            map.spawn(monster, map.findEmptyPosition());

            CharacterManager.characters.add(monster);
        }
        //TODO: Instantiate monsters.
    }

    public void OnPlayerConnected(NetworkPlayer networkPlayer) {
        //Create a new player.
        Player player = new Player(); //TODO: Replace later
        player.setReference((String.valueOf(networkPlayer.getID()).charAt(0)));
        player.setID(networkPlayer.getID());
        player.setInventory(Inventory.Default);

        //TODO: Later check for save
        //Make the player spawn at an empty position.
        Position position = map.findEmptyPosition();
        map.spawn(player, position);

        //TODO: Create a default Inventory to set to player once he joined like public static Inventory Default = ...
        //TODO: Done in Character.init() but still needs rework.

        CharacterManager.characters.add(player);
    }

    public void OnUpdate() {
        //TODO: Re-send player's actions (move, pick up item....)
    }

    public void OnPlayerDisconnected(NetworkPlayer networkPlayer) {
        //TODO: When Player class changed, remove from CharacterManager.
        //TODO: Create a method in Map to reset a player reference and use it here.
        //TODO: Save player state.
    }

    public void OnServerShutdown() {
        //TODO: Save map state.
    }

    public Player getPlayer(NetworkPlayer networkPlayer) {
        return Arrays.stream(CharacterManager.getPlayers())
                .filter(x -> x.getID() == networkPlayer.getID())
                .findFirst()
                .orElse(null);
    }

    //region Accessors

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int getALD() {
        return ALD;
    }

    public void setALD(int ALD) {
        this.ALD = ALD;
    }

    //endregion
}
