package uk.ac.qub.eeecs.game.GameObjects.UtilityClasses;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import uk.ac.qub.eeecs.gage.engine.AssetManager;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.Card;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.CharacterCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.EquipCard;
import uk.ac.qub.eeecs.game.GameObjects.CardClasses.UtilityCard;
import uk.ac.qub.eeecs.game.GameObjects.ContainerClasses.Mob;

public class CardBitmapFactory {
    public static Bitmap returnBitmap(Card card, GameScreen gameScreen) {
        AssetManager assetManager = gameScreen.getGame().getAssetManager();

        if (card instanceof CharacterCard) {
            Bitmap characterCardBitmap = returnCharacterCardBitmap((CharacterCard) card, gameScreen);
            return characterCardBitmap;

        } else if (card instanceof EquipCard) {
            Bitmap equipCardBitmap = returnEquipCardBitmap(card,gameScreen);
            return equipCardBitmap;

        } else if (card instanceof UtilityCard) {
            Bitmap utilityCardBitmap = returnUtilityCardBitmap(card,gameScreen);
            return utilityCardBitmap;

        } else {
            return assetManager.getBitmap("CardBackground");
        }


    }

    public static Bitmap returnMobBitmap(Mob mob, GameScreen gameScreen){
        AssetManager assetManager = gameScreen.getGame().getAssetManager();
        String name = mob.getName();
        int attackDmg = mob.getAttackDamage();
        int healthPoints = mob.getHealthPoints();

        Bitmap background;

        if(mob.isSelectedToAttack()){
            background = assetManager.getBitmap("MobSelected");
        } else{
            background = assetManager.getBitmap("MobBackground");
        }

        if(mob.hasBeenUsed()){
            background = assetManager.getBitmap("MobHasBeenUsed");
        }


        int cardWidth = background.getWidth();
        int cardHeight = background.getHeight();

        int portraitXScaling = cardWidth;
        int portraitYScaling = (int) (cardHeight * 0.8);
        int portraitXLocation = 0;
        int portraitYLocation = 0;
        Bitmap portrait = Bitmap.createScaledBitmap(assetManager.getBitmap(name), portraitXScaling, portraitYScaling, false);

        //Dealing with font for card stats
        Paint cardStatsFont = new Paint();
        int cardStatsFontSize = (int) (cardWidth * 0.19);
        cardStatsFont.setTextSize(cardStatsFontSize);
        cardStatsFont.setARGB(180, 255, 255, 255);
        cardStatsFont.setTypeface(assetManager.getFont("MinecraftRegFont"));


        //Part of factory dealing with attack damage.
        int attackDmgXLocation = (int) (cardWidth * 0.10);
        int attackDmgYLocation = (int) (cardHeight * 0.96);

        //Part of the factory dealing with health points
        int healthPointsXLocation;
        if(healthPoints > 9){
            healthPointsXLocation = (int) (cardWidth * 0.60);
        }else{
            healthPointsXLocation = (int) (cardWidth * 0.75);
        }
        int healthPointsYLocation = (int) (cardHeight * 0.96);




        //Part of the factory dealing with the description text.
        Paint cardDescFont = new Paint();
        int cardDescTextSize = (int) (cardWidth * 0.10);
        cardDescFont.setTextSize(cardDescTextSize);
        cardDescFont.setARGB(255, 255, 255, 255);
        cardDescFont.setTypeface(assetManager.getFont("MinecraftRegFont"));


        //All Bitmaps are 'stitched together' below.
        Bitmap result = Bitmap.createBitmap(cardWidth, cardHeight, background.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(portrait, portraitXLocation, portraitYLocation, null);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText(Integer.toString(attackDmg), attackDmgXLocation, attackDmgYLocation, cardStatsFont);
        canvas.drawText(Integer.toString(healthPoints), healthPointsXLocation, healthPointsYLocation, cardStatsFont);


        return result;
    }

    public static Bitmap returnCharacterCardBitmap(CharacterCard card, GameScreen gameScreen) {
        AssetManager assetManager = gameScreen.getGame().getAssetManager();
        String name = card.getCardName();
        int manaCost = card.getManaCost();
        String cardDesc = card.getCardDescription();
        int healthPoints = card.getmHP();
        int attackDmg = card.getmAttackDmg();

        Bitmap background = assetManager.getBitmap("CardBackground");
        int cardWidth = background.getWidth();
        int cardHeight = background.getHeight();

        //Part of factory dealing with portrait on card.
        int portraitXScaling = cardWidth;
        int portraitYScaling = (int) (cardHeight * 0.6);
        int portraitXLocation = 0;
        int portraitYLocation = 0;
        Bitmap portrait = Bitmap.createScaledBitmap(assetManager.getBitmap(name), portraitXScaling, portraitYScaling, false);

        //Dealing with font for card stats
        Paint cardStatsFont = new Paint();
        int cardStatsFontSize = (int) (cardWidth * 0.19);
        cardStatsFont.setTextSize(cardStatsFontSize);
        cardStatsFont.setARGB(180, 255, 255, 255);
        cardStatsFont.setTypeface(assetManager.getFont("MinecraftRegFont"));


        //Part of factory dealing with attack damage.
        int attackDmgXLocation = (int) (cardWidth * 0.10);
        int attackDmgYLocation = (int) (cardHeight * 0.96);

        //Part of factory dealing with mana cost.
        int manaCostXLocation = (int) (cardWidth * 0.43);
        int manaCostYLocation = (int) (cardHeight * 0.96);

        //Part of the factory dealing with health points
        int healthPointsXLocation = (int) (cardWidth * 0.76);
        int healthPointsYLocation = (int) (cardHeight * 0.96);

        //Part of the factory dealing with the description text.
        Paint cardDescFont = new Paint();
        int cardDescTextSize = (int) (cardWidth * 0.10);
        cardDescFont.setTextSize(cardDescTextSize);
        cardDescFont.setARGB(255, 255, 255, 255);
        cardDescFont.setTypeface(assetManager.getFont("MinecraftRegFont"));
        int cardTextXLocation = (int) (cardWidth * 0.08);
        int cardTextYLocation = (int) (cardHeight * 0.70);

        //All Bitmaps are 'stitched together' below.
        Bitmap result = Bitmap.createBitmap(cardWidth, cardHeight, background.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(portrait, portraitXLocation, portraitYLocation, null);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText(Integer.toString(attackDmg), attackDmgXLocation, attackDmgYLocation, cardStatsFont);
        canvas.drawText(Integer.toString(manaCost), manaCostXLocation, manaCostYLocation, cardStatsFont);
        canvas.drawText(Integer.toString(healthPoints), healthPointsXLocation, healthPointsYLocation, cardStatsFont);
        canvas.drawText(cardDesc, cardTextXLocation, cardTextYLocation, cardDescFont);

        return result;
    }

    public static Bitmap returnEquipCardBitmap(Card card, GameScreen gameScreen) {
        AssetManager assetManager = gameScreen.getGame().getAssetManager();
        String name = card.getCardName();
        int manaCost = card.getManaCost();
        String cardDesc = card.getCardDescription();

        Bitmap background = assetManager.getBitmap("CardBackground_Equip");

        int cardWidth = background.getWidth();
        int cardHeight = background.getHeight();

        //Part of factory dealing with the portrait on card.
        int portraitXScaling = cardWidth;
        int portraitYScaling = (int) (cardHeight * 0.6);
        int portraitXLocation = 0;
        int portraitYLocation = 0;
        Bitmap portrait = Bitmap.createScaledBitmap(assetManager.getBitmap(name), portraitXScaling,portraitYScaling,false);

        //Part of the factory dealing with the description text.
        Paint cardDescFont = new Paint();
        int cardDescTextSize = (int) (cardWidth * 0.10);
        cardDescFont.setTextSize(cardDescTextSize);
        cardDescFont.setARGB(255, 255, 255, 255);
        cardDescFont.setTypeface(assetManager.getFont("MinecraftRegFont"));
        int cardTextXLocation = (int)(cardWidth * 0.08);
        int cardTextYLocation = (int)(cardHeight * 0.70);

        //Dealing with font for card stats
        Paint cardStatsFont = new Paint();
        int cardStatsFontSize = (int) (cardWidth * 0.19);
        cardStatsFont.setTextSize(cardStatsFontSize);
        cardStatsFont.setARGB(180, 255, 255, 255);
        cardStatsFont.setTypeface(assetManager.getFont("MinecraftRegFont"));


        //Part of the factory dealing with the mana cost.
        int manaCostXLocation = (int) (cardWidth * 0.43);
        int manaCostYLocation = (int) (cardHeight * 0.96);

        //All Bitmaps are 'stitched together' below.
        Bitmap result = Bitmap.createBitmap(cardWidth,cardHeight,background.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(portrait,portraitXLocation,portraitYLocation,null);
        canvas.drawBitmap(background,0,0,null);

        canvas.drawText(Integer.toString(manaCost), manaCostXLocation, manaCostYLocation, cardStatsFont);

        canvas.drawText(cardDesc,cardTextXLocation,cardTextYLocation,cardDescFont);

        return result;
    }

    public static Bitmap returnUtilityCardBitmap(Card card, GameScreen gameScreen) {
        AssetManager assetManager = gameScreen.getGame().getAssetManager();
        String name = card.getCardName();
        int manaCost = card.getManaCost();
        String cardDesc = card.getCardDescription();

        Bitmap background = assetManager.getBitmap("CardBackground_Util");

        int cardWidth = background.getWidth();
        int cardHeight = background.getHeight();

        //Part of factory dealing with the portrait on card.
        int portraitXScaling = cardWidth;
        int portraitYScaling = (int) (cardHeight * 0.6);
        int portraitXLocation = 0;
        int portraitYLocation = 0;
        Bitmap portrait = Bitmap.createScaledBitmap(assetManager.getBitmap(name), portraitXScaling,portraitYScaling,false);

        //Part of the factory dealing with the description text.
        Paint cardDescFont = new Paint();
        int cardDescTextSize = (int) (cardWidth * 0.10);
        cardDescFont.setTextSize(cardDescTextSize);
        cardDescFont.setARGB(255, 255, 255, 255);
        cardDescFont.setTypeface(assetManager.getFont("MinecraftRegFont"));
        int cardTextXLocation = (int)(cardWidth * 0.08);
        int cardTextYLocation = (int)(cardHeight * 0.70);

        //Dealing with font for card stats
        Paint cardStatsFont = new Paint();
        int cardStatsFontSize = (int) (cardWidth * 0.19);
        cardStatsFont.setTextSize(cardStatsFontSize);
        cardStatsFont.setARGB(180, 255, 255, 255);
        cardStatsFont.setTypeface(assetManager.getFont("MinecraftRegFont"));


        //Part of the factory dealing with the mana cost.
        int manaCostXLocation = (int) (cardWidth * 0.43);
        int manaCostYLocation = (int) (cardHeight * 0.96);

        //All Bitmaps are 'stitched together' below.
        Bitmap result = Bitmap.createBitmap(cardWidth,cardHeight,background.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(portrait,portraitXLocation,portraitYLocation,null);
        canvas.drawBitmap(background,0,0,null);

        canvas.drawText(Integer.toString(manaCost), manaCostXLocation, manaCostYLocation, cardStatsFont);

        canvas.drawText(cardDesc,cardTextXLocation,cardTextYLocation,cardDescFont);

        return result;
    }
}
