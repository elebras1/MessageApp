package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.controller.ISubscribeController;
import com.elebras1.message.ihm.view.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class FxSubscribeView extends GridPane implements View {

    private final TextField usernameField;
    private final TextField tagField;
    private final PasswordField passwordField;

    public FxSubscribeView(ISubscribeController controller) {
        usernameField = new TextField();
        tagField = new TextField();
        passwordField = new PasswordField();
        setAlignment(Pos.CENTER);
        setHgap(8);
        setVgap(8);
        setPadding(new Insets(16));
        buildLayout(controller);
    }

    private void buildLayout(ISubscribeController controller) {
        Label title = new Label("Create account");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        add(title, 0, 0, 2, 1);

        add(new Label("Username"), 0, 1);
        add(usernameField, 1, 1);

        add(new Label("Tag"), 0, 2);
        add(tagField, 1, 2);

        add(new Label("Password"), 0, 3);
        add(passwordField, 1, 3);

        Button subscribeButton = new Button("Subscribe");
        subscribeButton.setStyle(
            "-fx-background-color: #5CFA69;" +
            "-fx-text-fill: #222222;" +
            "-fx-background-radius: 4;" +
            "-fx-border-color: transparent;"
        );
        subscribeButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String tag = tagField.getText().trim();
            String password = passwordField.getText().trim();
            try {
                controller.subscribe(username, tag, password);
                new Alert(Alert.AlertType.INFORMATION, "Inscription réussie !", ButtonType.OK).showAndWait();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
        });
        add(subscribeButton, 0, 4, 2, 1);
    }
}

