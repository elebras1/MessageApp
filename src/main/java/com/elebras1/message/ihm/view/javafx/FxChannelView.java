package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.ihm.view.IChannelView;
import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.UUID;

public class FxChannelView extends HBox implements IChannelView {

    private final FxBubbleTextIdentifyView bubble;
    private final String channelName;

    public FxChannelView(UUID id, String channelName, boolean isCreator, boolean isPrivate,
                         OnClickUuidCallback onRemove, OnClickUuidCallback onAddMember) {
        this.channelName = channelName;
        setSpacing(4);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(2));

        bubble = new FxBubbleTextIdentifyView(id, channelName);
        HBox.setHgrow(bubble, Priority.ALWAYS);
        getChildren().add(bubble);

        HBox buttonsBox = new HBox(4);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        if (isCreator && isPrivate) {
            Button addMemberBtn = createButton("➕", "#2ECC71", "Ajouter un membre");
            addMemberBtn.setOnAction(e -> onAddMember.onClick(bubble.getUuid()));
            buttonsBox.getChildren().add(addMemberBtn);
        }

        if (isCreator || isPrivate) {
            String bg = isCreator ? "#E74C3C" : "#FF8C00";
            String tooltip = isCreator ? "Supprimer le canal" : "Quitter le canal";
            String icon = isCreator ? "🗑️" : "🚪";
            Button removeBtn = createButton(icon, bg, tooltip);
            removeBtn.setOnAction(e -> onRemove.onClick(bubble.getUuid()));
            buttonsBox.getChildren().add(removeBtn);
        }

        getChildren().add(buttonsBox);
    }

    private Button createButton(String emoji, String bgColor, String tooltip) {
        Button btn = new Button(emoji);
        btn.setStyle(
            "-fx-background-color: " + bgColor + ";" +
            "-fx-text-fill: white;" +
            "-fx-min-width: 30; -fx-max-width: 30;" +
            "-fx-min-height: 30; -fx-max-height: 30;" +
            "-fx-font-size: 13;" +
            "-fx-background-radius: 4;" +
            "-fx-padding: 0;"
        );
        btn.setCursor(Cursor.HAND);
        btn.setTooltip(new Tooltip(tooltip));
        return btn;
    }

    @Override
    public void setOnClickCallback(OnClickUuidCallback callback) {
        bubble.setOnClickCallback(callback);
    }

    @Override
    public String getChannelName() {
        return channelName;
    }
}

