package solitaire.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import solitaire.GameInstance;
import solitaire.networking.Network.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;

public class SolitaireServer {
    private Server server;
    private HashMap<String, GameInstance> games;

    public SolitaireServer() throws IOException {
        server = new Server() {
            protected Connection newConnection() {
                return new SolitaireConnection();
            }
        };

        Network.register(server);

        server.addListener(new Listener() {
            public void received(Connection c, Object object) {
                // We know all connections for this server are actually ChatConnections.
                SolitaireConnection connection = (SolitaireConnection) c;

                System.out.println("Client connected");

                if (object instanceof RegisterGameRequest) {
                    if (connection.uuid != null) return;

                    GameInstance game = new GameInstance(connection.getID());

                    games.put(game.getUUID(), game);

                    RegisterGameResponse reg = new RegisterGameResponse();
                    reg.uuid = game.getUUID();

                    server.sendToTCP(connection.getID(), reg);

                    return;
                }

                if (object instanceof JoinGameRequest) {
                    if (connection.uuid == null) return;

                    GameInstance game = games.get(connection.uuid);

                    GameStateResponse response = new GameStateResponse();
                    if (game.getPlayerID() == connection.getID()) {
                        response.spectator = false;
                    } else {
                        response.spectator = true;
                        game.getSpectators().add(connection.getID());
                    }

                    server.sendToTCP(connection.getID(), response);

                    return;
                }

                if (object instanceof GameMove) {
                    if (connection.uuid == null) return;

                    GameInstance game = games.get(connection.uuid);
                    if (game.getPlayerID() != game.getPlayerID()) return;

                    GameMove gameMove = (GameMove) object;

                    GameMoveResponse response = new GameMoveResponse();

                    // TODO: check if move is valid
                    if (true) {
                        response.valid = true;
                        server.sendToAllExceptTCP(connection.getID(), gameMove);
                    } else {
                        response.valid = false;
                    }

                    server.sendToTCP(connection.getID(), response);

                    return;
                }

                if (object instanceof AddGame) {
                    if (connection.uuid == null) return;

                    GameInstance game = games.get(connection.uuid);
                    if (game.getPlayerID() != game.getPlayerID()) return;

                    AddGame addGame = (AddGame) object;

                    if (true/*number of games == 4*/) {
                        return;
                    }

                    // TODO: vytvorit hru, pridat do response
                    GameBoard response = new GameBoard();

                    sendToAllPlayers(game, response);

                    return;
                }

                if (object instanceof CloseGame) {
                    if (connection.uuid == null) return;

                    GameInstance game = games.get(connection.uuid);
                    if (game.getPlayerID() != game.getPlayerID()) return;

                    CloseGame closeGame = (CloseGame) object;

                    if (true/*game not exist*/) {
                        return;
                    }

                    // TODO: vytvorit hru, pridat do response

                    sendToAllSpectators(game, closeGame);

                    return;
                }

                if (object instanceof Undo) {
                    if (connection.uuid == null) return;

                    GameInstance game = games.get(connection.uuid);
                    if (game.getPlayerID() != game.getPlayerID()) return;

                    Undo undo = (Undo) object;

                    // TODO: existuje hra? Sprav undo

                    sendToAllSpectators(game, undo);

                    return;
                }

                if (object instanceof Save) {
                    if (connection.uuid == null) return;

                    GameInstance game = games.get(connection.uuid);
                    if (game.getPlayerID() != game.getPlayerID()) return;

                    Save save = (Save) object;

                    // TODO: existuje hra? Uloz

                    return;
                }

            }

            public void disconnected(Connection c) {
                System.out.println("Client disconnected");
                SolitaireConnection connection = (SolitaireConnection) c;

                if (connection.uuid == null) return;
                GameInstance game = games.get(connection.uuid);

                if (game.RemovePlayer(connection.getID()) == 0) {
                    games.remove(connection.uuid);
                }
            }
        });

        server.bind(Network.port);
        server.start();

        // Open a window to provide an easy way to stop the server.
        JFrame frame = new JFrame("Chat SolitaireServer");
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

    private void sendToAllPlayers(GameInstance instance, Object o) {
        server.sendToTCP(instance.getPlayerID(), o);
        sendToAllSpectators(instance, o);
    }

    private void sendToAllSpectators(GameInstance instance, Object o) {
        for (int player:instance.getSpectators()) {
            server.sendToTCP(player, o);
        }
    }

    // Every connection should've unique ID of the game
    static class SolitaireConnection extends Connection {
        public String uuid;
    }

    public static void main (String[] args) throws IOException {
        Log.set(Log.LEVEL_DEBUG);
        new SolitaireServer();
    }


}