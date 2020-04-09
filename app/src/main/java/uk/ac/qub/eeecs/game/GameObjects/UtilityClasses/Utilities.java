package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;


import uk.ac.qub.eeecs.gage.Game;

public class Utilities {

    public static float convertYAxisToLayerView(float yInputFromScreenView, Game mGame){
        float screenHeight = mGame.getScreenHeight();
        return screenHeight - yInputFromScreenView;
    }

//    private String insertNewLines(String text){
//        StringBuilder textBuilder = new StringBuilder("");
//
//        // Split string into words
//        String[] words = text.split(" ");
//
//        int lineLength = 0;
//        for (String word : words)
//        {
//            // Return null if a word exceeds the maximum limit.
//            if (word.length() > TEXT_MAX_LINE_LENGTH)
//            {
//                return null;
//            }
//
//            // If appending this word will exceed the max length, take a new line.
//            if ((lineLength + word.length()) > TEXT_MAX_LINE_LENGTH)
//            {
//                textBuilder.append("\n");
//                lineLength = 0;
//            }
//            // If not appending the first word, add a space first.
//            else if (lineLength > 0)
//            {
//                textBuilder.append(" ");
//            }
//            textBuilder.append(word);
//            lineLength += (word.length() + 1);
//        }
//
//        return textBuilder.toString();
//    }

}
