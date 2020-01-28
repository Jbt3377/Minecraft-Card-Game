package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;

public abstract class Interaction {

    public static void processDragEvents(List<TouchEvent> touchEvents, Draggable dObj, Game game){


        boolean cardsSelected = false;


            float touchOffsetX = 0.0f;
            float touchOffsetY = 0.0f;

            for(TouchEvent t : touchEvents){
                float x_cor = t.x;
                float y_cor = game.getScreenHeight() - t.y;

                //Changes the cardFaceUp boolean if the card is single tapped - MMC
                if(t.type == TouchEvent.TOUCH_SINGLE_TAP  && dObj.getBoundingBox().contains(x_cor,y_cor)){
                    if (game.isMagnificationToggled()) {

                    } else {
                        //flipTimer = FLIP_TIME;
                    }
                }

                if(dObj.getBoundingBox().contains(x_cor,y_cor)  && (cardsSelected == false)  && t.type == TouchEvent.TOUCH_DOWN){
                        cardsSelected = true;
                        dObj.setHasBeenSelected(true);
                        touchOffsetX = x_cor - dObj.getCurrentXPosition();
                        touchOffsetY = y_cor - dObj.getCurrentYPosition();

                }


                if(t.type == TouchEvent.TOUCH_DRAGGED && dObj.getHasBeenSelected() && !game.isMagnificationToggled()){
                    dObj.setNewPosition(x_cor - touchOffsetX, y_cor - touchOffsetY);

                }

                if(t.type == TouchEvent.TOUCH_UP){
                        cardsSelected = false;
                        dObj.setHasBeenSelected(false);
                }

            }
            }
        }




