package solitaire;

import solitaire.networking.Network;
import solitaire.networking.UndoBuffer;
import solitaire.piles.*;

public class GameInstance {

    private StockPile stockPile; // 0
    private WastePile wastePile; // 1
    private TableauPile[] tableauPiles; // 2, 3, 4, 5
    private FoundationPile[] foundationPiles; // 6, 7, 8, 9, 10, 11, 12

    private UndoBuffer undoBuffer;

    public GameInstance(boolean server) {
        tableauPiles = new TableauPile[4];
        for (int i = 0; i < tableauPiles.length; i++) {
            tableauPiles[i] = new TableauPile();
        }
        foundationPiles = new FoundationPile[7];
        for (int i = 0; i < foundationPiles.length; i++) {
            foundationPiles[i] = new FoundationPile();
        }
        stockPile = new StockPile(server);
        wastePile = new WastePile();
        undoBuffer = new UndoBuffer(5);
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

        undoBuffer.addMove(resp);

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

    public void debug() {
        System.out.println(stockPile.getCard() + " " + wastePile.getCard() + " ");
        for (TableauPile pile : tableauPiles) {
            System.out.print(pile.getCard() + " ");
        }
        System.out.println();
        for (FoundationPile pile : foundationPiles) {
            System.out.print(pile.getCard() + " ");
        }
        System.out.println();
    }

    public Network.GameMoveResponse undo() {
        Network.GameMoveResponse resp = undoBuffer.getMove();
        if (resp == null) return null;

        addCardsToPile(resp.to, resp.add);
        removeCardsFromPile(resp.from, resp.add.length);

        return resp;
    }

    public Network.HintResponse getHint() {
        Network.HintResponse resp = new Network.HintResponse();

        for (FoundationPile fp : this.foundationPiles)
        {
            Card tempCard = fp.getFirstFaceUpCard();
            if (tempCard == null)
            {
                continue;
            }

            // Foundation pile s Tableau pile
            for (TableauPile tp : this.tableauPiles)
            {
                if (tp.canAdd(tempCard))
                {
                    resp.hint = "Move " + tempCard.toStringRobo() + " to Tableau pile";
                    return resp;
                }
            }

            tempCard = fp.getLastFaceUpCard();
            if (tempCard == null)
            {
                continue;
            }

            // Foundation pile s Foundation pile
            for (FoundationPile fpInter : this.foundationPiles)
            {
                if (fpInter.canAdd(tempCard))
                {
                    Card topCard = fpInter.getFirstFaceUpCard();
                    if (topCard != null)
                    {
                        resp.hint = "Move " + tempCard.toStringRobo() + " at " + topCard.toStringRobo();
                    }
                    else
                    {
                        resp.hint = "Move " + tempCard.toStringRobo() + " to Foundation pile";
                    }

                    return resp;
                }
            }
        }

        // Top Waste pile Card
        Card tempCard = this.wastePile.getFirstCard();
        if (tempCard != null)
        {
            for (TableauPile tp : this.tableauPiles)
            {
                if (tp.canAdd(tempCard))
                {
                    resp.hint = "Move " + tempCard.toStringRobo() + " to Tableau pile";
                    return resp;
                }
            }

            for (FoundationPile fp : this.foundationPiles)
            {
                if (fp.canAdd(tempCard))
                {
                    Card topCard = fp.getFirstFaceUpCard();
                    if (topCard != null)
                    {
                        resp.hint = "Move " + tempCard.toStringRobo() + " at " + topCard.toStringRobo();
                    }
                    else
                    {
                        resp.hint = "Move " + tempCard.toStringRobo() + " to FoundationPile";
                    }

                    return resp;
                }
            }
        }

        // Waste pile
        for (Card interCard : this.wastePile.getPile())
        {
            for (TableauPile tp : this.tableauPiles)
            {
                if (tp.canAdd(interCard))
                {
                    resp.hint = "Draw card";
                    return resp;
                }
            }

            for (FoundationPile fp : this.foundationPiles)
            {
                if (fp.canAdd(interCard))
                {
                    resp.hint = "Draw card";
                    return resp;
                }
            }
        }

        // Stock pile
        for (Card interCard : this.stockPile.getPile())
        {
            for (TableauPile tp : this.tableauPiles)
            {
                if (tp.canAdd(interCard))
                {
                    resp.hint = "Draw card";
                    return resp;
                }
            }

            for (FoundationPile fp : this.foundationPiles)
            {
                if (fp.canAdd(interCard))
                {
                    resp.hint = "Draw card";
                    return resp;
                }
            }
        }

        resp.hint = "No hint available";
        return resp;
    }

    public void removeCardsFromPile(int from, int numberOfCards) {
        BasePile pile = getBasePileByIndex(from);
        for (int i = 0; i < numberOfCards; i++) {
            pile.popCard();
        }
    }

    public void addCardsToPile(int to, String[] cards) {
        BasePile pile = getBasePileByIndex(to);
        for (int i = cards.length - 1; i >= 0; i--) {
            pile.pushCard(new Card(cards[i]));
        }
    }


    private BasePile getBasePileByIndex(int index) {
        if (index == 0) {
            return s();
        } else if (index == 1) {
            return w();
        } else if (index > 1 && index < 6) {
            return t(index - 2);
        } else {
            return f(index - 6);
        }
    }

    public boolean isWon() {
        for (TableauPile t : tableauPiles) {
            if (t.size() != 13) {
                return false;
            }
        }

        return true;
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
