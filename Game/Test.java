package Game;

import java.util.ArrayList;
import java.util.Scanner;
import Game.Characters.*;
import Game.Characters.Character;
import Game.Managers.CharacterManager;
import Game.Utilitaries.*;
import Game.Objects.*;

public class Test {

    public static Map map;
    public static Player player;
    public static Monster monster;
    public static CharacterManager manager;

    public static String message = "\n======================================================================\n";

    public static void setStart() {
        //Maps
        map = new Map();
        //CharacterManager
        manager = new CharacterManager();
        //Players
        player = new Player();
        player.setReference('1');
        player.getStatistics().setAgility(12);
        player.getStatistics().setStrength(12);
        player.getStatistics().setResistance(9);
        player.setPosition(new Position(2,2));
        map.put(new KeyValuePair<>(String.valueOf(player.getReference()),player), player.getPosition());
        manager.characters.add(player);

        monster = new Monster();
        monster.init();
        monster.getPosition().set(player.getPosition().getX(),player.getPosition().getY()+1);
        manager.characters.add(monster);


    }

    public static void affichage() {
        System.out.println(map.toString());
        System.out.println(player);
    }

    public static String choixTest() {
        System.out.print("Test :\n" +
                "1 - Deplacement: \n" +
                "2 - Attaque\n" +
                "3 - Blessure et Soin\n" +
                "4 - Mort d'un monstre\n" +
                "5 - Experience\n" +
                "6 - Vision\n"+
                "\n" +
                "Fin\n\n"+
                "Votre choix : ");
        Scanner scan = new Scanner(System.in);
        String entrie = scan.next();
        while (!(entrie.equals("1")) && !(entrie.equals("2")) && !(entrie.equals("3")) && !(entrie.equals("4"))
                && !(entrie.equals("5")) && !(entrie.equals("6")) && !(entrie.equals("Fin")) ) {
            scan = new Scanner(System.in);
            entrie = scan.next();
        }
        return entrie;
    }


    public static void main(String[] args) {
        setStart();
        String test = "Non";
        while (test != "Fin") {
            switch (choixTest()) {
                case "1":
                    Deplacement();
                    break;
                case "2":
                    Combat();
                    break;
                case "3":
                    Blessure();
                    break;
                case "4":
                    Mort();
                    break;
                case "5":
                    Experience();
                    break;
                case "6":
                    VisionMap();
                    break;
                default:
                    test = "Fin";
                    break;
            }


        }
        System.out.println("Fin du test");
    }


    public static void Deplacement(){
        System.out.println(message);
        System.out.println(map.toString());

        //Deplacement vers la DROITE
        System.out.println("Deplacement vers la Droite");
        System.out.println("Position du joueur avant deplacement: " + player.getPosition().toString());
        if (map.isEmpty(Direction.Right, player)) {
            map.move(player, Direction.Right);
        } else {
            System.out.println("Il y a quelque chose sur votre chemin");
        }

        System.out.println(map.toString());
        System.out.println(message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Deplacement vers la GAUCHE
        System.out.println("Deplacement vers la Gauche");
        System.out.println("Position du joueur avant deplacement: " + player.getPosition().toString());
        if (map.isEmpty(Direction.Left, player)) {
            map.move(player, Direction.Left);
        } else {
            System.out.println("Il y a quelque chose sur votre chemin");
        }

        System.out.println(map.toString());
        System.out.println(message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Deplacement vers le HAUT
        System.out.println("Deplacement vers le Haut");
        System.out.println("Position du joueur avant deplacement: " + player.getPosition().toString());
        if (map.isEmpty(Direction.Up, player)) {
            map.move(player, Direction.Up);
        } else {
            System.out.println("Il y a quelque chose sur votre chemin");
        }

        System.out.println(map.toString());
        System.out.println(message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Deplacement vers le BAS
        System.out.println("Deplacement vers le Bas");
        System.out.println("Position du joueur avant deplacement: " + player.getPosition().toString());
        if (map.isEmpty(Direction.Down, player)) {
            map.move(player, Direction.Down);
        } else {
            System.out.println("Il y a quelque chose sur votre chemin");
        }

        System.out.println(map.toString());
        System.out.println(message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Deplacement vers MUR
        System.out.println("Deplacement vers le Mur");
        map.put(new KeyValuePair<>(map.WALL.getKey(), map.WALL.getValue()),new Position(3,2));
        System.out.println(map.toString());
        if(map.isEmpty(Direction.Right,player))
            map.move(player,Direction.Right);
        else
            System.out.println("Il y a quelque chose sur votre chemin");
        System.out.println(map.toString());
        System.out.println(message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void Combat() {
        //Combat
        for(int i=0; i < 4;i++) {
            System.out.println(message);
            System.out.println("Blessure du monstre: " );
            player.attack(monster, map);
            System.out.println("Blessure du monstre: " );
            System.out.println("Experience du joueur: " + player.getExperience().getValue());
            System.out.println(message);
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void Blessure() {
        //Blessure et test soin
        System.out.println(message);
        System.out.println(player.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new HealingPotion().use(player);
        System.out.println(player);

        System.out.println(message);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void Mort() {
        //Mort d'un personnage
        Monster monster = new Monster();
        monster.getPosition().set(Map.MAP_SIZE / 2, Map.MAP_SIZE / 2);
        monster.setPosition(map.findEmptyPosition());
        map.put(new KeyValuePair<>(java.lang.Character.toString(monster.getReference()), monster), monster.getPosition());
        map.put(new KeyValuePair<>(java.lang.Character.toString(monster.getReference()), monster), monster.getPosition());
        manager.characters.add(monster);
        Monster exemple = manager.getMonsters()[0];
        System.out.println("Test de la mort d'un monstrer");
        System.out.println("Position du monstre :" + exemple.getPosition().toString());
        map.put(new KeyValuePair<>("C", exemple.death()), exemple.getPosition());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(message);
        affichage();
        System.out.println(message);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void Experience() {
        System.out.println(message);
        System.out.println("Test de gain d'expérience du joueur");
        player.getExperience().setValue(1);
        player.gainExp(33);
        System.out.println("Experience: " + player.getExperience().getValue());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(message);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void VisionMap() {
        System.out.println(map.readMap(map.randerDistance(player)));
        System.out.println(message);
        player.getStatistics().getRange().setView(7);
        System.out.println(map.readMap(map.randerDistance(player)));
    }

}
