package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.ihm.view.IChatView;
import com.elebras1.message.ihm.view.View;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

public class FxChatView extends BorderPane implements IChatView {

    private final SplitPane horizontalSplit;
    private final SplitPane verticalSplit;

    public FxChatView() {
        verticalSplit = new SplitPane();
        verticalSplit.setOrientation(Orientation.VERTICAL);
        verticalSplit.setDividerPositions(0.5);

        horizontalSplit = new SplitPane();
        horizontalSplit.setDividerPositions(0.3);
        horizontalSplit.getItems().add(verticalSplit);

        setCenter(horizontalSplit);
    }

    @Override
    public void setLeftUpSection(View leftUpSection) {
        Node node = (Node) leftUpSection;
        if (verticalSplit.getItems().isEmpty()) {
            verticalSplit.getItems().add(node);
        } else {
            verticalSplit.getItems().set(0, node);
        }
        horizontalSplit.setDividerPositions(0.3);
    }

    @Override
    public void setLeftDownSection(View leftDownSection) {
        Node node = (Node) leftDownSection;
        if (verticalSplit.getItems().size() < 2) {
            verticalSplit.getItems().add(node);
        } else {
            verticalSplit.getItems().set(1, node);
        }
        horizontalSplit.setDividerPositions(0.3);
    }

    @Override
    public void setRightSection(View rightSection) {
        Node node = (Node) rightSection;
        if (horizontalSplit.getItems().size() < 2) {
            horizontalSplit.getItems().add(node);
        } else {
            horizontalSplit.getItems().set(1, node);
        }
    }
}

