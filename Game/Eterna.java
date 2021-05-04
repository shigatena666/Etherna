package Game;

import Game.Characters.Player;

public class Eterna {
    //TODO: Profiles with saves.
    //TODO: Implement game logic here.

    private Map map;
    private Player player; //TODO: Later replace with CharacterManager arrays.
    private int ALD = 60; //Action-limit date. 60s

    public Eterna() {
        map = new Map();
        player = new Player();
    }

    //region Accessors

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getALD() {
        return ALD;
    }

    public void setALD(int ALD) {
        this.ALD = ALD;
    }

    //endregion
}
