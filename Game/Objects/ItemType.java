package Game.Objects;


public enum ItemType {

    ARMURE(new Armor()), EPEE(new Weapon()), BOUCLIER(new Shield()), POTION_DE_SOIN(new HealingPotion()), POTION_DE_POISON(new PoisonPotion()), POTION_DE_FEU(new FirePotion());

    private Item item;

    ItemType(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return this.item;
    }
}