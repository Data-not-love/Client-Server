package org.example.java_socket_network_programming.TCP.Client;

import javafx.application.Application;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.*;

public class ClientGUI extends Application {
    private Label fileLabel;
    private File selectedFile;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Client Side");

        fileLabel = new Label("No File is being chosen");
        Button chooseFileBtn = new Button("Choose File");
        Button sendFileBtn = new Button("Send File");

        HBox buttonRow = new HBox(10);
        buttonRow.getChildren().addAll(chooseFileBtn,sendFileBtn);
        buttonRow.setStyle("-fx-alignment: center;");

        chooseFileBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select a File : ");
            selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                fileLabel.setText("Send this File : " + selectedFile.getName());
            }
        });

        sendFileBtn.setOnAction(event -> {
            if(selectedFile==null){
                fileLabel.setText("No file selected! Select a file please");
            }
            else {
                Client client = new Client();
                fileLabel.setText("File sent successfully: " + selectedFile.getName());
                try {
                    client.sendFile(selectedFile);
                } catch (IOException e) {
                    fileLabel.setText("Failed to send file: " + e.getMessage());
                }
            }
        });

        VBox dragAndDrop = new VBox();
        dragAndDrop.setPrefSize(300, 200);
        dragAndDrop.setStyle("-fx-border-color: gray; -fx-border-width: 2; -fx-alignment: center; -fx-background-color: #f9f9f9;");
        Label labelDragAndDrop = new Label("Drag & drop a file here");
        dragAndDrop.getChildren().add(labelDragAndDrop);

        dragAndDrop.setOnDragOver(event -> {
            if (event.getGestureSource() != dragAndDrop && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        dragAndDrop.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasFiles()) {
                selectedFile = db.getFiles().get(0); // Get the dropped file
                labelDragAndDrop.setText("Dropped: " + selectedFile.getName());
                fileLabel.setText("Send this File : " + selectedFile.getName());
                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });


        VBox layout = new VBox(10, fileLabel, buttonRow,dragAndDrop);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
