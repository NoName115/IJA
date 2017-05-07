package solitaire;

import solitaire.networking.IClientController;
import solitaire.networking.SolitaireClient;
import solitaire.piles.FoundationPile;
import solitaire.piles.TableauPile;
import solitaire.piles.StockPile;
import solitaire.piles.WastePile;

import java.util.*;

public class Solitaire implements IClientController {
    public static void main(String[] args) {
        new Solitaire();
    }

    private StockPile stockPile; // 0
    private WastePile wastePile; // 1
    private TableauPile[] tableauPiles; // 2, 3, 4, 5
    private FoundationPile[] foundationPiles; // 6, 7, 8, 9, 10, 11, 12

    private SolitaireDisplay display;
    private SolitaireClient client;

    public Solitaire() {
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
        client = new SolitaireClient(this);
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

            client.makeMove(0, 0, -1, -1);
        }

    }

    //
    public void wasteClicked() {
        System.out.println("waste clicked");
        if (!wastePile.isEmpty()) {
            if (!display.isWasteSelected()) display.selectWaste();
            else display.unselect();
        }
    }

    public void foundationClicked(int index) {
        System.out.println("foundation #" + index + " clicked");
        if (display.isWasteSelected()) {
            if (tableauPiles[index].canAdd(wastePile.getCard())) {
                tableauPiles[index].pushCard(wastePile.popCard());
                display.unselect();
            }
        }
        if (display.isPileSelected()) {
            FoundationPile selectedPile = foundationPiles[display.selectedPile()];
            if (tableauPiles[index].canAdd(selectedPile.getCard())) {
                Card temp = selectedPile.popCard();
                tableauPiles[index].pushCard(temp);
                if (!selectedPile.isEmpty()) selectedPile.getCard().turnUp();
                display.unselect();
            }

        }
    }

    public void pileClicked(int index) {
        System.out.println("pile #" + index + " clicked");
        if (display.isWasteSelected()) {
            Card temp = wastePile.getCard();
            if (foundationPiles[index].canAdd(temp)) {
                foundationPiles[index].pushCard(wastePile.popCard());
                foundationPiles[index].getCard().turnUp();
            }
            display.unselect();
            display.selectPile(index);
        } else if (display.isPileSelected()) {
            int oldPile = display.selectedPile();
            if (index != oldPile) {
                Stack<Card> temp = removeFaceUpCards(oldPile);
                if (foundationPiles[index].canAdd(temp.peek())) {
                    foundationPiles[index].addCards(temp);
                    if (!foundationPiles[oldPile].isEmpty()) foundationPiles[oldPile].getCard().turnUp();

                    display.unselect();
                } else {
                    foundationPiles[oldPile].addCards(temp);
                    display.unselect();
                    display.selectPile(index);

                }
            } else display.unselect();
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

    @Override
    public void moveCard(int playground, int from, int to, int numberOfCards) {

    }

    @Override
    public void addCards(int playground, int to, String[] cards) {
        System.out.println("adding cards");
        if (to == 1) {
            for (int i = 0; i < cards.length; i++) {
                wastePile.pushCard(new Card(cards[i]));
            }
        }
    }

    @Override
    public void lastOperationStatus(boolean valid) {

    }

    @Override
    public void playgroundUpdate(int index) {

    }

    @Override
    public void closePlayground(int index) {

    }

    @Override
    public void showHint(String hint) {

    }
}