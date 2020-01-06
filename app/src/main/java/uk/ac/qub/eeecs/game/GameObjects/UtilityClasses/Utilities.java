package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;


import uk.ac.qub.eeecs.gage.Game;

public class Utilities {

    public static float convertYAxisToLayerView(float yInputFromScreenView, Game mGame){
        float screenHeight = mGame.getScreenHeight();
        return screenHeight - yInputFromScreenView;
    }

}
