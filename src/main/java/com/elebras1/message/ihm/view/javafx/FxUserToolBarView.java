package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.controller.INavBarController;
import com.elebras1.message.core.session.ISessionObserver;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class FxUserToolBarView extends HBox implements ISessionObserver, View {

    private final INavBarController viewManager;
    private final Button subscribeButton;
    private final Button loginButton;
    private final Button logoutButton;
    private final Button renameButton;
    private final Button deleteAccountButton;
    private final Label connectedUserLabel;

    public FxUserToolBarView(INavBarController viewManager) {
        this.viewManager = viewManager;
        setAlignment(Pos.CENTER);
        setSpacing(20);
        setPadding(new Insets(10));
        setStyle("-fx-background-color: #EFD522;");

        subscribeButton = styledButton("Subscribe", "#5CFA69", "#222222");
        loginButton = styledButton("Login", "#5E7BF8", "white");
        logoutButton = styledButton("Logout", "#F85E5E", "white");
        renameButton = styledButton("Rename", "#FDB92E", "#222222");
        deleteAccountButton = styledButton("Delete Account", "#F85E5E", "white");
        connectedUserLabel = new Label();
        connectedUserLabel.setStyle("-fx-text-fill: #333333; -fx-font-weight: bold; -fx-font-size: 13;");

        subscribeButton.setOnAction(e -> viewManager.showSubscribeView());
        loginButton.setOnAction(e -> viewManager.showLoginView());
        logoutButton.setOnAction(e -> viewManager.logout());
        renameButton.setOnAction(e -> viewManager.showEditProfilView());
        deleteAccountButton.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete your account? This action cannot be undone.",
                ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Confirm Account Deletion");
            confirm.showAndWait().ifPresent(bt -> {
                if (bt == ButtonType.YES) viewManager.removeUser();
            });
        });

        logoutButton.setVisible(false);
        renameButton.setVisible(false);
        deleteAccountButton.setVisible(false);
        connectedUserLabel.setVisible(false);

        getChildren().addAll(loginButton, subscribeButton, logoutButton, renameButton, deleteAccountButton, connectedUserLabel);
    }

    private Button styledButton(String text, String bg, String fg) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-text-fill: " + fg + ";" +
            "-fx-background-radius: 4;" +
            "-fx-border-color: transparent;"
        );
        return btn;
    }

    @Override
    public void notifyLogin(User connectedUser) {
        loginButton.setVisible(false);
        subscribeButton.setVisible(false);
        logoutButton.setVisible(true);
        renameButton.setVisible(true);
        deleteAccountButton.setVisible(true);
        connectedUserLabel.setText("👤 " + connectedUser.getName() + " (@" + connectedUser.getUserTag() + ")");
        connectedUserLabel.setVisible(true);
    }

    @Override
    public void notifyLogout() {
        loginButton.setVisible(true);
        subscribeButton.setVisible(true);
        logoutButton.setVisible(false);
        renameButton.setVisible(false);
        deleteAccountButton.setVisible(false);
        connectedUserLabel.setText("");
        connectedUserLabel.setVisible(false);
    }
}

