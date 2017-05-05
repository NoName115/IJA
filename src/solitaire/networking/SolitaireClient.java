package solitaire.networking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import solitaire.networking.Network.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class SolitaireClient {

    Client client;
    IClientController controller;

    public SolitaireClient (IClientController controller) {

        this.controller = controller;

        client = new Client();
        client.start();

        // For consistency, the classes to be sent over the network are
        // registered by the same method for both the client and server.
        Network.register(client);

        client.addListener(new Listener() {
            public void connected (Connection connection) {
            }

            public void received (Connection connection, Object object) {
                System.out.println("Message from server");
                if (object instanceof RegisterGameResponse) {
                    RegisterGameResponse regResponse = (RegisterGameResponse)object;
                    System.out.println("Connection ID is: " + regResponse.uuid);

                    //client.stop();
                    return;
                }
            }

            public void disconnected (Connection connection) {
                EventQueue.invokeLater(new Runnable() {
                    public void run () {
                        // Closing the frame calls the close listener which will stop the client's update thread.
                    }
                });
            }
        });

        // We'll do the connect on a new thread so the ChatFrame can show a progress bar.
        // Connecting to localhost is usually so fast you won't see the progress bar.
        new Thread("Connect") {
            public void run () {
                try {
                    System.out.println("Connecting...");
                    client.connect(5000, "localhost", Network.port);
                    System.out.println("Connected!");


                    RegisterGameRequest regReq = new RegisterGameRequest();
                    client.sendTCP(regReq);

                    Thread.sleep(2000);
                    client.stop();
                    // Server communication after connection can go here, or in Listener#connected().
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.exit(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
