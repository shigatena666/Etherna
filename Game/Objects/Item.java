package Game.Objects;

import java.util.HashMap;

public interface Item {
    String getDescription();
    Rarity getRarity();
    String getName();
    Slot getSlot();
    HashMap<Attribute, Integer> getAttributes();  //Bonus sur les attributs
    Integer getValueOfAttribute(Attribute attribute); //Obtenir la valeur d'un attribut
    int getCost();
    int getPrice();
}
