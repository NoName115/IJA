package solitaire;

import com.esotericsoftware.jsonbeans.Json;
import solitaire.networking.Network.*;
import solitaire.networking.NetworkServer;

import java.io.File;
import java.util.ArrayList;

public class SolitaireServer {

    private GameInstance[] gi;
    private boolean[] act;

    public int getPlayerID() {
        return playerID;
    }

    public ArrayList<Integer> getSpectators() {
        return spectators;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    private int playerID;
    private String UUID;
    private ArrayList<Integer> spectators;

    public enum PileType {S, W, T, F}

    NetworkServer networkServer;

    public SolitaireServer(NetworkServer networkServer, String UUID) {

        this.networkServer = networkServer;

        playerID = -1;
        spectators = new ArrayList<>();
        this.UUID = UUID;

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
        UpdatePlayground pg = gi[index].serialize();
        pg.playground = index;
        return pg;
    }

    public boolean isActive(int index) {
        return act[index];
    }

    public void activate(int index) {
        act[index] = true;
        gi[index] = new GameInstance(true);
        gi[index].deal();
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

    public boolean isWon(int index) {
        return gi[index].isWon();
    }

    /**
     * Function to remove player from game
     *
     * @param id ID of the player
     * @return number of players left in the game
     */
    public int RemovePlayer(int id) {
        if (playerID == id) {
            playerID = 0;
        }

        spectators.remove(new Integer(id));

        if (playerID == 0) {
            return spectators.size();
        }
        return spectators.size() + 1;
    }
}
