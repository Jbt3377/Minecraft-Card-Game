package uk.ac.qub.eeecs.game.spaceDemo;

import java.util.Random;

import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

/**
 * Simple asteroid
 *
 * Note: See the course documentation for extension/refactoring stories
 * for this class.
 *
 * @version 1.0
 */
public class Asteroid extends SpaceEntity {

    // /////////////////////////////////////////////////////////////////////////
    // Properties //
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Default size for the asteroid
     */
    private static final float DEFAULT_RADIUS = 20;

    private static String[] asteroid_image_names = {"Asteroid1", "Asteroid2", "Asteroid3",
            "Asteroid4", "Asteroid5"};


    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create an .asteroid
     *
     * @param startX     x location of the asteroid
     * @param startY     y location of the asteroid
     * @param gameScreen Gamescreen to which asteroid belongs
     */
    public  Asteroid(float startX, float startY, GameScreen gameScreen , int size) {
        super(startX, startY, (DEFAULT_RADIUS + size)*2.0f, (DEFAULT_RADIUS + size)*2.0f, null, gameScreen);

        Random random = new Random();

        int r = random.nextInt(4);

        /*
        mBitmap = gameScreen.getGame().getAssetManager()
                .getBitmap(random.nextBoolean() ? "Asteroid1" : "Asteroid2");

         */

        // Pick a random asteroid image from a set. Set used is determined by the size of the

        String asteroidName;
        float radius = DEFAULT_RADIUS + size;
        System.out.println(radius);
        if (radius <= 25) {
            asteroidName = random.nextBoolean() ? "AsteroidLowRes1" : "AsteroidLowRes2";
        }else if(radius <=50){
            asteroidName = random.nextBoolean() ? "AsteroidMedRes1" : "AsteroidMedRes2";
        }else{
            asteroidName = random.nextBoolean() ? "AsteroidHighRes1" : "AsteroidHighRes2";
        }

        mBitmap = gameScreen.getGame().getAssetManager()
                .getBitmap(asteroidName);

        mRadius = DEFAULT_RADIUS + size;
        mMass = 1000.0f;

        angularVelocity = random.nextFloat() * 240.0f - 20.0f;



    }
}
