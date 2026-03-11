package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.ihm.view.View;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

public class FxBubbleTextEditableView extends StackPane implements View {

    private final TextArea textArea;
    private final int maxCharacters;

    public FxBubbleTextEditableView(int maxCharacters) {
        this.maxCharacters = maxCharacters;
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPrefRowCount(2);
        textArea.setStyle("-fx-background-color: #71FFFF; -fx-border-color: transparent;");

        textArea.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() > maxCharacters) {
                textArea.setText(oldVal);
            }
        });

        setStyle(
            "-fx-background-color: #71FFFF;" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: #FFFFFF;" +
            "-fx-border-radius: 6;" +
            "-fx-border-width: 1;" +
            "-fx-padding: 4;"
        );
        getChildren().add(textArea);
    }

    public String getText() {
        return textArea.getText();
    }

    public void clear() {
        textArea.setText("");
    }
}

