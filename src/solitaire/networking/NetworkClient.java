package solitaire.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import com.esotericsoftware.minlog.Log;
import solitaire.networking.Network.*;

import java.io.IOException;

public class NetworkClient {

    Client client;
    IClientController controller;

    public NetworkClient(IClientController controller) {

        this.controller = controller;

        client = new Client();
        client.start();

        // For consistency, the classes to be sent over the network are
        // registered by the same method for both the client and server.
        Network.register(client);

        client.addListener(new Listener() {
            public void connected (Connection connection) {
                registerGame();
            }

            public void received (Connection connection, Object object) {
                System.out.println("Message from server");
                if (object instanceof RegisterGameResponse) {
                    RegisterGameResponse regResponse = (RegisterGameResponse)object;
                    System.out.println("Connection ID is: " + regResponse.uuid);

                    //client.stop();
                    return;
                }

                if (object instanceof GameMoveResponse) {
                    GameMoveResponse resp = (GameMoveResponse) object;
                    controller.addCards(resp.playground, resp.to, resp.add);
                    controller.removeCards(resp.playground, resp.from, resp.add.length);
                }

                if (object instanceof UpdatePlayground) {
                    UpdatePlayground resp = (UpdatePlayground) object;
                    controller.playgroundUpdate(resp.playground, resp);
                }
            }

            public void disconnected (Connection connection) {

            }
        });

        new Thread("Connect") {
            public void run () {
                try {
                    System.out.println("Connecting...");
                    client.connect(5000, "localhost", Network.port);
                    System.out.println("Connected!");

                    // Server communication after connection can go here, or in Listener#connected().
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        }.start();
    }

    public void registerGame() {
        RegisterGameRequest req = new RegisterGameRequest();
        client.sendTCP(req);
    }

    public void joinGame(String UUID) {
        JoinGameRequest req = new JoinGameRequest();
        req.uuid = UUID;
    }

    public void makeMove(int playground, int from, int to) {

        System.out.println("playground " + playground + " from " + from + " to " + to);
        GameMove req = new GameMove();
        req.playground = playground;
        req.from = from;
        req.to = to;

        client.sendTCP(req);
    }

    public void addGame(int index) {
        AddPlayground req = new AddPlayground();
        req.index = index;

        client.sendTCP(index);
    }

    public void closeGame(int index) {
        ClosePlayground req = new ClosePlayground();
        req.index = index;

        client.sendTCP(req);
    }

    public void undo(int index) {
        UndoRequest req = new UndoRequest();
        req.index = index;

        client.sendTCP(req);
    }

    public void save(int index) {
        SaveRequest req = new SaveRequest();
        req.index = index;

        client.sendTCP(req);
    }

    public void load(int index) {
        LoadRequest req = new LoadRequest();
        req.index = index;

        client.sendTCP(req);
    }

    public void getHint(int index) {
        HintRequest req = new HintRequest();
        req.index = index;

        client.sendTCP(req);
    }

    public void disconnect() {
        client.stop();
    }


}
