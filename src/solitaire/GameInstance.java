package solitaire;

import solitaire.networking.Network;
import solitaire.piles.*;

public class GameInstance {

    private StockPile stockPile; // 0
    private WastePile wastePile; // 1
    private TableauPile[] tableauPiles; // 2, 3, 4, 5
    private FoundationPile[] foundationPiles; // 6, 7, 8, 9, 10, 11, 12

    public GameInstance() {
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
    }

    public void deserialize(Network.UpdatePlayground up) {
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

    public Network.UpdatePlayground serialize() {
        Network.UpdatePlayground up = new Network.UpdatePlayground();
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

    public Network.GameMoveResponse makeMove(Network.GameMove move) {

        System.out.println("From: " + move.from +  ", To: " + move.to);
        SolitaireServer.PileType from = getPileTypeByIndex(move.from);
        SolitaireServer.PileType to = getPileTypeByIndex(move.to);
        Network.GameMoveResponse resp = null;

        if (from == SolitaireServer.PileType.S && to == SolitaireServer.PileType.W) {
            resp = PileMover.stockToWaste(stockPile, wastePile);
        } else if (from == SolitaireServer.PileType.W && to == SolitaireServer.PileType.F) {
            resp = PileMover.wasteToFoundation(wastePile, foundationPiles[move.to - 6]);
        } else if (from == SolitaireServer.PileType.W && to == SolitaireServer.PileType.T) {
            resp = PileMover.wasteToTableau(wastePile, tableauPiles[move.to - 2]);
        } else if (from == SolitaireServer.PileType.F && to == SolitaireServer.PileType.F) {
            resp = PileMover.foundationToFoundation(foundationPiles[move.from - 6], foundationPiles[move.to - 6]);
        } else if (from == SolitaireServer.PileType.F && to == SolitaireServer.PileType.T) {
            resp = PileMover.foundationToTableau(foundationPiles[move.from - 6], tableauPiles[move.to - 2]);
        } else if (from == SolitaireServer.PileType.T && to == SolitaireServer.PileType.F) {
            resp = PileMover.tableauToFoundation(tableauPiles[move.from - 2], foundationPiles[move.to - 6]);
        }

        if (resp == null) {
            System.out.println("response null");
            return null;
        }

        resp.to = move.to;
        resp.from = move.from;
        resp.playground = move.playground;

        return resp;
    }

    public SolitaireServer.PileType getPileTypeByIndex(int index) {
        if (index == 0) {
            return SolitaireServer.PileType.S;
        } else if (index == 1) {
            return SolitaireServer.PileType.W;
        } else if (index > 1 && index < 6) {
            return SolitaireServer.PileType.T;
        } else {
            return SolitaireServer.PileType.F;
        }
    }

    public StockPile s() {
        return stockPile;
    }

    public WastePile w() {
        return wastePile;
    }

    public FoundationPile f(int n) {
        return foundationPiles[n];
    }

    public TableauPile t(int n) {
        return tableauPiles[n];
    }

}
