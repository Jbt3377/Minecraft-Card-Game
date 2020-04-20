package uk.ac.qub.eeecs.game.GameObjects.ContainerClasses;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.gage.engine.audio.Sound;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.CardBitmapFactory;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.PopUpObject;

public class Mob extends Sprite {

    /////////////
    // Properties
    /////////////

    private int healthPoints, attackDamage;
    private int mobID;
    private String name;
    private Sound damagedSound, attackSound, deathSound;
    private boolean hasBeenUsed;
    private static int nextID = 0;
    private boolean isSelectedToAttack;
    private EquipCard equipCard;

    //////////////
    // Constructor
    //////////////

    /**
     * Public constructor for Mob
     * @param x_cor         - X Co-Ordinate for the Mob to be drawn on screen
     * @param y_cor         - Y Co-Ordinate for the Mob to be drawn on screen
     * @param gameScreen    - GameScreen to which the Mob will be drawn
     * @param characterCard - Character Card used to spawn the Mob, and used to fetch Name, Atk and
     *                          and Hp values
     */
    public Mob(float x_cor, float y_cor, GameScreen gameScreen, CharacterCard characterCard) {

        super(x_cor, y_cor, gameScreen.getGame().getScreenWidth() * 0.104f, gameScreen.getGame().getScreenHeight() * 0.185f,
                null, gameScreen);

        this.mobID = Mob.nextID;
        Mob.nextID++;

        this.name = characterCard.getCardName();
        this.healthPoints = characterCard.getmHP();
        this.attackDamage = characterCard.getmAttackDmg();
        this.mBitmap = CardBitmapFactory.returnMobBitmap(this,gameScreen);
        this.hasBeenUsed = false;
        this.equipCard = null;

        fetchAudioAssets();
    }

    //////////
    // Methods
    //////////

    /**
     * Method used to set/update volume level for mob sound effects
     */
    private void setEffectVolume(){
        damagedSound.setVolume(getGameScreen().getGame().getVolume());
        attackSound.setVolume(getGameScreen().getGame().getVolume());
        deathSound.setVolume(getGameScreen().getGame().getVolume());
    }

    /**
     * Method fetches and sets mob sounds as relevant SFX
     */
    private void fetchAudioAssets(){

        try {
            // Fetch Attack SFX
            String soundAtkAssetName = this.name.toLowerCase() + "_attack";
            this.attackSound = getGameScreen().getGame().getAssetManager().getSound(soundAtkAssetName);

            // Fetch Damaged SFX
            String soundDmgAssetName = this.name.toLowerCase() + "_damaged";
            this.damagedSound = getGameScreen().getGame().getAssetManager().getSound(soundDmgAssetName);

            // Fetch Death SFX
            String soundDthAssetName = this.name.toLowerCase() + "_death";
            this.deathSound = getGameScreen().getGame().getAssetManager().getSound(soundDthAssetName);

            // Set SFX Volume
            setEffectVolume();
        } catch(Exception e){
            System.out.println("Missing SFX for Mob");
        }
    }

    /**
     * Method run the process of attacking an opponent mob
     */
    public void attackTarget(Mob targetedMob) {

        // Enemy Mob takes Damage
        targetedMob.decreaseHP(attackDamage);

        new PopUpObject(targetedMob.position.x+70, this.position.y+70, getGameScreen(), 30,
                "-" + attackDamage, 5, true);

        // Play SFX for Attack Sequence
        attackSequenceSFX();

        // Mob has attacked, can no longer be used this turn
        this.hasBeenUsed = true;

        // Update the HP value displayed of the mob
        this.updateMobBitmap();
    }

    /**
     * Method plays sequence of SFX for the attack sequence
     */
    private void attackSequenceSFX(){

        // Attacking Mob - Play Attack Sound
        if(this.attackSound != null)
            getGameScreen().getGame().getAudioManager().play(this.attackSound);

    }

    /**
     * Method decreases the HP of the Mob
     */
    private void decreaseHP(int damageInflicted){
        this.healthPoints -= damageInflicted;
    }


    //////////////
    // Draw Method
    //////////////

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D, LayerViewport layerViewport, ScreenViewport screenViewport) {
        super.draw(elapsedTime, graphics2D,layerViewport, screenViewport);
    }

    ////////////////////
    // Getters & Setters
    ////////////////////

    public int getHealthPoints(){ return healthPoints; }

    public void setHealthPoints(int healthPoints){ this.healthPoints = healthPoints; }

    public int getAttackDamage(){ return attackDamage; }

    public void setAttackDamage(int attackDamage){ this.attackDamage = attackDamage; }

    public Sound getDamagedSound(){ return damagedSound; }

    public Sound getAttackSound(){ return attackSound; }

    public Sound getDeathSound(){ return deathSound; }

    public String getName(){
        return this.name;
    }

    public int getMobID() {
        return mobID;
    }

    public boolean hasBeenUsed() {
        return hasBeenUsed;
    }

    public void setHasBeenUsed(boolean hasBeenUsed) {
        this.hasBeenUsed = hasBeenUsed;
    }

    public void updateMobBitmap(){
        this.mBitmap = CardBitmapFactory.returnMobBitmap(this, getGameScreen());
    }

    public boolean isSelectedToAttack() {
        return isSelectedToAttack;
    }

    public void setSelectedToAttack(boolean selectedToAttack) {
        isSelectedToAttack = selectedToAttack;
    }

    public EquipCard getEquipCard() {
        return equipCard;
    }

    public void setEquipCard(EquipCard equipCard) {
        this.equipCard = equipCard;
    }

}
