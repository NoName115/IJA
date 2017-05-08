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

    private BasePile getBasePileByIndex(int gameIndex, int index) {
        if (index == 0) {
            return gi[gameIndex].s();
        } else if (index == 1) {
            return gi[gameIndex].w();
        } else if (index > 1 && index < 6) {
            return gi[gameIndex].t(index - 2);
        } else {
            return gi[gameIndex].f(index - 6);
        }
    }

    private void removeCardsFromPile(BasePile pile, int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            pile.popCard();
        }
    }

    private void addCardsToPile(BasePile pile, String[] cards) {
        for (int i = cards.length - 1; i >= 0; i--) {
            pile.pushCard(new Card(cards[i]));
        }
    }

    @Override
    public void moveCard(int playground, int from, int to, int numberOfCards) {

    }

    @Override
    public void addCards(int playground, int to, String[] cards) {
        System.out.println("adding cards");
        addCardsToPile(getBasePileByIndex(playground, to), cards);
        display.repaint();
    }

    @Override
    public void removeCards(int playground, int from, int numberOfCards) {
        System.out.println("removing cards");
        removeCardsFromPile(getBasePileByIndex(playground, from), numberOfCards);
        display.repaint();
    }

    @Override
    public void playgroundUpdate(int index, UpdatePlayground up) {
        System.out.println(up);
        gi[index].deserialize(up);
        display.repaint();
    }

    @Override
    public void closePlayground(int index) {

    }

    @Override
    public void showHint(String hint) {

    }
}