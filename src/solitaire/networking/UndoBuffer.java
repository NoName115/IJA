package solitaire.networking;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
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
        return buffer.removeLast();
    }
}
