package solitaire.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import solitaire.Card;
import solitaire.piles.BasePile;
import solitaire.piles.StockPile;

import java.util.Arrays;

/**
 * Class that holds communication messages
 */
public class Network {
    static public final int port = 54555;

    /**
     * This registers objects that are going to be sent over the network.
     *
     * @param endPoint kryonet endpoint
     */
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(RegisterGameRequest.class);
        kryo.register(RegisterGameResponse.class);
        kryo.register(GameMoveResponse.class);
        kryo.register(GameMove.class);
        kryo.register(AddPlayground.class);
        kryo.register(UpdatePlayground.class);
        kryo.register(ClosePlayground.class);
        kryo.register(UndoRequest.class);
        kryo.register(SaveRequest.class);
        kryo.register(LoadRequest.class);
        kryo.register(HintRequest.class);
        kryo.register(HintResponse.class);
        kryo.register(String[].class);
    }

    /**
     * Creating new game
     */
    static public class RegisterGameRequest {
    }

    /**
     * Response for creating new game
     */
    static public class RegisterGameResponse {
        public boolean spectator;
    }

    /**
     * Status of whole playground
     */
    static public class UpdatePlayground {
        public int playground;
        public String[] stock;
        public String[] waste;
        public String[] tableau0;
        public String[] tableau1;
        public String[] tableau2;
        public String[] tableau3;
        public String[] foundation0;
        public String[] foundation1;
        public String[] foundation2;
        public String[] foundation3;
        public String[] foundation4;
        public String[] foundation5;
        public String[] foundation6;
    }
    /**
     * Request for moving solitaire.add between solitaire.piles
     */
    static public class GameMove {
        public int from;
        public int to;
        public int playground;
    }

    /**
     * Response after moving solitaire.add - valid move or not
     */
    static public class GameMoveResponse {
        public int playground;
        public String[] add;
        public int from;
        public int to;

        @Override
        public String toString() {
            return "GameMoveResponse{" +
                    "playground=" + playground +
                    ", add=" + Arrays.toString(add) +
                    ", from=" + from +
                    ", to=" + to +
                    '}';
        }
    }

    /**
     * Request/Response for adding playground
     */
    static public class AddPlayground {
        public int index;
    }

    /**
     * Request/Response for closing playground
     */
    static public class ClosePlayground {
        public int index;
    }

    /**
     * Request for undo operation
     */
    static public class UndoRequest {
        public int index;
    }

    /**
     * Request for saving game
     */
    static public class SaveRequest {
        public int index;
    }

    /**
     * Request for loading game
     */
    static public class LoadRequest {
        public int index;
    }

    /**
     * Request for getting hint
     */
    static public class HintRequest {
        public int index;
    }

    /**
     * Response for getting hint
     */
    static public class HintResponse {
        public int index;
        public String hint;
    }
}
