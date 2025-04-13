package org.example.java_socket_network_programming.TCP.Controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.BlockingQueue;

public class Notification{

    public static void askUserForFile(String fileName, String fileSize, BlockingQueue<Boolean> decisionQueue) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Incoming File");

        Label info = new Label("File: " + fileName + "\nSize: " + fileSize + " MB");
        VBox layout = getVBox(decisionQueue, stage, info);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 150);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @NotNull
    private static VBox getVBox(BlockingQueue<Boolean> decisionQueue, Stage stage, Label info) {
        Label question = new Label("Do you want to accept this file?");

        Button accept = new Button("Accept");
        Button refuse = new Button("Refuse");

        accept.setOnAction(e -> {
            decisionQueue.offer(true);
            stage.close();
        });

        refuse.setOnAction(e -> {
            decisionQueue.offer(false);
            stage.close();
        });

        HBox buttonRow = new HBox(10, accept, refuse);
        buttonRow.setStyle("-fx-alignment: center;");
        VBox layout = new VBox(10, info, question, buttonRow);
        return layout;
    }
}
