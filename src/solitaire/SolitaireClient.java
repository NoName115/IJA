package solitaire;

import solitaire.networking.IClientController;
import solitaire.networking.Network;
import solitaire.networking.Network.*;
import solitaire.networking.NetworkClient;
import solitaire.piles.*;

import java.util.*;


public class SolitaireClient implements IClientController {
    
    // Spustenie clienta
    public static void main(String[] args) {
        new SolitaireClient();
    }

    private GameInstance[] gi;

    private SolitaireDisplay display;
    private NetworkClient client;

    public SolitaireClient() {

        gi = new GameInstance[4];
        for (int i = 0; i < gi.length; i++) {
            gi[i] = new GameInstance(false);
        }

        display = new SolitaireDisplay(this);
        client = new NetworkClient(this);
    }

    // Klient - kreslenie
    public Card getStockCard(int gameIndex) {
        return gi[gameIndex].s().getCard();
    }

    // Klient - kreslenie
    public Card getWasteCard(int gameIndex) {
        return gi[gameIndex].w().getCard();
    }

    // Klient - kreslenie
    // TODO
    // Funkcia je FoundationCard, ale vracia tableauPile ?? :D :D
    public Card getFoundationCard(int index, int gameIndex) {
        if (gi[gameIndex].t(index).isEmpty())
        {
            return null;
        }

        return gi[gameIndex].t(index).getCard();
    }

    // Klient - kreslenie
    public Stack<Card> getPile(int index, int gameIndex) {
        return gi[gameIndex].f(index).getPile();
    }

    // Client - send message that stock is clicked
    public void stockClicked(int gameIndex) {
        System.out.println("stock clicked");

        display.unselect(gameIndex);

        if (!display.isWasteSelected(gameIndex) && !display.isPileSelected(gameIndex)) {

            client.makeMove(gameIndex, 0, 1);
            display.unselect(gameIndex);
        }

    }

    public void wasteClicked(int gameIndex) {
        System.out.println("waste clicked");

        if (!gi[gameIndex].w().isEmpty()) {
            if (!display.isWasteSelected(gameIndex))
            {
                display.selectWaste(gameIndex);
            }
            else
            {
                display.unselect(gameIndex);
            }
        }
    }

    public void tableauClicked(int index, int gameIndex) {
        System.out.println("tableau #" + index + " clicked");

        if (display.isWasteSelected(gameIndex))
        {
            client.makeMove(gameIndex, 1, index + 2);
            display.unselect(gameIndex);
        }

        if (display.isPileSelected(gameIndex))
        {
            client.makeMove(gameIndex, 6 + display.selectedPile(gameIndex), index + 2);
            display.unselect(gameIndex);
        }
    }

    public void foundationClicked(int index, int gameIndex) {
        System.out.println("foundation #" + index + " clicked");

        if (display.isWasteSelected(gameIndex))
        {
            client.makeMove(gameIndex, 1, 6 + index);
            display.unselect(gameIndex);

        }
        else if (display.isPileSelected(gameIndex))
        {
            client.makeMove(gameIndex, 6 + display.selectedPile(gameIndex), 6 + index);
            display.unselect(gameIndex);
        }
        else
        {
            display.selectPile(index, gameIndex);
            gi[gameIndex].f(index).getCard().turnUp();
        }
    }

    public void undo(int gameIndex) {
        client.undo(gameIndex);
    }

    public void hint(int gameIndex) {
        client.getHint(gameIndex);
    }

    public void saveGame(int gameIndex) {
        client.save(gameIndex);
    }

    public void loadGame(int gameIndex) {
        client.load(gameIndex);
    }

    public void activateGame(int gameIndex) {
        client.addGame(gameIndex);
    }

    public void disableGame(int gameIndex) {
        client.closeGame(gameIndex);
    }

    @Override
    public void addCards(int playground, int to, String[] cards) {
        gi[playground].addCardsToPile(to, cards);
        display.repaint();
    }

    @Override
    public void removeCards(int playground, int from, int numberOfCards) {
        gi[playground].removeCardsFromPile(from, numberOfCards);
        display.repaint();
    }

    @Override
    public void playgroundUpdate(int index, UpdatePlayground up) {
        System.out.println(up);
        gi[index].deserialize(up);
        display.setGameRunning(up.playground, true);
        display.repaint();
    }

    @Override
    public void showHint(String hint, int gameIndex) {
        this.display.showHint(hint);
    }

    @Override
    public void removeGame(int gameIndex) {
        display.setGameRunning(gameIndex, false);
    }
}