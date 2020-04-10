package uk.ac.qub.eeecs.game.GameObjects.CardClasses;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses.CharacterCardStats;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardBitmapFactory;

public class CharacterCard extends Card {

    ////////////
    //Properties
    ////////////

    private int mHP;
    private int mAttackDmg;
    private EquipCard mEquipedCard;


    /////////////
    //Constructor
    ////////////

    public CharacterCard(float x, float y, GameScreen gameScreen, CharacterCardStats cardStats) {
        super(x, y, gameScreen, cardStats);
        this.mHP = cardStats.getHp();
        this.mAttackDmg = cardStats.getAttack();
        this.mEquipedCard = null;
        this.mCardBase = CardBitmapFactory.returnBitmap(this,gameScreen);
    }

    /////////
    //Methods
    /////////

    /*

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {

//    //  Draw the attack value
//    drawBitmap(mCardDigits[mAttack], mAttackOffset, mAttackScale,
//               graphics2D, layerViewport, screenViewport);
//
//    // Draw the health value
//    drawBitmap(mCardDigits[mHealth], mHealthOffset, mHealthScale,
//               graphics2D, layerViewport, screenViewport);


    }

    */

    /////////////////////
    //Setters and Getters
    /////////////////////

    public int getmHP() {
        return mHP;
    }

    public void setmHP(int mHP) {
        this.mHP = mHP;
    }

    public int getmAttackDmg() { return mAttackDmg; }

    public void setmAttackDmg(int mAttackDmg) {
        this.mAttackDmg = mAttackDmg;
    }

    public EquipCard getmEquipedCard() {
        return mEquipedCard;
    }

    public void setmEquipedCard(EquipCard mEquipedCard) {
        this.mEquipedCard = mEquipedCard;
    }
}