package solitaire.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import solitaire.SolitaireServer;
import solitaire.networking.Network.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class NetworkServer {
    private Server server;
    private NetworkServer me;
    HashMap<String, SolitaireServer> games;

    public NetworkServer() throws IOException {
        server = new Server() {
            protected Connection newConnection() {
                return new SolitaireConnection();
            }
        };
        me = this;
        Network.register(server);

        games = new HashMap<>();

        server.addListener(new Listener() {
            public void received(Connection c, Object object) {
                SolitaireConnection connection = (SolitaireConnection) c;

                if (object instanceof RegisterGameRequest) {

                    System.out.println("Reg");
                    RegisterGameRequest req = (RegisterGameRequest) object;
                    if (req.uuid == null && connection.uuid == null) {
                        connection.uuid = UUID.randomUUID().toString();
                        games.put(connection.uuid, new SolitaireServer(me, connection.uuid));
                    }
                    else if (req.uuid != null && connection.uuid == null) {
                        if (games.containsKey(req.uuid)) {
                            connection.uuid = req.uuid;
                        } else {
                            connection.uuid = UUID.randomUUID().toString();
                            games.put(connection.uuid, new SolitaireServer(me, connection.uuid));
                        }
                    }

                    SolitaireServer game = games.get(connection.uuid);

                    RegisterGameResponse reg = new RegisterGameResponse();

                    if (game.getPlayerID() == -1) {
                        reg.uuid = connection.uuid;
                        game.setPlayerID(connection.getID());
                        HintResponse resp = new HintResponse();
                        resp.hint = connection.uuid;
                        server.sendToTCP(connection.getID(), resp);
                    } else {
                        game.getSpectators().add(connection.getID());
                    }

                    if (game.isActive(0)) server.sendToTCP(connection.getID(), game.getGameById(0));
                    if (game.isActive(1)) server.sendToTCP(connection.getID(), game.getGameById(1));
                    if (game.isActive(2)) server.sendToTCP(connection.getID(), game.getGameById(2));
                    if (game.isActive(3)) server.sendToTCP(connection.getID(), game.getGameById(3));

                    return;
                }

                if (object instanceof GameMove) {

                    if (connection.uuid == null) return;
                    SolitaireServer game = games.get(connection.uuid);

                    System.out.println("GameMove");
                    if (game.getPlayerID() != connection.getID()) return;

                    GameMoveResponse resp = game.makeMove((GameMove) object);

                    if (resp == null) return;

                    sendToAllPlayers(resp, game);

                    if (game.isWon(resp.playground)) {
                        HintResponse hr = new HintResponse();
                        hr.hint = "You won";
                        hr.index = resp.playground;
                        server.sendToTCP(connection.getID(), hr);
                    }

                    return;
                }

                if (object instanceof HintRequest) {
                    if (connection.uuid == null) return;
                    SolitaireServer game = games.get(connection.uuid);

                    System.out.println("HintRequest");
                    HintRequest req = (HintRequest) object;
                    HintResponse resp = game.getHint(req.index);

                    server.sendToTCP(connection.getID(), resp);
                }

                if (object instanceof UndoRequest) {

                    if (connection.uuid == null) return;
                    SolitaireServer game = games.get(connection.uuid);
                    System.out.println("UndoRequest");
                    if (game.getPlayerID() != connection.getID()) return;

                    UndoRequest undoRequest = (UndoRequest) object;

                    GameMoveResponse resp = game.makeUndo(undoRequest.index);

                    System.out.println(resp);
                    if (resp == null) {
                        return;
                    }

                    sendToAllPlayers(resp, game);

                    return;
                }

                if (object instanceof SaveRequest) {

                    if (connection.uuid == null) return;
                    SolitaireServer game = games.get(connection.uuid);
                    SaveRequest saveRequest = (SaveRequest) object;
                    game.save(saveRequest.index);

                    return;
                }

                if (object instanceof LoadRequest) {
                    if (connection.uuid == null) return;
                    SolitaireServer game = games.get(connection.uuid);
                    LoadRequest loadRequest = (LoadRequest) object;
                    if (game.load(loadRequest.index)) {
                        sendToAllPlayers(game.getGameById(loadRequest.index), game);
                    }

                }

                if (object instanceof AddPlayground) {
                    if (connection.uuid == null) return;
                    SolitaireServer game = games.get(connection.uuid);
                    if (game.getPlayerID() != connection.getID()) return;

                    AddPlayground req = (AddPlayground) object;
                    System.out.println("AddPlayground " + req.index);
                    game.activate(req.index);
                    sendToAllPlayers(game.getGameById(req.index), game);
                }

                if (object instanceof ClosePlayground) {
                    if (connection.uuid == null) return;
                    SolitaireServer game = games.get(connection.uuid);
                    if (game.getPlayerID() != connection.getID()) return;

                    ClosePlayground req = (ClosePlayground) object;
                    game.deactivate(req.index);
                    sendToAllPlayers(req, game);
                }

            }

            public void disconnected(Connection c) {
                if (((SolitaireConnection) c).uuid == null) return;
                System.out.println("Client disconnected");
                SolitaireConnection connection = (SolitaireConnection) c;

                SolitaireServer game = games.get(((SolitaireConnection) c).uuid);
                if (game.RemovePlayer(connection.getID()) == 0) {
                    games.remove(((SolitaireConnection) c).uuid);
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

    /**
     * Method to send respnse to all players
     *
     * @param o response object
     */
    private void sendToAllPlayers(Object o, SolitaireServer game) {
        server.sendToTCP(game.getPlayerID(), o);
        sendToAllSpectators(o, game);
    }

    /**
     * Method to send response to all spectators
     *
     * @param o response object
     */
    private void sendToAllSpectators(Object o, SolitaireServer game) {
        for (int player : game.getSpectators()) {
            server.sendToTCP(player, o);
        }
    }

    // Every connection should've unique ID of the game
    static class SolitaireConnection extends Connection {
        String uuid;
    }

    public static void main(String[] args) throws IOException {
        Log.set(Log.LEVEL_DEBUG);
        new NetworkServer();
    }


}