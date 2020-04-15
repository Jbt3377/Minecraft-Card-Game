package uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses;

public class CharacterCardStats extends CardStats {

    ////////////
    //Properties
    ////////////
    private int hp;
    private int attack;

    /////////////
    //Constructor
    /////////////

    /**
     * Public constructor for CharacterCardStats object
     * @param name - The name of card.
     * @param manaCost - The mana cost to play this card.
     * @param descText - The description text of this card.
     * @param id - The id of this card.
     * @param hp - The hp of this card.
     * @param attack - The attack value of this card.
     */
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
