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
            throw new IllegalArgumentException("Invalid Max Characters: Must be greater than 1");

    }

    public String alignText(String text){

        switch(this.alignment){
            case LEFT:
                return alignLeft(text);
            case CENTRE:
                return alignCentre(text);
            default:
                throw new IllegalArgumentException("No Alignment Set");
        }

    }


    private String alignLeft(String input){

        StringBuilder output = new StringBuilder();
        String[] words = input.split(" ");

        List<String> listOfWords = new ArrayList<>();
        Collections.addAll(listOfWords, words);
        ListIterator<String> iter = listOfWords.listIterator();

        int lineLength = 0;
        int currentWord = 0;
        while(iter.hasNext()){

            String word = iter.next();
            currentWord++;

            if(word.length() > maxCharsPerLine){

                String part1 = word.substring(0, maxCharsPerLine);
                String part2 = word.substring(maxCharsPerLine);
                listOfWords.add(currentWord, part2);
                word = part1;
                lineLength = 0;

            }else if(word.length() + lineLength > maxCharsPerLine){

                output.append("\n");
                lineLength = 0;

            }else if (lineLength > 0){
                output.append(" ");
            }

            output.append(word);
            lineLength += (word.length() + 1);
        }

        return output.toString();
    }


    private String alignCentre(String input){

        String[] words = input.split(" ");
        List<String> strings = new ArrayList<>();
        Collections.addAll(strings, words);
        ListIterator<String> iter = strings.listIterator();

        System.out.println("here");

        StringBuffer output = new StringBuffer();

        while(iter.hasNext()){

            String word = iter.next();

            int spacesToAddToEachSide = (maxCharsPerLine - word.length())/2;
            pad(output, spacesToAddToEachSide);
            output.append(word);
            pad(output, spacesToAddToEachSide);
            output.append("\n");
        }



        return output.toString();
    }

    private void pad(StringBuffer output, int spaces){
        for(int i=0; i<spaces; i++) output.append(" ");
    }

}
