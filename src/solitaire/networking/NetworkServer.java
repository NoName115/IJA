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
                    System.out.println("RegisterGameRequest");

                    RegisterGameResponse reg = new RegisterGameResponse();

                    if (playerID == -1) {
                        reg.spectator = false;
                        playerID = connection.getID();
                    } else {
                        spectators.add(connection.getID());
                    }

                    if (sserver.isActive(0)) server.sendToTCP(connection.getID(), sserver.getGameById(0));
                    if (sserver.isActive(1)) server.sendToTCP(connection.getID(), sserver.getGameById(1));
                    if (sserver.isActive(2)) server.sendToTCP(connection.getID(), sserver.getGameById(2));
                    if (sserver.isActive(3)) server.sendToTCP(connection.getID(), sserver.getGameById(3));

                    return;
                }

                if (object instanceof GameMove) {

                    System.out.println("GameMove");
                    if (playerID != connection.getID()) return;

                    GameMoveResponse resp = sserver.makeMove((GameMove) object);

                    if (resp == null) return;

                    sendToAllPlayers(resp);

                    if (sserver.isWon(resp.playground)) {
                        HintResponse hr = new HintResponse();
                        hr.hint = "You won";
                        hr.index = resp.playground;
                        server.sendToTCP(connection.getID(), hr);
                    }

                    return;
                }

                if (object instanceof HintRequest) {
                    System.out.println("HintRequest");
                    HintRequest req = (HintRequest) object;
                    HintResponse resp = sserver.getHint(req.index);

                    server.sendToTCP(connection.getID(), resp);
                }

                if (object instanceof UndoRequest) {

                    System.out.println("UndoRequest");
                    if (playerID != connection.getID()) return;

                    UndoRequest undoRequest = (UndoRequest) object;

                    GameMoveResponse resp = sserver.makeUndo(undoRequest.index);

                    System.out.println(resp);
                    if (resp == null) {
                        return;
                    }

                    sendToAllPlayers(resp);

                    return;
                }

                if (object instanceof SaveRequest) {

                    SaveRequest saveRequest = (SaveRequest) object;
                    sserver.save(saveRequest.index);

                    return;
                }

                if (object instanceof LoadRequest) {
                    LoadRequest loadRequest = (LoadRequest) object;
                    if (sserver.load(loadRequest.index)) {
                        sendToAllPlayers(sserver.getGameById(loadRequest.index));
                    }

                }

                if (object instanceof AddPlayground) {
                    if (playerID != connection.getID()) return;

                    AddPlayground req = (AddPlayground) object;
                    System.out.println("AddPlayground " + req.index);
                    sserver.activate(req.index);
                    sendToAllPlayers(sserver.getGameById(req.index));
                }

                if (object instanceof ClosePlayground) {
                    if (playerID != connection.getID()) return;

                    ClosePlayground req = (ClosePlayground) object;
                    sserver.deactivate(req.index);
                    sendToAllSpectators(req);
                }

            }

            public void disconnected(Connection c) {
                System.out.println("Client disconnected");
                SolitaireConnection connection = (SolitaireConnection) c;

                if (RemovePlayer(connection.getID()) == 0) {
                    resetGame();
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

    /**
     * Method to reset game
     */
    private void resetGame() {
        sserver = new SolitaireServer(this);
    }

    /**
     * Method to send respnse to all players
     * @param o response object
     */
    private void sendToAllPlayers(Object o) {
        server.sendToTCP(playerID, o);
        sendToAllSpectators(o);
    }

    /**
     * Method to send response to all spectators
     *
     * @param o response object
     */
    private void sendToAllSpectators(Object o) {
        for (int player:spectators) {
            server.sendToTCP(player, o);
        }
    }

    // Every connection should've unique ID of the game
    static class SolitaireConnection extends Connection {
    }

    /**
     * Function to remove player from game
     *
     * @param id ID of the player
     * @return number of players left in the game
     */
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