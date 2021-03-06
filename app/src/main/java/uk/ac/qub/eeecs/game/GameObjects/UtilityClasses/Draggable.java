package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import uk.ac.qub.eeecs.gage.util.BoundingBox;

public interface Draggable {

    float getCurrentXPosition();
    /*
    Provide the objects current x position;
     */

    float getCurrentYPosition();
    /*
    Provide the object current y position;
     */

    void setNewPosition(float newXPosition, float newYPosition);
    /*
    Provide a method to set the position of the object
     */

    BoundingBox getBoundingBox();
    /*
    Provide a method to return the BoundingBox of the object.
     */

    boolean getHasBeenSelected();
    /*
    Provide a boolean that indicates if an object has been touched or not.
     */

    void setHasBeenSelected(boolean hasBeenSelected);
    /*
    Provide a method to change the hasBeenSelected boolean for this object.
     */

    float getOriginalXPos();
    /*
    Provide a method to get the cards x position before dragging took place
     */

    float getOriginalYPos();
    /*
    Provide a method to get the cards y position before dragging took place
     */

    void setOriginalXPos(float xPos);
    /*
    Provide a method to set the cards x position before dragging took place
     */

    void setOriginalYPos(float yPos);
    /*
    Provide a method to set the cards y position before dragging took place
     */


}
