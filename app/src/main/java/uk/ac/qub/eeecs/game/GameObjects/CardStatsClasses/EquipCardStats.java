package uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses;

public class EquipCardStats extends CardStats {

    int effect_intensity;
    public int getEffect_intensity() {
        return effect_intensity;
    }
    public void setEffect_intensity(int effect_intensity) {
        this.effect_intensity = effect_intensity;
    }



    /**
     * Public constructor for EquipCardStats object.
     * @param name - The name of card.
     * @param manaCost - The mana cost to play this card.
     * @param descText - The description text of this card.
     * @param id - The id of this card.
     */
    public EquipCardStats(String name, int manaCost, String descText, int id, int effect_intensity) {
        super(name, manaCost, descText, id);
        this.effect_intensity = effect_intensity;
    }

    /*
    Intended functionality would have made this class more distinct
    from the super class, however time constraints meant that such
    functionality could not be added.
     */

}
