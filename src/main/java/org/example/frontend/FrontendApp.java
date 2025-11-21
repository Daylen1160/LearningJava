package org.example.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FrontendApp extends Application {

    // Built-in Java tool to talk to the Internet
    private final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // 1. UI Elements
        TextField inputField = new TextField();
        inputField.setPromptText("Enter item name...");

        Button saveButton = new Button("Save to DB");
        Button refreshButton = new Button("View All");
        TextArea displayArea = new TextArea();

        // 2. Button Logic - SAVE
        saveButton.setOnAction(e -> {
            String name = inputField.getText();
            String json = "{\"name\":\"" + name + "\"}"; // Manual JSON for simplicity

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/add"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            // Send to backend (Async)
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> System.out.println("Saved: " + response.body()));
        });

        // 3. Button Logic - VIEW
        refreshButton.setOnAction(e -> {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/all"))
                    .GET()
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        // Update UI (Must be on JavaFX thread)
                        javafx.application.Platform.runLater(() ->
                                displayArea.setText(response.body())
                        );
                    });
        });

        // 4. Layout
        VBox layout = new VBox(10, inputField, saveButton, refreshButton, displayArea);
        stage.setScene(new Scene(layout, 300, 400));
        stage.setTitle("Sandbox App");
        stage.show();
    }
}
