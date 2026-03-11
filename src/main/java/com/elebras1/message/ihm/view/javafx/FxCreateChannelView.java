package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.ihm.view.View;
import com.elebras1.message.ihm.view.callback.OnCreateChannelCallback;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class FxCreateChannelView extends Stage implements View {

    public FxCreateChannelView(Node parent, OnCreateChannelCallback onChannelCreated) {
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Créer un channel");
        setResizable(false);

        Label title = new Label("Nouveau channel");
        title.setStyle("-fx-text-fill: #FDB92E; -fx-font-weight: bold; -fx-font-size: 15;");

        Label nameLabel = new Label("Nom du channel :");
        nameLabel.setStyle("-fx-text-fill: #EEEEEE;");

        TextField nameField = new TextField();
        nameField.setStyle(
            "-fx-background-color: #3C3C3C;" +
            "-fx-text-fill: #EEEEEE;" +
            "-fx-border-color: #FDB92E;" +
            "-fx-border-width: 1;" +
            "-fx-padding: 4 8 4 8;"
        );

        CheckBox privateCheck = new CheckBox("Channel privé");
        privateCheck.setSelected(true);
        privateCheck.setStyle("-fx-text-fill: #EEEEEE;");

        VBox center = new VBox(4, nameLabel, nameField, privateCheck);
        center.setStyle("-fx-background-color: #D06565;");

        Button cancelBtn = styledButton("Annuler", "#555555", "#EEEEEE");
        cancelBtn.setOnAction(e -> close());

        Button createBtn = styledButton("Créer", "#2ECC71", "#000000");
        createBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                onChannelCreated.onCreate(name, privateCheck.isSelected());
                close();
            } else {
                nameField.setStyle(nameField.getStyle() + "-fx-border-color: #F85E5E;");
            }
        });
        nameField.setOnAction(e -> createBtn.fire());

        HBox footer = new HBox(6, cancelBtn, createBtn);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setStyle("-fx-background-color: #D06565;");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #D06565; -fx-padding: 16;");
        root.setTop(title);
        root.setCenter(center);
        root.setBottom(footer);
        BorderPane.setMargin(center, new Insets(12, 0, 12, 0));

        setScene(new Scene(root, 360, 240));
    }

    private Button styledButton(String text, String bg, String fg) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-text-fill: " + fg + ";" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 4;" +
            "-fx-border-color: transparent;"
        );
        return btn;
    }
}

