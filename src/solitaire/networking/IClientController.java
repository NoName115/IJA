package solitaire.networking;

import solitaire.PlayGround;

public interface IClientController {
    /**
     * Method which controlls moving cards between piles
     *
     * @param from source pile index
     * @param to destination pile index
     * @param numberOfCards number of cards
     */
    void moveCard(int from, int to, int numberOfCards);

    /**
     * Status of last created operation
     *
     * @param valid true if valid
     */
    void lastOperationStatus(boolean valid);

    /**
     * Method updates whole playground
     *
     * @param index index of playground
     */
    void playgroundUpdate(int index);

    /**
     * Method to close playground
     *
     * @param index index of playground
     */
    void closePlayground(int index);
}
