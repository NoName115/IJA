package solitaire;

import java.util.ArrayList;
import java.util.UUID;

public class GameInstance {
    private int playerID;
    private String uuid;
    private ArrayList<Integer> spectators;
    // TODO: List of games

    public GameInstance(int playerID) {
        this.playerID = playerID;
        this.uuid = UUID.randomUUID().toString();
    }

    public int getPlayerID() {
        return playerID;
    }

    public ArrayList<Integer> getSpectators() {
        return spectators;
    }

    public String getUUID() {
        return uuid;
    }

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
