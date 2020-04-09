package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

enum textAlignment {
    LEFT, CENTRE
}

public class TextAlignmentUtil {

    private int maxCharsPerLine;
    private textAlignment alignment;

    public TextAlignmentUtil(int maxCharsPerLine, textAlignment alignment){

        this.maxCharsPerLine = maxCharsPerLine;
        this.alignment = alignment;

        if(maxCharsPerLine<1)
            throw new IllegalArgumentException("Invalid maxChars: Must be greter than 1");

    }


    private String formatLeft(String input){

        StringBuilder output = new StringBuilder(input.length());
        List<String> listOfWords = new ArrayList<>();
        ListIterator<String> wordIterator = listOfWords.listIterator();

        String[] words = input.split(" ");
        Collections.addAll(listOfWords, words);


        int lineLength = 0;
        int wordCount = 0;
        while(wordIterator.hasNext()){

            String word = wordIterator.next();

            // if word length > max, substring word

            if(lineLength + word.length() > maxCharsPerLine) {
                output.append("\n");
                lineLength = 0;
            }
            output.append(word);
            lineLength += word.length();
        }

        return output.toString();


    }

}
