package uk.ac.qub.eeecs.game.GameObjects.ContainerClasses;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.PlayerClasses.Human;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.Draggable;


public class MobContainer extends Sprite implements Container {

    /////////////
    // Properties
    /////////////

    private boolean isEmpty;
    private Mob containedMob;
    public enum ContainerType{ BOTTOM_PLAYER, TOP_PLAYER, UTILITY_CARD}
    private ContainerType contType;

    private float x_location;
    private float y_location;

    //////////////
    // Constructor
    //////////////

    public MobContainer(float x, float y, ContainerType contType, GameScreen gameScreen){
        super(x, y,
                gameScreen.getGame().getScreenWidth() * 0.104f * 1.17f,
                gameScreen.getGame().getScreenHeight() * 0.185f * 1.17f,
                gameScreen.getGame().getAssetManager().getBitmap("ItemFrame"), gameScreen);

        this.isEmpty = true;
        this.containedMob = null;
        this.contType = contType;
        this.x_location = x;
        this.y_location = y;

        if(contType == MobContainer.ContainerType.UTILITY_CARD){
            mBitmap = gameScreen.getGame().getAssetManager().getBitmap("ItemFrameUtility");
        }
    }

    //////////
    // Methods
    //////////

    /**
     * Method to occupy Container with placed Mob
     * @return - Indicates if mob was dropped inside the container
     */
    public boolean placeCard(Mob mob){

        if (isEmpty) {
            containedMob = mob;
            isEmpty = false;
            return true;
        } else
            return false;
    }

    /**
     * Method clears container of held mob
     */
    public void emptyContainer() {
        this.isEmpty = true;
        this.containedMob = null;
    }


    public boolean checkForNewContents(List<TouchEvent> input, Draggable dObj) {

        if (this.contType == ContainerType.BOTTOM_PLAYER && isEmpty()) {
            for (TouchEvent event : input) {

                float x_cor = event.x;
                float y_cor = convertYAxisToLayerView(event.y);

                if (mBound.contains(dObj.getCurrentXPosition(), dObj.getCurrentYPosition())) {
                    return true;
                }
            }

        }else if(this.contType == ContainerType.TOP_PLAYER && isEmpty()){

            for (TouchEvent event : input) {

                float x_cor = event.x;
                float y_cor = convertYAxisToLayerView(event.y);

                if (mBound.contains(dObj.getCurrentXPosition(), dObj.getCurrentYPosition())) {
                    return true;
                }
            }

        }
    return false;
    }

    public boolean checkForUtilityCard(List<TouchEvent> input, Draggable dObj){
        if (this.contType == ContainerType.UTILITY_CARD && isEmpty()) {
            for (TouchEvent event : input) {

                float x_cor = event.x;
                float y_cor = convertYAxisToLayerView(event.y);

                if (mBound.contains(dObj.getCurrentXPosition(), dObj.getCurrentYPosition())) {
                    return true;
                }
            }
        }
        return false;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Getters
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isEmpty() {
        return isEmpty;
    }

    public Mob getContents() {
        return containedMob;
    }

    public ContainerType getContType() { return contType; }

    public float getX_location() {
        return x_location;
    }

    public float getY_location() {
        return y_location;
    }

}
