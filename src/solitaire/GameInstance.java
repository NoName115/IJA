package solitaire;

import java.util.ArrayList;

/**
 * Created by jakub on 5/7/17.
 */
public class GameInstance {
    private String UUID;
    private int playerID;
    ArrayList<Integer> spectators;

    public GameInstance(int playerID) {
        this.playerID = playerID;
        this.UUID = java.util.UUID.randomUUID().toString();
        spectators = new ArrayList<>();
    }

    public String getUUID() {
        return UUID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public ArrayList<Integer> getSpectators() {
        return spectators;
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
