package Game.Characters;

import Game.Objects.*;

import java.util.ArrayList;
import java.util.Arrays;


public class Inventory {
    private ArrayList<Item> buffer;

    //region Constructors

    /**
     * Default ctor.
     * Initiliaze a new inventory with an ArrayList.
     */
    public Inventory() {
        this.buffer = new ArrayList<>();
    }

    /**
     * Copy ctor.
     *
     * @param inventory The inventory which will be copied.
     */
    public Inventory(Inventory inventory) {
        this.buffer = new ArrayList<>();
        this.buffer.addAll(inventory.getBuffer());
    }

    /**
     * Copy ctor.
     *
     * @param inventory The inventory ArrayList which will be copied.
     */
    public Inventory(ArrayList<Item> inventory) {
        this.buffer = new ArrayList<>();
        this.buffer.addAll(inventory);
    }

    //endregion

    //region Accessors

    /**
     * @return The current inventory.
     */
    public ArrayList<Item> getBuffer() {
        return this.buffer;
    }

    /**
     * @param buffer The new inventory.
     */
    public void setBuffer(ArrayList<Item> buffer) {
        this.buffer = buffer;
    }

    //endregion

    //region Methods

    /**
     * Add an object to the inventory.
     *
     * @param object The object will be added to the inventory.
     */
    public void add(Item object) {
        this.buffer.add(object);
    }

    /**
     * Give the Objet which the right parts
     *
     * @param parts The parts we need to take
     * @return Objet at the param
     */
    public Item getStuffofSlot(Slot parts,Item[] pack)  {
        int i = 0;
        while(getBuffer().get(i).getSlot() != parts){
            i++;
        }
        return getBuffer().get(i);
    }

    /**
     * @return a list of Item which are equipped
     */
    public Item[] getstuff() {
        ArrayList<Item> stuff = new ArrayList<>();
        for (Item item : buffer) {
            if (item.getSlot() != Slot.None) {
                stuff.add(item);
            }
        }
        Item[] pack = new Item[stuff.size()];
        int i=0;
        for(Item item: stuff){
            pack[i] = item;
            i++;
        }
        return pack;
    }
    /**
     * @return object is a consummable
     */
    public boolean isConsummable(Item object) {
        return object instanceof HealingPotion || object instanceof PoisonPotion || object instanceof FirePotion;
    }


    //endregion

    @Override
    public String toString() {
        return "Inventory{" +
                "buffer=" + Arrays.toString(buffer.toArray()) +
                '}';
    }

    public static Inventory Default = new Inventory() {
        {
            add(new Weapon(Slot.Right));
            add(new Armor(Slot.Body));
            add(new HealingPotion());
            add(new PoisonPotion());
        }
    };
}
