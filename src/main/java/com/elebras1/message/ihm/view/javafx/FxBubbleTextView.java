package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.ihm.view.View;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class FxBubbleTextView extends StackPane implements View {

    private static final String BG_COLOR = "#71FFFF";

    private final String text;

    public FxBubbleTextView(String content) {
        this.text = content;

        Label label = new Label(content);
        label.setWrapText(true);
        label.setStyle("-fx-text-fill: #222222;");

        setStyle(
                "-fx-background-color: " + BG_COLOR + ";" +
                "-fx-background-radius: 6;" +
                "-fx-border-color: #FFFFFF;" +
                "-fx-border-radius: 6;" +
                "-fx-border-width: 1;" +
                "-fx-padding: 8 12 8 12;"
        );
        getChildren().add(label);
    }

    public String getText() {
        return text;
    }
}

