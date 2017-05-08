package solitaire;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.JsonWriter;
import solitaire.networking.Network.*;
import solitaire.networking.NetworkServer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SolitaireServer {

    private GameInstance[] gi;
    private boolean[] act;

    public enum PileType {S, W, T, F}

    NetworkServer networkServer;

    public SolitaireServer(NetworkServer networkServer) {

        this.networkServer = networkServer;

        gi = new GameInstance[4];
        for (int i = 0; i < gi.length; i++) {
            gi[i] = new GameInstance(true);
            gi[i].deal();
        }

        act = new boolean[4];
        act[0] = true;
        for (int i = 1; i < gi.length; i++) {
            act[i] = false;
        }
    }

    public GameMoveResponse makeMove(GameMove move) {
        System.out.println("playground " + move.playground + " from " + move.from + " to " + move.to);
        gi[move.playground].debug();
        return gi[move.playground].makeMove(move);
    }

    public UpdatePlayground getGameById(int index) {
        if (!act[index]) {
            return null;
        }
        UpdatePlayground pg = gi[index].serialize();
        pg.playground = index;
        return pg;
    }

    public void activate(int index) {
        act[index] = true;
        gi[index] = new GameInstance(true);
    }

    public void deactivate(int index) {
        act[index] = false;
    }

    public GameMoveResponse makeUndo(int index) {
        return gi[index].undo();
    }

    public HintResponse getHint(int index) {
        return gi[index].getHint();
    }

    public void save(int index) {
        Json json = new Json();
        json.toJson(gi[index].serialize(), new File("save" + index + ".json"));
    }

    public boolean load(int index) {
        File f = new File("save" + index + ".json");
        if (f.exists() && !f.isDirectory()) {
            Json json = new Json();
            UpdatePlayground pg = json.fromJson(UpdatePlayground.class, f);
            gi[index].deserialize(pg);

            return true;
        }
        return false;
    }
}
