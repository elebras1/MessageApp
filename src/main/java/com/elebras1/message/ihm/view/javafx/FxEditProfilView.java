package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.controller.IEditProfilController;
import com.elebras1.message.ihm.view.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class FxEditProfilView extends GridPane implements View {

    private final TextField nameField;

    public FxEditProfilView(IEditProfilController controller) {
        nameField = new TextField();
        setAlignment(Pos.CENTER);
        setHgap(8);
        setVgap(8);
        setPadding(new Insets(16));
        buildLayout(controller);
    }

    private void buildLayout(IEditProfilController controller) {
        add(new Label("Name"), 0, 2);
        add(nameField, 1, 2);

        Button editButton = new Button("Edit");
        editButton.setStyle(
            "-fx-background-color: #FDB92E;" +
            "-fx-text-fill: #222222;" +
            "-fx-background-radius: 4;" +
            "-fx-border-color: transparent;"
        );
        editButton.setOnAction(e -> controller.editProfil(nameField.getText().trim()));
        add(editButton, 0, 4, 2, 1);
    }
}

