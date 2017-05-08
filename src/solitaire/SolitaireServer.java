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
            gi[i] = new GameInstance();
            gi[i].deal();
        }
    }

    public GameMoveResponse makeMove(GameMove move) {
        return gi[move.playground].makeMove(move);
    }

    public UpdatePlayground getGameById(int index) {
        return gi[index].serialize();
    }



}
