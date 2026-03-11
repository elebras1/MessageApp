package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.controller.ILoginController;
import com.elebras1.message.ihm.view.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class FxLoginView extends GridPane implements View {

    private final TextField tagField;
    private final PasswordField passwordField;

    public FxLoginView(ILoginController controller) {
        tagField = new TextField();
        passwordField = new PasswordField();
        setAlignment(Pos.CENTER);
        setHgap(8);
        setVgap(8);
        setPadding(new Insets(16));
        buildLayout(controller);
    }

    private void buildLayout(ILoginController controller) {
        Label title = new Label("Sign in to Your Account");
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        add(title, 0, 0, 2, 1);

        add(new Label("Tag"), 0, 2);
        add(tagField, 1, 2);

        add(new Label("Password"), 0, 3);
        add(passwordField, 1, 3);

        Button loginButton = new Button("Login");
        loginButton.setStyle(
            "-fx-background-color: #5E7BF8;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 4;" +
            "-fx-border-color: transparent;"
        );
        loginButton.setOnAction(e -> {
            String tag = tagField.getText().trim();
            String password = passwordField.getText().trim();
            try {
                controller.login(tag, password);
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
        });
        add(loginButton, 0, 4, 2, 1);
    }
}

