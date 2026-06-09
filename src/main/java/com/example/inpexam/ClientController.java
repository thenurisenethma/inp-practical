package com.example.inpexam;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientController {

    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String message="";
    Socket remoteSocket;
    @FXML
    private Button btnDisconnect;

    @FXML
    private Button btnSend;

    @FXML
    private Label lblUserName;

    @FXML
    private TextArea txtArea;

    @FXML
    private TextArea txtAreaInfoPanel;

    @FXML
    private TextField txtMessage;

    @FXML
    void onActionDisconnect(ActionEvent event) {

    }

    @FXML
    void onActionSend(ActionEvent event) throws IOException {
        dataOutputStream = new DataOutputStream(remoteSocket.getOutputStream());
        dataOutputStream.writeUTF(txtMessage.getText());
        dataOutputStream.flush();
    }


    public void initialize() {
        new Thread(() -> {
            try {
                remoteSocket = new Socket("127.0.0.1", 6000);
                dataInputStream = new DataInputStream(remoteSocket.getInputStream());
                while (!message.equals("END")) {
                    message = dataInputStream.readUTF();
                    javafx.application.Platform.runLater(() -> {
                        txtArea.appendText("Bit : " + message + "\n");
                    });
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
