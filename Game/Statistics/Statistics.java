package Game.Statistics;

import Game.Log.LogType;
import Game.Log.Logger;
import Game.Utilitaries.Range;
import Game.Utilitaries.ValueMinMax;

import java.util.Random;

public class Statistics {
    private static final int INCREASE_POINTS = 3;

    private int statsPoint;
    private int agility;
    private int resistance;
    private int strength;
    private Range range;
    private ValueMinMax actionPoints;

    //region Constructors

    /**
     * Default ctor.
     * Initialize every value to default.
     */
    public Statistics() {
        this.statsPoint = 0;
        this.agility = 0;
        this.resistance = 0;
        this.strength = 0;
        this.range = new Range();
        this.actionPoints = new ValueMinMax(0, Integer.MAX_VALUE, 5);
    }

    /**
     * Params ctor.
     * Initialize every value by the one provided as parameters.
     *
     * @param agility The agility which will be given later to the character.
     * @param resistance The resistance which will be later given to the character.
     * @param strength The strength which will be given later to the character.
     * @param range The range which will be given later to the character.
     * @param actionPoints The action points which will be later given to the character.
     */
    public Statistics(int statsPoint, int agility, int resistance, int strength, Range range, ValueMinMax actionPoints) {
        this.statsPoint = statsPoint;
        this.agility = agility;
        this.resistance = resistance;
        this.strength = strength;
        this.range = range;
        this.actionPoints = actionPoints;
    }

    /**
     * Copy ctor.
     * Copy an instance of Client.Statistics.
     *
     * @param statistics The statistics instance to copy.
     */
    public Statistics(Statistics statistics) {
        this(statistics.getStatsPoint(), statistics.getAgility(), statistics.getResistance(), statistics.getStrength(), statistics.getRange(), statistics.getActionPoints());
    }

    //endregion

    //region Accessors

    /**
     * @return The agility as integer.
     */
    public int getAgility() {
        return agility;
    }

    /**
     * @param agility Set the agility to the given parameter.
     */
    public void setAgility(int agility) {
        this.agility = agility;
    }

    /**
     * @return The resistance as integer.
     */
    public int getResistance() {
        return resistance;
    }

    /**
     * @param resistance Set the resistance to the given parameter
     */
    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    /**
     * @return The strength as integer.
     */
    public int getStrength() {
        return strength;
    }

    /**
     * @param strength Set the strength to the given parameter.
     */
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * @return The range instance.
     */
    public Range getRange() {
        return range;
    }

    /**
     * @param range Set the range instance to the given one.
     */
    public void setRange(Range range) {
        this.range = range;
    }

    /**
     * @return The ValueMinMax instance that represents the action points of the character.
     */
    public ValueMinMax getActionPoints() {
        return actionPoints;
    }

    /**
     * @param actionPoints Set the action points instance to the given one.
     */
    public void setActionPoints(ValueMinMax actionPoints) {
        this.actionPoints = actionPoints;
    }

    /**
     * @return The amount of points you can spend to maximize a statistic.
     */
    public int getStatsPoint() {
        return statsPoint;
    }

    /**
     * Increase the amount of points you can spend by 3 (INCREASE_POINTS).
     */
    public void increaseStatsPoint() {
        statsPoint += INCREASE_POINTS;
    }

    /**
     * @param statsPoint Set the amount of points you can spend to maximize a statistic.
     */
    public void setStatsPoint(int statsPoint) {
        this.statsPoint = statsPoint;
    }

    //endregion

    //region Methods

    //Calcul le resultat d'un lancer de dés d'une statistique
    public static int lancerDes(int value) {
        int somme = value % 3;
        Random r = new Random();
        for (int i = 0; i < value / 3; i++) {
            int resul = r.nextInt(6) + 1;
            somme += resul;
        }
        return somme;
    }

    public void add(Stats stat, int value) {
        switch (stat) {
            case Strength:
                this.setStrength(this.getStrength() + value);
                break;

            case Agility:
                this.setAgility(this.getAgility() + value);
                break;

            case Resistance:
                this.setResistance(this.getResistance() + value);
                break;

            default:
                Logger.Log("Unknown provided stat. This should never happen.", LogType.Error);
                break;
        }
    }

    //endregion

    @Override
    public String toString() {
        String[] tab = new String[8];
        tab[0] = "- Adresse : " + getAgility() / 3 + "D" + getAgility() % 3;
        tab[1] = "- Force : " + getStrength() / 3 + "D" + getStrength() % 3;
        tab[2] = "- Resistance : " + getResistance() / 3 + "D" + getResistance() % 3;
        tab[3] = "+ Degats : " + getStrength() / 3 + "D" + getStrength() % 3;
        tab[4] = "+ Defence : " + getResistance() / 3 + "D" + getResistance() % 3;
        tab[5] = "+ Initiative : " + getAgility() / 3 + "D" + getAgility() % 3;
        tab[6] = "+ Evasion : " + getAgility() / 3 + "D" + getAgility() % 3;
        tab[7] = "+ Precision : " + getAgility() / 3 + "D" + getAgility() % 3;
        StringBuilder s = new StringBuilder("Vos caractéristiques :\n");
        //Utilisation d'un stringbuilder, + opti foreach
        for (String value : tab) {
            s.append(value).append("\n");
        }
        return s.toString();
    }
}