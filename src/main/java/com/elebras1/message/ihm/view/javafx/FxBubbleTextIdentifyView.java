package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.ihm.view.View;
import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;
import javafx.scene.Cursor;

import java.util.UUID;

public class FxBubbleTextIdentifyView extends FxBubbleTextView implements View {

    private static final String DEFAULT_BG = "#71FFFF";
    private static final String SELECTED_BG = "#00BFBF";

    private final UUID id;
    private OnClickUuidCallback onClickCallback;

    public FxBubbleTextIdentifyView(UUID id, String content) {
        super(content);
        this.id = id;
        setCursor(Cursor.HAND);
        setOnMousePressed(e -> setStyle(buildStyle(SELECTED_BG)));
        setOnMouseReleased(e -> {
            setStyle(buildStyle(DEFAULT_BG));
            if (onClickCallback != null) {
                onClickCallback.onClick(this.id);
            }
        });
    }

    private String buildStyle(String bg) {
        return "-fx-background-color: " + bg + ";" +
               "-fx-background-radius: 6;" +
               "-fx-border-color: #FFFFFF;" +
               "-fx-border-radius: 6;" +
               "-fx-border-width: 1;" +
               "-fx-padding: 8 12 8 12;";
    }

    public UUID getUuid() {
        return id;
    }

    public void setOnClickCallback(OnClickUuidCallback callback) {
        this.onClickCallback = callback;
    }
}

