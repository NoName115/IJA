package solitaire.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    static public final int port = 54555;

    // This registers objects that are going to be sent over the network.
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(RegisterGameRequest.class);
        kryo.register(RegisterGameResponse.class);
        kryo.register(JoinGameRequest.class);
        kryo.register(GameStateResponse.class);
        kryo.register(GameMoveResponse.class);
        kryo.register(GameMove.class);
        kryo.register(AddGame.class);
        kryo.register(UpdatePlayground.class);
        kryo.register(CloseGame.class);
        kryo.register(Undo.class);
        kryo.register(Save.class);

    }

    static public class RegisterGameRequest {
    }

    static public class RegisterGameResponse {
        public String uuid;
    }

    static public class JoinGameRequest {
        public int uuid;
    }

    static public class GameStateResponse {
        public boolean spectator;
        // state of whole game - list of 4 boards
    }

    static public class GameBoard {
        // one board - eg. for adding game
    }

    static public class GameMove {
        public int index;
        // move to do
    }

    static public class GameMoveResponse {
        public boolean valid;
    }

    static public class AddGame {
        public int index;
    }

    static public class UpdatePlayground {
        public int index;
    }

    static public class CloseGame {
        public int index;
    }

    static public class Undo {
        public int index;
    }

    static public class Save {
        public int index;
    }
}
