package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.ihm.view.IMessageView;
import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.UUID;

public class FxMessageView extends BorderPane implements IMessageView {

    private OnClickUuidCallback onDeleteCallback;

    public FxMessageView(UUID id, String content, String metadata, boolean isMine) {
        setPadding(new Insets(2, 0, 2, 0));

        HBox metaBar = new HBox(6);
        metaBar.setPadding(new Insets(0, 0, 2, 0));

        Label metaLabel = new Label(metadata);
        metaLabel.setStyle("-fx-text-fill: #91FD66; -fx-font-size: 10;");
        HBox.setHgrow(metaLabel, Priority.ALWAYS);
        metaBar.getChildren().add(metaLabel);

        if (isMine) {
            Button deleteBtn = new Button("✕");
            deleteBtn.setStyle(
                "-fx-background-color: #EFFF21;" +
                "-fx-text-fill: #FF5555;" +
                "-fx-border-color: transparent;" +
                "-fx-padding: 0 4 0 4;"
            );
            deleteBtn.setCursor(Cursor.HAND);
            deleteBtn.setTooltip(new Tooltip("Supprimer le message"));
            deleteBtn.setOnAction(e -> {
                if (onDeleteCallback != null) onDeleteCallback.onClick(id);
            });
            metaBar.getChildren().add(deleteBtn);
        }

        setTop(metaBar);
        setCenter(new FxBubbleTextView(content));
    }

    @Override
    public void setOnDeleteCallback(OnClickUuidCallback callback) {
        this.onDeleteCallback = callback;
    }
}

