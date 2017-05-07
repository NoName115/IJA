package solitaire;

import solitaire.networking.Network;
import solitaire.networking.Network.*;
import solitaire.networking.NetworkServer;
import solitaire.piles.*;

public class SolitaireServer {

    private StockPile stockPile; // 0
    private WastePile wastePile; // 1
    private TableauPile[] tableauPiles; // 2, 3, 4, 5
    private FoundationPile[] foundationPiles; // 6, 7, 8, 9, 10, 11, 12

    public enum PileType { S, W, T, F }

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
    private void deal() {
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

    public String status() {
        return "Waste: " + wastePile.size() + "\n" +
                "Stock: " + stockPile.size() + "\n" +
                "F1: " + foundationPiles[0].size() + "\n" +
                "F1: " + foundationPiles[1].size() + "\n" +
                "F1: " + foundationPiles[2].size() + "\n" +
                "F1: " + foundationPiles[3].size() + "\n" +
                "F1: " + foundationPiles[4].size() + "\n" +
                "F1: " + foundationPiles[5].size() + "\n" +
                "F1: " + foundationPiles[6].size();

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

    public GameMoveResponse makeMove(GameMove move) {
        PileType from = getPileTypeByIndex(move.from);
        PileType to = getPileTypeByIndex(move.to);
        GameMoveResponse resp = null;

        if (from == PileType.S && to == PileType.W) {
            resp = PileMover.stockToWaste(stockPile, wastePile);
        } else if (from == PileType.W && to == PileType.F) {
            resp = PileMover.wasteToFoundation(wastePile, foundationPiles[move.to - 6]);
        } else if (from == PileType.W && to == PileType.T) {
            resp = PileMover.wasteToTableau(wastePile, tableauPiles[move.to - 2]);
        } else if (from == PileType.F && to == PileType.F) {
            resp = PileMover.foundationToFoundation(foundationPiles[move.from - 6], foundationPiles[move.to - 6]);
        } else if (from == PileType.F && to == PileType.T) {
            resp = PileMover.foundationToTableau(foundationPiles[move.from - 6], tableauPiles[move.to - 2]);
        } else if (from == PileType.T && to == PileType.F) {
            resp = PileMover.tableauToFoundation(tableauPiles[move.from - 2], foundationPiles[move.to - 6]);
        }

        if (resp == null) {
            System.out.println("response null");
            return null;
        }

        resp.to = move.to;
        resp.from = move.from;

        return resp;
    }

    public PileType getPileTypeByIndex(int index) {
        if (index == 0) {
            return PileType.S;
        } else if (index == 1) {
            return PileType.W;
        } else if (index > 1 && index < 6) {
            return PileType.T;
        } else {
            return PileType.F;
        }
    }

}
