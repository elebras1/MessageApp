package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.ihm.view.IMessageView;
import com.elebras1.message.ihm.view.IMessagesView;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FxMessagesView extends BorderPane implements IMessagesView {

    private final FxListElementView listElementView;
    private final FxBubbleTextEditableView newMessageView;
    private final Button sendButton;
    private final FxSearchBarView searchBar;
    private final List<FxMessageView> allMessages = new ArrayList<>();

    private static final int MAX_CHARACTERS = 200;

    public FxMessagesView() {
        searchBar = new FxSearchBarView("Rechercher dans les messages...");
        setTop(searchBar);

        listElementView = new FxListElementView();
        setCenter(listElementView);

        newMessageView = new FxBubbleTextEditableView(MAX_CHARACTERS);
        HBox.setHgrow(newMessageView, Priority.ALWAYS);

        sendButton = new Button("Send");
        sendButton.setStyle(
            "-fx-background-color: #FDB92E;" +
            "-fx-text-fill: #222222;" +
            "-fx-background-radius: 4;" +
            "-fx-border-color: transparent;" +
            "-fx-pref-width: 70; -fx-pref-height: 35;"
        );

        HBox inputPanel = new HBox(8, newMessageView, sendButton);
        inputPanel.setPadding(new Insets(8, 12, 8, 12));
        setBottom(inputPanel);

        searchBar.setOnSearchChanged(this::filterMessages);
    }

    private void filterMessages(String query) {
        listElementView.clearContent();
        String lq = query.toLowerCase().trim();
        for (FxMessageView mv : allMessages) {
            if (lq.isEmpty()) {
                listElementView.addContent(mv);
            }
        }
    }

    @Override
    public void setSendAction(Consumer<String> onSend) {
        sendButton.setOnAction(e -> {
            String text = newMessageView.getText();
            if (!text.trim().isEmpty()) {
                onSend.accept(text);
                newMessageView.clear();
            }
        });
    }

    @Override
    public void clearMessages() {
        allMessages.clear();
        listElementView.clearContent();
        searchBar.clear();
    }

    @Override
    public void addMessage(IMessageView messageView) {
        FxMessageView mv = (FxMessageView) messageView;
        allMessages.add(mv);
        String query = searchBar.getText();
        if (query.isEmpty()) {
            listElementView.addContent(mv);
        }
    }
}

