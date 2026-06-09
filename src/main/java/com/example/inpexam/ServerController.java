package com.example.inpexam;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerController {
    ServerSocket serverSocket;
    List<Socket> clients = new ArrayList<>();

    @FXML
    private Button btnSend;

    @FXML
    private TextArea txtArea;

    @FXML
    private TextField txtMessage;

    @FXML
    private TextField txtItem;
    @FXML
    void onActionSend(ActionEvent event) {
        broadcast("Server: " + txtMessage.getText());

    }

    public void initialize() {

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(6000);
                txtArea.appendText("Ready to Bid...\n");
                txtArea.appendText("Vintage Watch LKR 5000\n");
                System.out.println("Auction Server Started - port 6000");
                System.out.println("Item : " + txtItem.getText());
                System.out.println("Vintage Watch");
                System.out.println("Starting Price : LKR 5000");
                while (true) {
                    Socket socket = serverSocket.accept();
                    clients.add(socket);

                    txtArea.appendText("client connected : Amal\n");

                    new Thread(() -> handleClients(socket)).start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    @FXML
    void onActionSetItem(ActionEvent event) {

    }

    private void handleClients(Socket socket) {
        try {
            DataInputStream input = new DataInputStream(socket.getInputStream());

            String message;
            while (true) {
                message = input.readUTF();
                txtArea.appendText("Current Highest BID: " + message + "\n");
                if (message.equals("5000")) {
                    System.out.println("Bid Rejected!");
                }else {
                broadcast(message);}
            }

        } catch (IOException e) {
            txtArea.appendText("Client disconnected\n");
        }
    }

    private void broadcast(String message) {
        for (Socket socket : clients) {
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(message);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
