package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;

import android.graphics.Bitmap;
import android.graphics.Paint;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import uk.ac.qub.eeecs.gage.MainActivity;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

/**
 * Utility Class used to draw static and moving images and text on screen
 */
public class PopUpObject extends Sprite {

    /////////////
    // Properties
    /////////////

    private int displayTime;
    private String textInput;
    private Paint textPaint;
    private boolean isMovingPopUp;
    private int movementSpeed;

    ///////////////
    // Constructors
    ///////////////

    /**
     * Public Constructor for image popup with overlaying text
     *
     * @param x           - X Coordinate of popup
     * @param y           - Y Coordinate of popup
     * @param bitmap      - Popup image
     * @param gameScreen  - Game screen to which the popup should be displayed
     * @param displayTime - Length of time the popup is displayed on screen
     * @param textInput   - Overlaying text
     */
    public PopUpObject(float x, float y, Bitmap bitmap, GameScreen gameScreen, int displayTime,
                       String textInput) {

        super(x, y, bitmap, gameScreen);

        this.displayTime = displayTime;
        this.textInput = textInput;
        this.isMovingPopUp = false;
        this.movementSpeed = 0;

        TextAlignmentUtil util = new TextAlignmentUtil(10, textAlignment.CENTRE);
        this.textInput = util.alignText(textInput);

        setupTextPaint();
        gameScreen.popUpObjects.add(this);

    }

    /**
     * Public Overload Constructor for moving text popup
     *
     * @param x           - X Coordinate of popup
     * @param y           - Y Coordinate of popup
     * @param gameScreen  - Game screen to which the popup should be displayed
     * @param displayTime - Length of time the popup is displayed on screen
     * @param textInput   - Overlaying text
     * @param goingUp - boolean flag determines direction of movement
     */
    public PopUpObject(float x, float y, GameScreen gameScreen, int displayTime, String textInput,
                       int movementSpeed, boolean goingUp) {

        super(x, y, gameScreen.getGame().getAssetManager().getBitmap("Potion of Healing"), gameScreen);

        this.displayTime = displayTime;
        this.isMovingPopUp = true;
        this.textInput = textInput;

        setupTextPaint();

        if (goingUp)
            this.movementSpeed = -movementSpeed;
        else
            this.movementSpeed = movementSpeed;

        gameScreen.popUpObjects.add(this);
    }


    ////////////////////////
    // Update & Draw Methods
    ////////////////////////


    @Override
    public void update(ElapsedTime elapsedTime) {
        super.update(elapsedTime);
        displayTime--;

        // Remove popup if display time has expired
        if (displayTime <= 0)
            mGameScreen.popUpObjects.remove(this);

        // Move popup position on y axis
        position.add(0, movementSpeed);
    }


    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

        if (!isMovingPopUp) {

            // Draw: Image
            super.draw(elapsedTime, graphics2D);

            if (textInput != null) {

                // Draw: Text Centred
                String[] lines = textInput.split("\n");
                Set<Integer> setOfYCoOrdinates = new HashSet<>();
                int numOfLines = lines.length;

                // Determine distance between each line
                int yCoordinateCount, upper;
                if(numOfLines % 2 == 0){
                    yCoordinateCount = 40;
                    upper = numOfLines;
                } else{
                    yCoordinateCount = 0;
                    upper = numOfLines+1;
                }

                // Set Y Coordinates for each line of text
                for (int i = 0; i < (upper / 2); i++) {
                    setOfYCoOrdinates.add(yCoordinateCount);
                    setOfYCoOrdinates.add(yCoordinateCount - yCoordinateCount * 2);
                    yCoordinateCount+=80;
                }

                Set<Integer> sortedTreeSet = new TreeSet<>(setOfYCoOrdinates);
                Iterator treeSetIter = sortedTreeSet.iterator();

                // Draw each line of text
                for(String line: lines)
                    graphics2D.drawText(line, position.x, position.y + ((int)treeSetIter.next()), textPaint);
            }
        }else {

            // Draw: Moving Text
            graphics2D.drawText(textInput, position.x, position.y, textPaint);
        }

    }

    //////////
    // Methods
    //////////

    /**
     * Method used to setup text paint
     */
    private void setupTextPaint(){
        textPaint = new Paint();
        textPaint.setTypeface(MainActivity.minecraftRegFont);
        textPaint.setTextSize(mGameScreen.getGame().getScreenWidth() * 0.030f);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

}
