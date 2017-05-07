package solitaire;

import solitaire.networking.Network;
import solitaire.networking.Network.*;
import solitaire.networking.NetworkServer;
import solitaire.piles.FoundationPile;
import solitaire.piles.StockPile;
import solitaire.piles.TableauPile;
import solitaire.piles.WastePile;

public class SolitaireServer {

    private StockPile stockPile;
    private WastePile wastePile;
    private TableauPile[] tableauPiles;
    private FoundationPile[] foundationPiles;

    NetworkServer networkServer;

    public SolitaireServer(NetworkServer networkServer) {

        this.networkServer = networkServer;

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

    public UpdatePlayground serialize() {
        UpdatePlayground up = new UpdatePlayground();
        up.stock = stockPile.toArray();
        up.waste = wastePile.toArray();
        up.tableau0 = tableauPiles[0].toArray();
        up.tableau1 = tableauPiles[1].toArray();
        up.tableau2 = tableauPiles[2].toArray();
        up.tableau3 = tableauPiles[3].toArray();
        up.foundation0 = foundationPiles[0].toArray();
        up.foundation1 = foundationPiles[1].toArray();
        up.foundation2 = foundationPiles[2].toArray();
        up.foundation3 = foundationPiles[3].toArray();
        up.foundation4 = foundationPiles[4].toArray();
        up.foundation5 = foundationPiles[5].toArray();
        up.foundation6 = foundationPiles[6].toArray();

        return up;
    }

}
