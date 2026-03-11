package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.ihm.view.IUserView;
import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.UUID;

public class FxUserView extends HBox implements IUserView {

    private static final Color ONLINE_COLOR = Color.web("#4CAF50");
    private static final Color OFFLINE_COLOR = Color.web("#2196F3");
    private static final int INDICATOR_SIZE = 6;

    private final FxBubbleTextIdentifyView bubbleView;
    private final Circle indicator;

    public FxUserView(UUID id, String displayName, boolean online) {
        setSpacing(6);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(2));

        bubbleView = new FxBubbleTextIdentifyView(id, displayName);
        HBox.setHgrow(bubbleView, Priority.ALWAYS);

        indicator = new Circle(INDICATOR_SIZE, online ? ONLINE_COLOR : OFFLINE_COLOR);
        indicator.setStroke(online ? ONLINE_COLOR.darker() : OFFLINE_COLOR.darker());
        indicator.setStrokeWidth(1);

        getChildren().addAll(bubbleView, indicator);
    }

    public void setOnline(boolean online) {
        indicator.setFill(online ? ONLINE_COLOR : OFFLINE_COLOR);
        indicator.setStroke(online ? ONLINE_COLOR.darker() : OFFLINE_COLOR.darker());
    }

    @Override
    public String getText() {
        return bubbleView.getText();
    }

    @Override
    public void setOnClickCallback(OnClickUuidCallback callback) {
        bubbleView.setOnClickCallback(callback);
    }
}

