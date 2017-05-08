package solitaire.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import solitaire.GameInstance;
import solitaire.SolitaireServer;
import solitaire.networking.Network.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NetworkServer {
    private Server server;
    private SolitaireServer sserver;
    private int playerID;
    ArrayList<Integer> spectators;


    public NetworkServer() throws IOException {
        server = new Server() {
            protected Connection newConnection() {
                return new SolitaireConnection();
            }
        };

        Network.register(server);

        sserver = new SolitaireServer(this);
        this.playerID = -1;
        spectators = new ArrayList<>();

        server.addListener(new Listener() {
            public void received(Connection c, Object object) {
                SolitaireConnection connection = (SolitaireConnection) c;

                if (object instanceof RegisterGameRequest) {
                    if (connection.uuid != null) return;
                    System.out.println("RegisterGameRequest");


                    RegisterGameResponse reg = new RegisterGameResponse();

                    server.sendToTCP(connection.getID(), sserver.getGameById(0));
                    server.sendToTCP(connection.getID(), sserver.getGameById(1));
                    server.sendToTCP(connection.getID(), sserver.getGameById(2));
                    server.sendToTCP(connection.getID(), sserver.getGameById(3));

                    return;
                }
//
//                if (object instanceof JoinGameRequest) {
//
//                    JoinGameRequest joinGameRequest = (JoinGameRequest) object;
//                    GameInstance game = games.get(joinGameRequest.uuid);
//
//                    connection.uuid = joinGameRequest.uuid;
//
//                    GameStateResponse response = new GameStateResponse();
//                    if (game.getPlayerID() == connection.getID()) {
//                        response.spectator = false;
//                    } else {
//                        response.spectator = true;
//                        game.getSpectators().add(connection.getID());
//                    }
//
//                    server.sendToTCP(connection.getID(), response);
//
//                    return;
//                }

                if (object instanceof GameMove) {
                    //if (connection.uuid == null) return;

                    System.out.println("GameMove");
//                    GameInstance game = games.get(connection.uuid);
//                    if (game.getPlayerID() != game.getPlayerID()) return;

                    GameMoveResponse resp = sserver.makeMove((GameMove) object);

                    if (resp == null) return;

                    System.out.println("Sending GameMoveResponse");
                    for (String r : resp.add) {
                        System.out.println(r);
                    }
                    server.sendToTCP(connection.getID(), resp);

                    return;
                }

//                if (object instanceof AddPlayground) {
//                    if (connection.uuid == null) return;
//
//                    GameInstance game = games.get(connection.uuid);
//                    if (game.getPlayerID() != game.getPlayerID()) return;
//
//                    AddPlayground addPlayground = (AddPlayground) object;
//
//                    if (true/*number of games == 4*/) {
//                        return;
//                    }
//
//                    // TODO: vytvorit hru, pridat do response
//                    Network.UpdatePlayground response = new Network.UpdatePlayground();
//
//                    sendToAllPlayers(game, response);
//
//                    return;
//                }
//
//                if (object instanceof ClosePlayground) {
//                    if (connection.uuid == null) return;
//
//                    GameInstance game = games.get(connection.uuid);
//                    if (game.getPlayerID() != game.getPlayerID()) return;
//
//                    ClosePlayground closePlayground = (ClosePlayground) object;
//
//                    if (true/*game not exist*/) {
//                        return;
//                    }
//
//                    // TODO: vytvorit hru, pridat do response
//
//                    sendToAllSpectators(game, closePlayground);
//
//                    return;
//                }
//
//                if (object instanceof UndoRequest) {
//                    if (connection.uuid == null) return;
//
//                    GameInstance game = games.get(connection.uuid);
//                    if (game.getPlayerID() != game.getPlayerID()) return;
//
//                    UndoRequest undoRequest = (UndoRequest) object;
//
//                    // TODO: existuje hra? Sprav undoRequest
//
//                    sendToAllSpectators(game, undoRequest);
//
//                    return;
//                }
//
//                if (object instanceof SaveRequest) {
//                    if (connection.uuid == null) return;
//
//                    GameInstance game = games.get(connection.uuid);
//                    if (game.getPlayerID() != game.getPlayerID()) return;
//
//                    SaveRequest saveRequest = (SaveRequest) object;
//
//                    // TODO: existuje hra? Uloz
//
//                    return;
//                }

            }

            public void disconnected(Connection c) {
                System.out.println("Client disconnected");
                SolitaireConnection connection = (SolitaireConnection) c;

                if (connection.uuid == null) return;

                if (RemovePlayer(connection.getID()) == 0) {
                    playerID = -1;
                }
            }
        });

        server.bind(Network.port);
        server.start();

        // Open a window to provide an easy way to stop the server.
        JFrame frame = new JFrame("Chat NetworkServer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent evt) {
                server.stop();
            }
        });
        frame.getContentPane().add(new JLabel("Close to stop the chat server."));
        frame.setSize(320, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void sendToAllPlayers(Object o) {
        server.sendToTCP(playerID, o);
        sendToAllSpectators(o);
    }

    private void sendToAllSpectators(Object o) {
        for (int player:spectators) {
            server.sendToTCP(player, o);
        }
    }

    // Every connection should've unique ID of the game
    static class SolitaireConnection extends Connection {
        public String uuid;
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

    public static void main (String[] args) throws IOException {
        Log.set(Log.LEVEL_DEBUG);
        new NetworkServer();
    }


}