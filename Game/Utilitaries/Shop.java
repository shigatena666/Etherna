package Game.Utilitaries;

import Game.Characters.Book;
import Game.Characters.Inventory;
import Game.Objects.*;
import Game.Skill.*;

import java.util.Arrays;

public class Shop {
    ItemType[] listItem;
    SkillType[] listSkill;


    public Shop() {
        listSkill = SkillType.values();
        listItem = ItemType.values();

    }

    public String display() {
        String s= "Compétence :\n";
        for(SkillType skill: listSkill) {
            s += " - " + skill.getSkill().getName()+ " - "+ skill.getSkill().getPrice() + "XP\n";
        }
        s+="Objets : \n";
        for(ItemType item: listItem) {
            s += " - " + item.getItem().getName() +" - " + item.getItem().getPrice()+ "XP\n";
        }
        return s;
    }
}
