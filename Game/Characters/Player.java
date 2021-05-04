package Game.Characters;


import Game.Log.LogType;
import Game.Log.Logger;
import Game.Objects.*;
import Game.Map;
import Game.Statistics.Stats;
import Game.Utilitaries.*;
import Game.Managers.*;

import java.util.Scanner;
import static java.lang.Math.abs;

public class Player extends Character {
    private int ID; //TODO: Never allow character with same ID.
    private Book skillBook;
    private String name;

    public Player() {
        ID = -1;
        skillBook = new Book();
    }

    /**
     * @return The ID of the character. Useful to distinguish players on a server.
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID Set the ID of the character. Will be attributed by the server.
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @param skillBook Set the skillBook of the character.
     */
    public void setSkillBook(Book skillBook) {
        this.skillBook = skillBook;
    }

    /**
     * @return The skillBook of the character.
     */
    public Book getSkillBook() {
        return skillBook;
    }

    /**
     * @return Print Item in Player's Inventory
     */
    public String DisplayObject() {
        StringBuilder s = new StringBuilder("Your inventory: \n");
        for (Item objet : getInventory().getBuffer()) {
                s.append("\t- ")
                        .append(getInventory().getBuffer().indexOf(objet) + ": ")
                        .append(objet.getName())
                        .append("\n");
        }
        return s.toString();
    }

    /**
     * @return Print Item in Player's Inventory which are consummable
     */
    public String DisplayConsummable() {
        StringBuilder s = new StringBuilder("Your consummables: \n");
        for (Item objet : getInventory().getBuffer()) {
            if(getInventory().isConsummable(objet)) {
                s.append("\t- ")
                        .append(getInventory().getBuffer().indexOf(objet)).append(": ")
                        .append(objet.getName())
                        .append("\n");
            }
        }
        return s.toString();
    }

    /**
     * @return Print Item in Player's Inventory which haven't Slot at None
     */
    public String DisplayStuff() {
        StringBuilder s = new StringBuilder("Equiped objects: \n");
        for (Item objet : getInventory().getBuffer()) {
            if (objet instanceof Equipable) {
                Equipable equipable = (Equipable)objet;

                if (equipable.getSlot() != Slot.None) {
                    s.append("+ ")
                            .append(equipable.getName())
                            .append(" : ");

                    for (java.util.Map.Entry<Attribute, Integer> entry : equipable.getAttributes().entrySet()) {
                        s.append(entry.getKey())
                                .append("=")
                                .append(abs(entry.getValue()) / 3)
                                .append("D")
                                .append(abs(entry.getValue()) % 3)
                                .append(" | ");
                    }
                    s.append("\n");
                }
            }
        }
        return s.toString();
    }

    /**
     * Give experience to the Player with a formula
     * if the player has max experience than he levelup
     * @param value experience of the ennemy to calculate a ratio
     */
    public void gainExp(int value) {
        this.getExperience().setValue( ( value /this.getExperience().getValue() ) * 3 + this.getExperience().getValue() );
        if(this.getExperience().getValue() == this.getExperience().getMax())
            this.levelUp();

    }

    /**
     * Inform the Player of a level up and modify his level and experience
     */
    public void levelUp() {
        Logger.Log("You leveled up from level: " + this.getLevel() + " to: " + (getLevel() + 1), LogType.Info);
        increaseLevel();
        getExperience().setValue(getExperience().getMin());
        getExperience().setMax(this.getExperience().getMax() + 20);
        Logger.Log("Your stats increased, you have 3 more points to spend.", LogType.Info);
        getStatistics().increaseStatsPoint();
        Logger.Log("Current stats point: " + getStatistics().getStatsPoint(), LogType.Info);
    }

    public void objetSol(Map map) {
        Logger.Log("Drop or Collect",LogType.Info);
        Scanner scan = new Scanner(System.in);
        while ( !(scan.next().matches("Drop")) || !(scan.next().matches("Collect")) ){
            Logger.Log("Erreur d'action", LogType.Info);
            scan = new Scanner(System.in);
        }

        switch (scan.next()) {
            case "Drop":
                Logger.Log(DisplayObject(), LogType.Info);
                Logger.Log("What do you want to throw ?", LogType.Info);
                scan = new Scanner(System.in);
                while (scan.nextInt() > 0 && scan.nextInt() < getInventory().getBuffer().size()){
                    Logger.Log("", LogType.Info);
                    scan = new Scanner(System.in);
                }

                Inventory chest = new Inventory();
                chest.add(getInventory().getBuffer().get(scan.nextInt()));
                Position position = map.findEmptyPosition();
                KeyValuePair<String,Object> keyValuePair = new KeyValuePair("C",chest);
                map.put(keyValuePair,position);
                break;

            case "Collect":
                Object loot = this.focusOn(map);
                while(!(loot instanceof Inventory)){
                    loot = this.focusOn(map);
                }
                for(Item object : ((Inventory) loot).getBuffer()) {
                    this.getInventory().add(object);
                }
                break;
        }

    }

    public void finTour() {
        this.getStatistics().getActionPoints().setValue(this.getActionPoint().getMax());
        this.getHealth().setValue(this.getHealth().getValue() + 2);
    }

    public void shop(){
        Logger.Log("Bonjour Joueur "+name+" ,dans le magasin du néant !", LogType.Info);

    }



    //Methode technique
    public void init() {
        setReference('1');
        System.out.println("Quelle est votre nom ?");
        name = new Scanner(System.in).next();
        /*
         * Inventaire
         */

        setInventory(Inventory.Default);

        /*
         * Statistiques
         */
        int i = 18;
        while (i > 0) {
            //Text
            System.out.println("Veuillez adonner vos points à vos statistiques, entre la Force, l'Adresse et la Resistance" + "\nVous avez 18 points à répartir.Il vous reste " + i);
            System.out.println("Force : " + this.getStatistics().getStrength() + " | Adresse : " + this.getStatistics().getAgility() + " | Resistance : " + this.getStatistics().getResistance());

            //Modif
            String categorie = "";

            Scanner scan;
            while ( !(categorie.equals("Strength")) && !(categorie.equals("Agility")) && !(categorie.equals("Resistance")) ) {
                System.out.println("Choissisez quelle statistique vous voulez : Strength - Agility - Resistance ?");
                scan = new Scanner(System.in);
                categorie = scan.next();
            }

            int add = 20;
            while (add > i) {
                System.out.println("Combien appliquez vous ? il vous reste " + i);
                scan = new Scanner(System.in);
                add = scan.nextInt();
            }
            Stats stat = Stats.valueOf(categorie);
            this.getStatistics().add(stat, add);
            i -= add;
        }

    }

    /**
     * Give an Object at the position of the player and the addition of a x and y
     *
     * @param map Map to take Object
     * @return an Object in the map buffer at the position choice by the player
     */

    public Character focusOn(Map map) {
        Logger.Log("Which ennemy do you cible ?", LogType.Info);


        Logger.Log("Give us a number between -1 and 1 for x", LogType.Info);
        Scanner scan = new Scanner(System.in);
        int x = scan.nextInt();

        Logger.Log("Give us a number between -1 and 1 for y", LogType.Info);
        scan = new Scanner(System.in);
        int y = scan.nextInt();

        return ((Character)map.getBuffer()[this.getPosition().getY() + y][this.getPosition().getX() + x].getValue());
    }

    @Override
    public Inventory death() {
        Inventory chest = new Inventory(this.getInventory());

        return chest;
    }

    @Override
    public String toString() {
        String s = (DisplayObject() + DisplayStuff() + "\n");
        s += this.getStatistics().toString();
        s += "\nVos points de vie sont à : " + getHealth().getValue() + "\n\n";
        s += "Vos points d'action : " + getStatistics().getActionPoints().getValue() + "\n";
        return s;
    }
}