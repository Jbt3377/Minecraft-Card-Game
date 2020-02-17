package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;

public class CharacterCard extends Card {

    ////////////
    //Properties
    ////////////

    private int hp;
    private int attack;
    private EquipCard equipCard;


    /////////////
    //Constructor
    ////////////

    public CharacterCard(float x, float y, GameScreen gameScreen, int index, CharacterCardStats cardStats) {
        super(x, y, gameScreen, index);
        this.hp = cardStats.getHp();
        this.attack = cardStats.getAttack();
        this.equipCard = null;
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

    public EquipCard getEquipCard() {
        return equipCard;
    }

    public void setEquipCard(EquipCard equipCard) {
        this.equipCard = equipCard;
    }
}
