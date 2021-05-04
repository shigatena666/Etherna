package Game;

import Game.Characters.*;
import Game.Characters.Character;
import Game.Log.LogType;
import Game.Log.Logger;
import Game.Managers.CharacterManager;
import Game.Objects.FirePotion;
import Game.Objects.HealingPotion;
import Game.Objects.Item;
import Game.Objects.PoisonPotion;
import Game.Utilitaries.*;
import Game.Map;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static Map map;
    public static Player player;
    public static CharacterManager manager;

    public static void creation() {
        //Maps
        map = new Map();
        for (int i = 0; i < 15; i++)
            map.randomWall(1);
        //Players
        player = new Player();
        player.setReference('1');
        player.init();
        Placement(player);
        CharacterManager.characters.add(player);
        //Monstres
        for (int i = 0; i < 8; i++) {
            Monster monstre = new Monster();
            monstre.setReference('M');
            monstre.init();
            Placement(monstre);
            CharacterManager.characters.add(monstre);
        }
    }

    public static void Placement(Character perso) {
        perso.getPosition().set(Map.MAP_SIZE / 2, Map.MAP_SIZE / 2);
        perso.setPosition(map.findEmptyPosition());
        map.put(new KeyValuePair<>(java.lang.Character.toString(perso.getReference()), perso), perso.getPosition());
        map.put(new KeyValuePair<>(java.lang.Character.toString(perso.getReference()), perso), perso.getPosition());
    }

    public static void affichage() {
        System.out.println("==================================================\n\n");
        System.out.println(map.readMap(map.randerDistance(player)));

        System.out.println(player);
    }

    public static String choixAction() {
        System.out.print("Vous pouvez :\n" +
                "1 - vous déplacer (2PA)\n" +
                "2 - attaquer (3PA)\n" +
                "3 - utiliser un objet (Variable)\n" +
                "4 - ramasser/déposer un objet (2PA)\n" +
                "5 - Boutique compétences\n"+
                "6 - finir et garder les PA restants\n" +
                "\n" +
                "Votre choix : ");
        Scanner scan = new Scanner(System.in);
        String entrie = scan.next();
        while (!(entrie.equals("1")) && !(entrie.equals("2")) && !(entrie.equals("3")) && !(entrie.equals("4")) && !(entrie.equals("5")) && !(entrie.equals("6"))){
            scan = new Scanner(System.in);
            entrie = scan.next();
        }
        return entrie;
    }

    public static void Deplacement() {
        System.out.println("Dans quelle direction voulez-vous aller ?");
        Direction direction = Direction.Up;
        Scanner scan = new Scanner(System.in);
        String choix = scan.next();
        while (!(choix.equals("Down")) && !(choix.equals("Up")) && !(choix.equals("Right")) && !(choix.equals("Left"))) {
            System.out.println("-Down\n-Up\n-Right\n-Left");
            scan = new Scanner(System.in);
            choix = scan.next();
        }
        switch (choix) {
            case "Down":
                direction = Direction.Down;
                break;
            case "Up":
                direction = Direction.Up;
                break;
            case "Right":
                direction = Direction.Right;
                break;
            case "Left":
                direction = Direction.Left;
                break;
        }

        if (map.isEmpty(direction, player)) {
            map.move(player, direction);
        } else {
            System.out.println("Il y a quelque chose sur votre chemin");
        }
    }

    public static void moveMonster() {
        ArrayList<Monster> monsterPack = new ArrayList<>();
        for (int line = 1; line < Map.MAP_SIZE; line++) {

            for (int column = 1; column < Map.MAP_SIZE - 1; column++) {

                if(map.getBuffer()[line][column].getValue() instanceof Monster)
                    monsterPack.add((Monster)(map.getBuffer()[line][column].getValue()));

            }
        }
        for(int i = 0; i < monsterPack.size(); i++) {
            monsterPack.get(i).autoAction(map);
        }
    }

    public static int useObject(Map map) {
        int value;

        Logger.Log(player.DisplayConsummable(), LogType.Info);
        Logger.Log("Which object do you want to use ?", LogType.Info);
        Scanner scan = new Scanner(System.in);
        while (!player.getInventory().isConsummable(player.getInventory().getBuffer().get(scan.nextInt()))){
            scan = new Scanner(System.in);
        }
        Item consummable = player.getInventory().getBuffer().get(scan.nextInt());
        value = consummable.getCost();


        //Item selected is a HealingPotion
        if(consummable instanceof HealingPotion){
            ((HealingPotion) consummable).use(player);
        }

        //Item selected is a FirePotion or a PoisonPotion
        else{
            Character ennemy = player.focusOn(map);
            while(!(player.isEnnemy(ennemy))){
                ennemy = player.focusOn(map);
            }
            if(consummable instanceof FirePotion)
                for(Character character: manager.nearbyCharacter((ennemy).getPosition(),1)) {
                    ((FirePotion) consummable).use(character);
                }

            else if(consummable instanceof PoisonPotion)
                ((PoisonPotion) consummable).use(ennemy);

        }

        player.getInventory().getBuffer().remove(scan.nextInt());

        return value;
    }

    public static void shopSkill(){
        System.out.println(new Shop().display());
    }

    public static void main(String[] args) {
        creation();

        while (player.getHealth().getValue() != player.getHealth().getMin()) {
            affichage();
            switch (choixAction()) {
                case "1":
                    //Deplacer
                    map.put(new KeyValuePair<>(java.lang.Character.toString(player.getReference()), player), player.getPosition()); //On reset la case actuelle
                    Deplacement();
                    map.put(new KeyValuePair<>(java.lang.Character.toString(player.getReference()), player), player.getPosition()); //On update la map en fonction de la position du joueur
                    //TODO: Déplacement
                    //Enlever des PAs
                    player.getStatistics().getActionPoints().setValue(player.getStatistics().getActionPoints().getValue() - 2);
                    break;

                case "2":
                    //Attaquer
                    player.attack(player.focusOn(map), map);
                    player.getStatistics().getActionPoints().setValue(player.getStatistics().getActionPoints().getValue() - 3);
                    break;

                case "3":
                    //Objet
                    int value = useObject(map);
                    player.getStatistics().getActionPoints().setValue(player.getStatistics().getActionPoints().getValue() - value);
                    break;

                case "4":
                    //Objet sol
                    player.objetSol(map);
                    break;

                case "5":
                    shopSkill();
                    break;
                case "6":
                    //Fin de tour
                    player.finTour();
                    break;
            }

            moveMonster();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
