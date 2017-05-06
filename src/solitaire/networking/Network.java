package solitaire.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

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
        kryo.register(JoinGameRequest.class);
        kryo.register(GameStateResponse.class);
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
        public String uuid;
    }

    /**
     * Request for joining game
     */
    static public class JoinGameRequest {
        public String uuid;
    }

    /**
     * Response after joining game along with multiple UpdatePlayground
     */
    static public class GameStateResponse {
        public boolean spectator;
    }

    /**
     * Status of whole playground
     */
    static public class UpdatePlayground {
        // one board - eg. for adding game
    }
    /**
     * Request for moving cards between piles
     */
    static public class GameMove {
        public int index;
        public int from;
        public int to;
        public int numberOfCards;
    }

    /**
     * Response after moving cards - valid move or not
     */
    static public class GameMoveResponse {
        public boolean valid;
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

    }
}
