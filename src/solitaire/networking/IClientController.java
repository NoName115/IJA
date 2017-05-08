package solitaire.networking;
import solitaire.networking.Network.*;

public interface IClientController {

    void addCards(int playground, int to, String[] cards);

    void removeCards(int playground, int from, int numberOfCards);
    /**
     * Method updates whole playground
     *
     * @param index index of playground
     */
    void playgroundUpdate(int index, UpdatePlayground up);

    /**
     * Method to close playground
     *
     * @param index index of playground
     */
    void closePlayground(int index);

    /**
     * Method to show hint
     *
     * @param hint Hint
     */
    void showHint(String hint);
}
