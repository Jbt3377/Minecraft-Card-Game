package uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses;

public class UtilityCardStats extends CardStats {
    //Properties
    private int effect_intensity;

    /**
     * Public constructor for UtilityCardStats object.
     * @param name - The name of card.
     * @param manaCost - The mana cost to play this card.
     * @param descText - The description text of this card.
     * @param id - The id of this card.
     * @param effect_intensity - The effect intensity of the card.
     */
    public UtilityCardStats(String name, int manaCost, String descText, int id, int effect_intensity) {
        super(name, manaCost, descText, id);
        this.effect_intensity = effect_intensity;
    }

    /*
    Intended functionality would have made this class more distinct
    from the super class, however time constraints meant that such
    functionality could not be added.
     */

    //Getters & Setters
    public int getEffect_intensity() {
        return effect_intensity;
    }
    public void setEffect_intensity(int effect_intensity) {
        this.effect_intensity = effect_intensity;
    }
}
