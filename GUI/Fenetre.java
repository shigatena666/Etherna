package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.Timer;

import Game.Characters.*;
import Game.Characters.Character;
import Game.Game;
import Game.Map;
import Game.Utilitaries.Direction;
import Game.Utilitaries.KeyValuePair;
import Game.Utilitaries.Position;
import javafx.scene.control.ProgressBar;


public class Fenetre extends JFrame {
    static final int ZONE_AUTRE = 1;
    static final int CODE_SAUVER = 0;
    static final int CODE_CHARGER = 1;
    static final int CODE_OPTION = 2;
    static final int ZONE_ACTION = 2;
    static final int CODE_ACTION[] = new int[]{3,4,5,6,7};


    JButton btnOption;
    JButton btnSauver;
    JButton btnCharger;
    JButton[] carte;
    JButton[] action;
    JTextArea fichePerso;
    JTextArea skillBook;
    JTextArea inventory;


    Dimension dim;
    Map map;
    Player player;
    static final String choix[] = { "Déplacement", "Attaquer", "Objets", "Récolter", "Fin de Tour" };



    public Fenetre(String title,Map map, Player player){
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.initComposants();
        this.centrer(0.7);
    }
    public Fenetre(){
        super("Eterna");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        Toolkit tk = Toolkit.getDefaultToolkit();
        dim = tk.getScreenSize();

        this.centrer(0.9);
        this.initComposants();

    }

    public void centrer(double d) {
        this.setResizable(true);
        int largeur = (int) (d * dim.width);
        int longueur = (int) (d * dim.height);
        this.setBounds((dim.width - largeur) / 2, (dim.height - longueur) / 2, largeur, longueur);
        this.setResizable(false);
    }

    private void initComposants() {
        creation();


        setLayout(null);
        JPanel option = buildOption();
        option.setBounds(0,0,(int)(dim.width/1.1),dim.height/15);
        add(option);

        JPanel map = buildMap();
        map.setBounds(10,50,(int)(dim.width/1.38),(int)(dim.height/1.4));
        add(map);

        JPanel perso = buildPerso();
        perso.setBounds((int)(dim.width/1.35),dim.height/13, (int)(dim.width*1.4), dim.height);
        add(perso);

        JPanel choix = buildChoix();
        choix.setBounds(0,(int)(dim.height/1.3),(int)(dim.width/1.1),dim.height/15);
        choix.setBorder(BorderFactory.createEtchedBorder());
        add(choix);

        JInternalFrame test = new JInternalFrame();
        test.add(new JButton("TEST"));
        add(test);

        initEcouteurs();

    }

    public void initEcouteurs() {
        this.btnCharger.addActionListener(new EcouteurBoutons(ZONE_AUTRE, CODE_CHARGER));
        this.btnSauver.addActionListener(new EcouteurBoutons(ZONE_AUTRE, CODE_SAUVER));
        this.btnOption.addActionListener(new EcouteurBoutons(ZONE_AUTRE,CODE_OPTION));
        for(int i =0; i<action.length; i++){
            this.action[i].addActionListener(new EcouteurBoutons(ZONE_ACTION,CODE_ACTION[i]));
        }
    }

    public JPanel buildOption() {
        JPanel pan = new JPanel();
        pan.setEnabled(false);

        btnCharger = new JButton("CHARGER");
        pan.add(btnCharger);
        pan.add(new JPanel());
        btnSauver = new JButton("SAUVER");
        pan.add(btnSauver);
        pan.add(new JPanel());
        btnOption =new JButton("OPTION");
        pan.add(btnOption);

        return pan;
    }

    public JPanel buildMap() {
        JPanel pan = new JPanel();
        pan.setLayout(new GridLayout(map.MAP_SIZE,map.MAP_SIZE));

        carte =buttonMap();

        for(JButton button:carte){
            pan.add(button);
        }

        return pan;
    }

    public JPanel buildPerso() {
        JPanel pan = new JPanel();
        pan.setLayout(null);

        JTextArea historique = new JTextArea(6,20);
        historique.setEditable(false);
        JScrollPane scrollHisto = new JScrollPane(historique);

        JTabbedPane tab = new JTabbedPane();

        fichePerso = new JTextArea(player.toString());
        fichePerso.setEditable(false);
        JScrollPane scrollFiche = new JScrollPane(fichePerso);

        skillBook = new JTextArea(player.getSkillBook().toString());
        skillBook.setEditable(false);
        JScrollPane scrollSkill = new JScrollPane(skillBook);

        inventory = new JTextArea(player.DisplayObject() + player.DisplayStuff());
        inventory.setEditable(false);
        JScrollPane scrollBag = new JScrollPane(inventory);

        tab.add("Statistiques",scrollFiche);
        tab.add("Compétences",scrollSkill);
        tab.add("Inventaire", scrollBag);
        tab.setBounds(0,0,175,dim.height/2);
        scrollHisto.setBounds(0,dim.height/2 + 10,175,150);
        pan.add(tab);
        pan.add(scrollHisto);

        return pan;
    }

    public JPanel buildChoix() {
        JPanel entri = new JPanel();

        entri.setEnabled(false);

        action = new JButton[choix.length];
        for(int i=0; i<action.length; i++){
            action[i] = new JButton(choix[i]);
            entri.add(action[i]);
        }


        return entri;
    }

    public JButton[] buttonMap(){
        carte = new JButton[map.MAP_SIZE*map.MAP_SIZE];
        for(int i=0; i < carte.length; i++){
            JButton butt = new JButton(map.getCase(i).getKey());
            butt.setEnabled(false);
            carte[i]=butt;
        }
        return carte;
    }

    public void creation() {
        //Maps
        map = new Map();
        for (int i = 0; i < 25; i++)
            map.randomWall(1);
        //Players
        player = new Player();
        player.setReference('1');
        player.getStatistics().setResistance(9);
        player.getStatistics().setStrength(12);
        player.getStatistics().setAgility(9);
        Placement(player);
        //Monstres
        for (int i = 0; i < 8; i++) {
            Monster monstre = new Monster();
            monstre.setReference('M');
            monstre.init();
            Placement(monstre);
        }
    }

    public void Placement(Character perso) {
        perso.getPosition().set(Map.MAP_SIZE / 2, Map.MAP_SIZE / 2);
        perso.setPosition(map.findEmptyPosition());
        map.put(new KeyValuePair<>(java.lang.Character.toString(perso.getReference()), perso), perso.getPosition());
        map.put(new KeyValuePair<>(java.lang.Character.toString(perso.getReference()), perso), perso.getPosition());
    }

    class EcouteurBoutons implements ActionListener {
        private int zone;
        private int code;
        public EcouteurBoutons(int z, int c) {
            this.zone = z;
            this.code = c;
        }
        public void charger() {
            System.out.println("Je charge");
        }
        public void sauver() {
            System.out.println("Je sauve");
        }
        public void option() {
            System.out.println("Option");
        }
        public void move(){
        }
        public void combat(){
            System.out.println("Combat");
        }
        public void objet() {
            System.out.println("Utiliser objet");
        }
        public void ramasser() {
            System.out.println("Ramasser objet");
        }
        public void endTour() {
            System.out.println("Fin de tour");
        }

        public void actionPerformed(ActionEvent e) {
            switch (zone) {
                case ZONE_ACTION:
                    switch (code){
                        case 3:
                            move();
                            break;
                        case 4:
                            combat();
                            break;
                        case 5:
                            objet();
                            break;
                        case 6:
                            ramasser();
                            break;
                        case 7:
                            endTour();
                            break;
                        default:
                            break;
                    }
                case ZONE_AUTRE:
                    switch (code) {
                        case CODE_CHARGER:
                            charger();
                            break;
                        case CODE_SAUVER:
                            sauver();
                            break;
                        case CODE_OPTION:
                            option();
                        default:
                            break;
                    }
                default:
                    break;
            }
        }
    }


    public static void main(String[] args) {
        Fenetre fen = new Fenetre();
    }

}

