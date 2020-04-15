package uk.ac.qub.eeecs.game.GameObjects.CardStatsClasses;

public class EquipCardStats extends CardStats {

    /**
     * Public constructor for EquipCardStats object.
     * @param name - The name of card.
     * @param manaCost - The mana cost to play this card.
     * @param descText - The description text of this card.
     * @param id - The id of this card.
     */
    public EquipCardStats(String name, int manaCost, String descText, int id) {
        super(name, manaCost, descText, id);
    }

    /*
    Intended functionality would have made this class more distinct
    from the super class, however time constraints meant that such
    functionality could not be added.
     */

}
