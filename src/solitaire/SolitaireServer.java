package solitaire;

import solitaire.piles.FoundationPile;
import solitaire.piles.StockPile;
import solitaire.piles.TableauPile;
import solitaire.piles.WastePile;

public class SolitaireServer {
    public static void main(String[] args) {
        new SolitaireServer();
    }

    private StockPile stockPile;
    private WastePile wastePile;
    private TableauPile[] tableauPiles;
    private FoundationPile[] foundationPiles;

    public SolitaireServer() {
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

        deal();
    }

    // Server - generacia balickov
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

    // Server
    public void dealCardFromStock() {

        if (!stockPile.isEmpty()) {
            wastePile.pushCard(stockPile.popCard());
        }

    }

    // Server
    public void resetStock() {
        while (!wastePile.isEmpty()) {
            stockPile.pushCard(wastePile.popCard());
        }
    }

    public void stockClicked() {
        if (stockPile.isEmpty()) resetStock();
        else dealCardFromStock();
    }

}
