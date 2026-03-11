package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.ihm.view.View;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class FxListElementView extends ScrollPane implements View {

    private final VBox container;

    public FxListElementView() {
        container = new VBox(6);
        container.setStyle("-fx-background-color: #D06565; -fx-padding: 8;");
        setContent(container);
        setFitToWidth(true);
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setStyle("-fx-background-color: transparent; -fx-background: transparent;");
    }

    public void clearContent() {
        container.getChildren().clear();
    }

    public void addContent(Node node) {
        container.getChildren().add(node);
        layout();
        setVvalue(1.0);
    }
}

