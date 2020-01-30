package uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses;

import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CardStats;

public class CharacterCardStats extends CardStats {

    ////////////
    //Properties
    ////////////
    private int hp;
    private int attack;

    /////////////
    //Constructor
    /////////////
    public CharacterCardStats(String name, int manaCost, String descText, int id, int hp, int attack) {
        super(name, manaCost, descText, id);
        this.hp = hp;
        this.attack = attack;
    }

    /////////////////////
    //Setters and Getters
    /////////////////////

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}
