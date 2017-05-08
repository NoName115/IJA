package solitaire;

import solitaire.networking.Network.*;
import solitaire.networking.NetworkServer;

public class SolitaireServer {

    private GameInstance[] gi;

    public enum PileType { S, W, T, F }

    NetworkServer networkServer;

    public SolitaireServer(NetworkServer networkServer) {

        this.networkServer = networkServer;

        gi = new GameInstance[4];
        for (int i = 0; i < gi.length; i++) {
            gi[i] = new GameInstance(true);
            gi[i].deal();
        }
    }

    public GameMoveResponse makeMove(GameMove move) {
        System.out.println("playground " + move.playground + " from " + move.from + " to " + move.to);
        gi[move.playground].debug();
        return gi[move.playground].makeMove(move);
    }

    public UpdatePlayground getGameById(int index) {
        UpdatePlayground pg = gi[index].serialize();
        pg.playground = index;
        return pg;
    }

    public GameMoveResponse makeUndo(int index) {
        return gi[index].undo();
    }

    public HintResponse getHint(int index) {
        return gi[index].getHint();
    }
}
