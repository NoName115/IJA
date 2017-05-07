package solitaire;

import solitaire.piles.FoundationPile;
import solitaire.piles.TableauPile;
import solitaire.piles.StockPile;
import solitaire.piles.WastePile;

import java.util.*;

public class Solitaire {
    public static void main(String[] args) {
        new Solitaire();
    }

    private StockPile stockPile;
    private WastePile wastePile;
    private TableauPile[] tableauPiles;
    private FoundationPile[] foundationPiles;

    private SolitaireDisplay display;

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
        deal();
    }

    public Card getStockCard() {
        return stockPile.getCard();
    }

    public Card getWasteCard() {
        return wastePile.getCard();
    }

    public Card getFoundationCard(int index) {
        if (tableauPiles[index].isEmpty()) return null;
        return tableauPiles[index].getCard();
    }

    // TODO: remove getPile method
    public Stack<Card> getPile(int index) {
        return foundationPiles[index].getPile();
    }

    // TODO: randomize
    public void deal() {
        for (int i = 0; i < foundationPiles.length; i++) {
            int counter = 0;
            while (counter != i + 1) {
                Card temp = stockPile.popCard();
                foundationPiles[i].pushCard(temp);
                counter++;
            }
            foundationPiles[i].getCard().turnUp();
        }
    }

    public void dealCardFromStock() {

        if (!stockPile.isEmpty()) {
            wastePile.pushCard(stockPile.popCard());
        }

    }

    public void resetStock() {
        while (!wastePile.isEmpty()) {
            stockPile.pushCard(wastePile.popCard());
        }
    }

    public void stockClicked() {
        System.out.println("stock clicked");
        display.unselect();
        if (!display.isWasteSelected() && !display.isPileSelected()) {
            if (stockPile.isEmpty()) resetStock();
            else dealCardFromStock();
        }

    }

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
}