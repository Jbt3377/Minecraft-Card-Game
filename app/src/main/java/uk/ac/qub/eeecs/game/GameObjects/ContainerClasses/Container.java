package uk.ac.qub.eeecs.game.GameObjects.ContainerClasses;

public interface Container<T> {

    void emptyContainer();
    T getContents();
    boolean isEmpty();

}
