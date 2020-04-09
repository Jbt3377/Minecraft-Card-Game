package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import java.util.List;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.input.TouchEvent;

public abstract class Interaction {

    public static void processDragEvents(List<TouchEvent> touchEvents, Draggable dObj, Game game){


            float touchOffsetX = 0.0f;
            float touchOffsetY = 0.0f;

            for(TouchEvent t : touchEvents){
                float x_cor = t.x;
                float y_cor = game.getScreenHeight() - t.y;


                if(t.type == TouchEvent.TOUCH_SINGLE_TAP  && dObj.getBoundingBox().contains(x_cor,y_cor)){
                    if (game.isMagnificationToggled()) {
                        //TODO - Implement zoom functionality
                    }
                }

                if(dObj.getBoundingBox().contains(x_cor,y_cor)  && (game.isCardsSelected() == false)  && t.type == TouchEvent.TOUCH_DOWN){
                        game.setCardsSelected(true);
                        dObj.setHasBeenSelected(true);
                        touchOffsetX = x_cor - dObj.getCurrentXPosition();
                        touchOffsetY = y_cor - dObj.getCurrentYPosition();

                }


                if(t.type == TouchEvent.TOUCH_DRAGGED && dObj.getHasBeenSelected() && !game.isMagnificationToggled()){
                    dObj.setNewPosition(x_cor - touchOffsetX, y_cor - touchOffsetY);

                }

                if(t.type == TouchEvent.TOUCH_UP){
                        game.setCardsSelected(false);
                        dObj.setHasBeenSelected(false);
                }

            }
            }
        }




