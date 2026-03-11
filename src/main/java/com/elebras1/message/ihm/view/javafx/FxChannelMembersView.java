package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.View;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class FxChannelMembersView extends Stage implements View {

    public FxChannelMembersView(Node parent, String channelName, List<User> members,
                                 List<User> candidates, Consumer<User> onAddMember,
                                 Consumer<User> onRemoveMember) {
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Gérer les membres — " + channelName);
        setResizable(false);

        Label title = new Label("# " + channelName);
        title.setStyle("-fx-text-fill: #FDB92E; -fx-font-weight: bold; -fx-font-size: 16;");

        // Section membres actuels
        Label membersLabel = sectionLabel("MEMBRES ACTUELS");
        VBox membersBox = new VBox(4);
        membersBox.setStyle("-fx-background-color: #3C3C3C; -fx-padding: 8;");
        for (User member : members) {
            HBox row = new HBox(8);
            row.setAlignment(Pos.CENTER_LEFT);
            Label nameLabel = new Label(member.getName() + " (@" + member.getUserTag() + ")");
            nameLabel.setStyle("-fx-text-fill: #71FFFF; -fx-font-size: 13;");
            HBox.setHgrow(nameLabel, Priority.ALWAYS);
            Button removeBtn = styledButton("−", "#F85E5E", "white");
            removeBtn.setTooltip(new Tooltip("Retirer du channel"));
            removeBtn.setOnAction(e -> { onRemoveMember.accept(member); close(); });
            row.getChildren().addAll(nameLabel, removeBtn);
            membersBox.getChildren().add(row);
        }
        ScrollPane membersScroll = new ScrollPane(membersBox);
        membersScroll.setFitToWidth(true);
        membersScroll.setPrefHeight(160);
        membersScroll.setMaxHeight(160);

        // Section ajout de membre
        Label addLabel = sectionLabel("AJOUTER UN MEMBRE");
        VBox body = new VBox(8, membersLabel, membersScroll, new Separator(), addLabel);
        body.setStyle("-fx-background-color: #D06565;");

        if (candidates.isEmpty()) {
            Label noCandidate = new Label("Tous les utilisateurs sont déjà membres.");
            noCandidate.setStyle("-fx-text-fill: #AAAAAA; -fx-font-style: italic; -fx-font-size: 12;");
            body.getChildren().add(noCandidate);
        } else {
            ComboBox<String> combo = new ComboBox<>();
            combo.setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: #EEEEEE;");
            for (User u : candidates) {
                combo.getItems().add(u.getName() + " (@" + u.getUserTag() + ")");
            }
            combo.getSelectionModel().selectFirst();

            Button addBtn = styledButton("+ Ajouter", "#2ECC71", "#000000");
            addBtn.setOnAction(e -> {
                int idx = combo.getSelectionModel().getSelectedIndex();
                if (idx >= 0) { onAddMember.accept(candidates.get(idx)); close(); }
            });

            HBox addPanel = new HBox(8, combo, addBtn);
            addPanel.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(combo, Priority.ALWAYS);
            body.getChildren().add(addPanel);
        }

        Button closeBtn = styledButton("Fermer", "#FDB92E", "#000000");
        closeBtn.setOnAction(e -> close());
        HBox footer = new HBox(closeBtn);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setStyle("-fx-background-color: #D06565;");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #D06565; -fx-padding: 16;");
        root.setTop(title);
        root.setCenter(body);
        root.setBottom(footer);
        BorderPane.setMargin(body, new Insets(12, 0, 12, 0));

        setScene(new Scene(root, 420, 520));
    }

    private Label sectionLabel(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-text-fill: #AAAAAA; -fx-font-weight: bold; -fx-font-size: 11;");
        return l;
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

