package Game.Characters;



import Game.Map;
import Game.Objects.*;
import Game.Statistics.Statistics;
import Game.Utilitaries.*;

import java.util.Scanner;

public abstract class Character {
    private char reference;
    private Position position;
    private Inventory inventory;
    private Statistics statistics;
    private ValueMinMax health;
    private EffectState effectState;
    private ValueMinMax experience; //TODO: Créer une classe information qui regroupe blessure et expérience (?)
    private ValueMinMax actionPoint;
    private int level;
    private boolean isAlive;

    //region Constructors

    /**
     * Default ctor.
     * Creates a character by initializing default values.
     */
    public Character() {
        this.reference = '1';
        this.position = new Position();
        this.inventory = new Inventory();
        this.statistics = new Statistics();
        this.health = new ValueMinMax(0,100,100);
        this.effectState = EffectState.Normal;
        this.experience = new ValueMinMax();
        this.actionPoint = new ValueMinMax();
        this.level = 1;
        this.isAlive = true;
    }

    /**
     * Params ctor.
     *
     * @param reference   The name of the character.
     * @param position    The position in which the character will spawn.
     * @param inventory   The inventory of the character.
     * @param statistics  The statistics of the character.
     * @param health      The wound state of the character.
     * @param effectState The state of the character. (Normal, Poisoned..)
     * @param experience  The min, required experience and current value of the character.
     * @param actionPoint The min, max and current value of action point of the character.
     */
    public Character(char reference, Position position, Inventory inventory, Statistics statistics,
                     ValueMinMax health, EffectState effectState, ValueMinMax experience, ValueMinMax actionPoint,
                     int level, boolean isAlive) {
        this.position = position;
        this.inventory = inventory;
        this.statistics = statistics;
        this.health = health;
        this.effectState = effectState;
        this.experience = experience;
        this.actionPoint = actionPoint;
        this.level = level;
        this.isAlive = isAlive;
    }

    /**
     * Copy ctor.
     *
     * @param personnage The character which will be copied.
     */
    public Character(Character personnage) {
        this(personnage.getReference(), personnage.getPosition(), personnage.getInventory(), personnage.getStatistics(),
                personnage.getHealth(), personnage.getEffectState(), personnage.getExperience(), personnage.getActionPoint(),
                personnage.getLevel(), personnage.isAlive());
    }

    //endregion

    //region Accessors

    /**
     * @return The reference (letter name) of the character.
     */
    public char getReference() {
        return reference;
    }

    /**
     * @param reference The name which will be given to the character.
     */
    public void setReference(char reference) {
        this.reference = reference;
    }

    /**
     * @return The current position of the character.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position The position whill will be given to the character.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return The current inventory of the character.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * @param objet The object to add into the inventory
     */
    public void addInventory(Item objet) {
        inventory.add(objet);
    }

    /**
     * @param inventory The inventory which will be given to the character.
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * @return The statistics of the character.
     */
    public Statistics getStatistics() {
        return statistics;
    }

    /**
     * @param statistics The statistics which will be given to the character.
     */
    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    /**
     * @return The current healt of the character.
     */
    public ValueMinMax getHealth() {
        return health;
    }

    /**
     * @param health The wound state which will be given to the chracter.
     */
    public void setHealth(ValueMinMax health) {
        this.health = health;
    }

    /**
     * @return The current state (effects) in which the character is
     */
    public EffectState getEffectState() {
        return effectState;
    }

    /**
     * @param effectState The effect which will be set to the character.
     */
    public void setEffectState(EffectState effectState) {
        this.effectState = effectState;
    }

    /**
     * @return The current experience ValueMinMax of the character.
     */
    public ValueMinMax getExperience() {
        return experience;
    }

    /**
     * @param experience The experience which will be set to the character (ValueMinMax).
     */
    public void setExperience(ValueMinMax experience) {
        this.experience = experience;
    }

    /**
     * @return The current action points ValueMinMax of the character.
     */
    public ValueMinMax getActionPoint() {
        return actionPoint;
    }

    /**
     * @param actionPoint The action points which will be set to the character (ValueMinMax).
     */
    public void setActionPoint(ValueMinMax actionPoint) {
        this.actionPoint = actionPoint;
    }

    /**
     * @return The current level of the character.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Increase the level of the character by 1.
     */
    public void increaseLevel() {
        this.level++;
    }

    /**
     * @param level Set the level of the character.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return Returns whether or not the character is alive.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * @param alive Set if the character is alive.
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    //endregion

    //region Methods

    /**
     * Shorten call of getPosition().moveUp();
     */
    public void moveUp() {
        this.position.moveUp();
    }

    /**
     * Shorten call of getPosition().moveDown();
     */
    public void moveDown() {
        this.position.moveDown();
    }

    /**
     * Shorten call of getPosition().moveLeft();
     */
    public void moveLeft() {
        this.position.moveLeft();
    }

    /**
     * Shorten call of getPosition().moveRight();
     */
    public void moveRight() {
        this.position.moveRight();
    }


    /**
     * @param direction The direction in which the character has to move.
     */
    public void move(Direction direction) {
        switch (direction) {
            case Up:
                moveUp();
                break;

            case Down:
                moveDown();
                break;

            case Left:
                moveLeft();
                break;

            case Right:
                moveRight();
                break;
        }
    }

    /**
     * Make all step for an attack
     *
     * @param cible A character to attack
     * @param map map to place chest at the death the ennemy
     */
    public void attack(Character cible, Map map){
        //Calcul possibility of attacking
        //Choice hand
        //TODO: Sécurité si main vide ou si on choisit None + Annuler les adding par item si le personnage n'en possède pas (cf. monstre)
        System.out.println("Quelle main allez vous utilisez ? Left or Right");
        //Slot hand = Slot.valueOf(new Scanner(System.in).next());
        Slot hand = Slot.Right;
        Item[] stuff =this.getInventory().getstuff();
        Item[] stuffEnnemy = cible.getInventory().getstuff();


        //Adding stat of characters
        int prec = this.getStatistics().lancerDes(this.getStatistics().getAgility());
        int esquive = cible.getStatistics().lancerDes(cible.getStatistics().getAgility());
        //Adding stat of items
        if( this.getInventory().getBuffer().size() > 0)
            prec += this.getStatistics().lancerDes(this.getInventory().getStuffofSlot(hand,stuff).getValueOfAttribute(Attribute.Precision));
        if(cible.getInventory().getBuffer().size() > 0)
            esquive -= cible.getStatistics().lancerDes(cible.getInventory().getStuffofSlot(Slot.Body,stuffEnnemy).getValueOfAttribute(Attribute.Evasion));

        System.out.println("Prec: "+prec +"\nEsquive: "+esquive);
        if(prec > esquive) {
            //Calcul possibility of dealing damage

            //Adding stat of characters
            int damage = this.getStatistics().lancerDes(this.getStatistics().getStrength());
            int defence = this.getStatistics().lancerDes(this.getStatistics().getResistance());
            //Adding stat of items
            if( this.getInventory().getBuffer().size() > 0)
                damage += this.getStatistics().lancerDes(this.getInventory().getStuffofSlot(hand,stuff).getValueOfAttribute(Attribute.Damage));
            if(cible.getInventory().getBuffer().size() > 0)
                for (Slot part : Slot.values()) {
                    defence += cible.getStatistics().lancerDes(cible.getInventory().getStuffofSlot(part,stuffEnnemy).getValueOfAttribute(Attribute.Defense));

                }

            System.out.println("Damage: "+damage +"\nDefence: " +defence );
            if (damage > defence) {
                //Dealing damage to character
                int result = damage - defence;

                cible.getHealth().setValue(cible.getHealth().getValue() - result);

                if(this instanceof Player)
                    this.getExperience().setValue(this.getExperience().getValue() + 1);
                if(cible.getHealth().getValue() == cible.getHealth().getMin())
                    map.put(new KeyValuePair<>("C",cible.death()),cible.getPosition());
                    if(this instanceof Player)
                        this.getExperience().setValue((cible.getExperience().getValue()/this.getExperience().getValue()) * 3 + this.getExperience().getValue());
            }
        }

    }

    public abstract Inventory death();


    /**
     * @param cible an Object on the map
     * @return cible is an Player or a Monster or a Character
     */
    public boolean isEnnemy(Object cible) {
        return cible instanceof Character;
    }
    //endregion

    @Override
    public String toString() {
        return "Personnage{" +
                "reference='" + reference + '\'' +
                ", position=" + position +
                ", inventory=" + inventory +
                ", statistics=" + statistics +
                ", effectState=" + effectState +
                ", experience=" + experience +
                ", actionPoint=" + actionPoint +
                '}';
    }
}