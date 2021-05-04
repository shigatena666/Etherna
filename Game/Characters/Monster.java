package Game.Characters;

import Game.Objects.*;
import Game.Statistics.*;
import Game.Map;
import Game.Utilitaries.*;

import java.util.ArrayList;
import java.util.Random;

public class Monster extends Character {

    /**
     * Initiate the monster
     * -Randomize points between monster's statistics
     */
    public void init() {
        //Reference
        this.setReference('M');
        //Statistique
        for(int i = 22; i > 0;i--){
            Random r= new Random();

            //Categorie
            Stats categorie = Stats.Agility;
            int cat = r.nextInt(3);
            if(cat == 0) {
                categorie = Stats.valueOfInt(2);
            }
            else if(cat == 1) {
                categorie = Stats.valueOfInt(0);
            }
            else if(cat == 2){
                categorie = Stats.valueOfInt(1);
            }

            //Valeur
            int add = r.nextInt(i);

            this.getStatistics().add(categorie,add);
            i -= add;
        }
    }

    /**
     * Return a Inventory with item inside
     * -Can be random or fixe
     */
    @Override
    public Inventory death() {
        Inventory chest = new Inventory();

        //Fixe
        chest.add(new HealingPotion());
        chest.add(new Weapon());
        chest.add(new Shield());

        //Random
         /*
        Random r = new Random();
        int lootObjet = r.nextInt(10);

        switch (lootObjet) {
            case 0:
                chest.add(new Weapon());
                break;
            case 1:
                chest.add(new Armor());
                break;
            case 2:
                chest.add(new Shield());
                break;
            case 3:
                chest.add(new PoisonPotion());
                break;
            case 4:
                chest.add(new FirePotion());
                break;
            case 5:
                chest.add(new HealingPotion());
                break;
            default:
                chest = null;
        }

        */
        return chest;
    }

    /**
     * Make the monster play,
     * First attack if a player is in range
     * Second move randomly
     * Third attack if a player is in range and didn't attack before
     *
     * @param map map to move
     */
    public void autoAction(Map map){

        Object ennemy = autoEnnemy(map);
        if(ennemy != null)
            this.attack((Character)ennemy, map);

        Direction direction = Direction.randomDirection();
        while(!(map.isEmpty(direction, this))){
            direction = Direction.randomDirection();
        }
        map.move(this,direction);

        if(ennemy == null)
            ennemy = autoEnnemy(map);
            if(ennemy != null)
                this.attack((Character)ennemy, map);
    }

    /**
     * Search if there is a player in his range of attack
     * @param map map to search
     * @return an Object who is a Player
     */
    public Object autoEnnemy(Map map){
        ArrayList<Position> arrayEnnemy = new ArrayList<>();
        for(int y = -this.getStatistics().getRange().getVertical(); y < this.getStatistics().getRange().getVertical(); y++) {
            for(int x = -this.getStatistics().getRange().getHorizontal(); x < this.getStatistics().getRange().getHorizontal(); x++) {
                Position position = new Position(this.getPosition().getX() + x, this.getPosition().getY() + y);
                if (map.isEmpty(position))
                    if (map.getBuffer()[position.getY()][position.getX()].getValue() instanceof Player)
                        arrayEnnemy.add(position);
            }
        }
        if(arrayEnnemy.size() == 0)
            return null;

        Random random = new Random();
        int r = random.nextInt(arrayEnnemy.size());

        return map.getBuffer()[arrayEnnemy.get(r).getY()][arrayEnnemy.get(r).getX()];
    }
}
