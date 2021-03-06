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
     * Method to show hint
     *
     * @param hint Hint
     */
    void showHint(String hint, int gameIndex);

    /**
     * Hide game to specatators
     *
     * @param gameIndex index of game
     */ 
    void removeGame(int gameIndex);
}
