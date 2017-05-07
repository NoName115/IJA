package solitaire;

import solitaire.networking.IClientController;
import solitaire.networking.Network;
import solitaire.networking.Network.*;
import solitaire.networking.NetworkClient;
import solitaire.piles.*;

import java.util.*;

public class SolitaireClient implements IClientController {
    public static void main(String[] args) {
        new SolitaireClient();
    }

    private StockPile stockPile; // 0
    private WastePile wastePile; // 1
    private TableauPile[] tableauPiles; // 2, 3, 4, 5
    private FoundationPile[] foundationPiles; // 6, 7, 8, 9, 10, 11, 12

    private SolitaireDisplay display;
    private NetworkClient client;

    public SolitaireClient() {
        tableauPiles = new TableauPile[4];
        for (int i = 0; i < tableauPiles.length; i++) {
            tableauPiles[i] = new TableauPile();
        }
        foundationPiles = new FoundationPile[7];
        for (int i = 0; i < foundationPiles.length; i++) {
            foundationPiles[i] = new FoundationPile();
        }
        stockPile = new StockPile();
        wastePile = new WastePile();

        display = new SolitaireDisplay(this);
        client = new NetworkClient(this);

    }

    // Klient - kreslenie
    public Card getStockCard() {
        return stockPile.getCard();
    }

    // Klient - kreslenie
    public Card getWasteCard() {
        return wastePile.getCard();
    }

    // Klient - kreslenie
    public Card getFoundationCard(int index) {
        if (tableauPiles[index].isEmpty()) return null;
        return tableauPiles[index].getCard();
    }

    // Klient - kreslenie
    // TODO: add getPile method
    public Stack<Card> getPile(int index) {
        return foundationPiles[index].getPile();
    }

    // Client - send message that stock is clicked
    public void stockClicked() {
        System.out.println("stock clicked");
        display.unselect();
        if (!display.isWasteSelected() && !display.isPileSelected()) {

            client.makeMove(0, 0, 1);
            display.unselect();
        }

    }

    public void wasteClicked() {
        System.out.println("waste clicked");
        if (!wastePile.isEmpty()) {
            if (!display.isWasteSelected()) display.selectWaste();
            else display.unselect();
        }
    }

    public void tableauClicked(int index) {
        System.out.println("tableau #" + index + " clicked");
        if (display.isWasteSelected()) {
            client.makeMove(0, 1, index + 2);
            display.unselect();
            // To server
//            if (tableauPiles[index].canAdd(wastePile.getCard())) {
//                tableauPiles[index].pushCard(wastePile.popCard());
//                display.unselect();
//            }
        }
        if (display.isPileSelected()) {
            client.makeMove(0, 6 + display.selectedPile(), index + 2);
            display.unselect();
//            FoundationPile selectedPile = foundationPiles[display.selectedPile()];
//            if (tableauPiles[index].canAdd(selectedPile.getCard())) {
//                Card temp = selectedPile.popCard();
//                tableauPiles[index].pushCard(temp);
//                if (!selectedPile.isEmpty()) selectedPile.getCard().turnUp();
//                display.unselect();
//            }

        }
    }

    public void foundationClicked(int index) {
        System.out.println("foundation #" + index + " clicked");
        if (display.isWasteSelected()) {
            client.makeMove(0, 1, 6 + index);
            display.unselect();
//            Card temp = wastePile.getCard();
//            if (foundationPiles[index].canAdd(temp)) {
//                foundationPiles[index].pushCard(wastePile.popCard());
//                foundationPiles[index].getCard().turnUp();
//            }
        } else if (display.isPileSelected()) {
            client.makeMove(0, 6 + display.selectedPile(), 6 + index);
            display.unselect();
//            int oldPile = display.selectedPile();
//            if (index != oldPile) {
//                Stack<Card> temp = removeFaceUpCards(oldPile);
//                if (foundationPiles[index].canAdd(temp.peek())) {
//                    foundationPiles[index].addCards(temp);
//                    if (!foundationPiles[oldPile].isEmpty()) foundationPiles[oldPile].getCard().turnUp();
//
//                    display.unselect();
//                } else {
//                    foundationPiles[oldPile].addCards(temp);
//                    display.unselect();
//                    display.selectPile(index);
//
//                }
//            } else display.unselect();
        } else {
            display.selectPile(index);
            foundationPiles[index].getCard().turnUp();
        }
    }

    private Stack<Card> removeFaceUpCards(int index) {
        Stack<Card> cards = new Stack<>();
        while (!foundationPiles[index].isEmpty() && foundationPiles[index].getCard().isFaceUp()) {
            cards.push(foundationPiles[index].popCard());
        }
        return cards;
    }

    private void deserialize(UpdatePlayground up) {
        this.stockPile = new StockPile(up.stock);
        this.wastePile = new WastePile(up.stock);
        this.foundationPiles[0] = new FoundationPile(up.foundation0);
        this.foundationPiles[1] = new FoundationPile(up.foundation1);
        this.foundationPiles[2] = new FoundationPile(up.foundation2);
        this.foundationPiles[3] = new FoundationPile(up.foundation3);
        this.foundationPiles[4] = new FoundationPile(up.foundation4);
        this.foundationPiles[5] = new FoundationPile(up.foundation5);
        this.foundationPiles[6] = new FoundationPile(up.foundation6);
        this.tableauPiles[0] = new TableauPile(up.tableau0);
        this.tableauPiles[1] = new TableauPile(up.tableau1);
        this.tableauPiles[2] = new TableauPile(up.tableau2);
        this.tableauPiles[3] = new TableauPile(up.tableau3);
    }

    private BasePile getBasePileByIndex(int index) {
        if (index == 0) {
            return stockPile;
        } else if (index == 1) {
            return wastePile;
        } else if (index > 1 && index < 6) {
            return tableauPiles[index - 2];
        } else {
            return foundationPiles[index - 6];
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
        addCardsToPile(getBasePileByIndex(to), cards);
    }

    @Override
    public void removeCards(int playground, int from, int numberOfCards) {
        System.out.println("removing cards");
        removeCardsFromPile(getBasePileByIndex(from), numberOfCards);
    }

    @Override
    public void playgroundUpdate(int index, UpdatePlayground up) {
        deserialize(up);
        display.repaint();
    }

    @Override
    public void closePlayground(int index) {

    }

    @Override
    public void showHint(String hint) {

    }
}