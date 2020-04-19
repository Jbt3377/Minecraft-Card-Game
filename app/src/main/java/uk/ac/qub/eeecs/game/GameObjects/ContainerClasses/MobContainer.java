package uk.ac.qub.eeecs.game.GameObjects.ContainerClasses;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.UtilityCard;
import uk.ac.qub.eeecs.game.GameObjects.UtilityClasses.Draggable;

public class MobContainer extends Sprite implements Container {

    /////////////
    // Properties
    /////////////

    public enum ContainerType{ BOTTOM_PLAYER, TOP_PLAYER, UTILITY_CARD}

    private float x_location;
    private float y_location;
    private boolean isEmpty;
    private Mob containedMob;
    private ContainerType contType;

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

    /**
     * Method used to determine if an Draggable Object can be dropped into the container
     * @param dObj - Draggable object, such as a card
     * @return Boolean flag indicating if it can be placed into container of this type and condition
     */
    public boolean checkCharacterEquipCanBeDropped(Draggable dObj) {

        float dObjCurrentXPos = dObj.getCurrentXPosition();
        float dObjCurrentYPos = dObj.getCurrentYPosition();

        if(dObj instanceof CharacterCard)
            return (this.contType != ContainerType.UTILITY_CARD && isEmpty() &&
                    mBound.contains(dObjCurrentXPos, dObjCurrentYPos));

        else if(dObj instanceof EquipCard && this.containedMob != null) {
            try{
                if(containedMob.getEquipCard() == null) {
                    return (this.contType != ContainerType.UTILITY_CARD && !isEmpty() &&
                            mBound.contains(dObjCurrentXPos, dObjCurrentYPos));
                }
                return false;
            }catch (Exception e) {
                return false;
            }

        }else
            return false;
    }

    /**
     * Method used to determine if a Draggable Object can be dropped into the container
     * @param dObj - Draggable object, such as a card
     * @return Boolean flag indicating if it can be placed into utility container type and condition
     */
    public boolean checkUtilityCanBeDropped(Draggable dObj){

        float dObjCurrentXPos = dObj.getCurrentXPosition();
        float dObjCurrentYPos = dObj.getCurrentYPosition();

        if(dObj instanceof UtilityCard)
            return (this.contType == ContainerType.UTILITY_CARD && isEmpty() &&
                    mBound.contains(dObjCurrentXPos, dObjCurrentYPos));
        else
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

    public ContainerType getContType() {
        return contType;
    }

    public float getX_location() {
        return x_location;
    }

    public float getY_location() {
        return y_location;
    }

}
