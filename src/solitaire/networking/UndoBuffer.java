package solitaire.networking;

import java.util.LinkedList;

public class UndoBuffer {
    private LinkedList<Network.GameMoveResponse> buffer;
    private int maxSize;

    public UndoBuffer(int n) {
        this.maxSize = n;
        buffer = new LinkedList<>();
    }

    public void addMove(Network.GameMoveResponse move) {
        buffer.addLast(move);
        if (buffer.size() > maxSize) {
            buffer.removeFirst();
        }
    }

    public Network.GameMoveResponse getMove() {
        if (buffer.size() == 0) return null;
        Network.GameMoveResponse resp = buffer.removeLast();
        int t = resp.to;
        resp.to = resp.from;
        resp.from = t;
        return resp;
    }
}
