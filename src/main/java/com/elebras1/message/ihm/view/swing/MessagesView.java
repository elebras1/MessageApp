package com.elebras1.message.ihm.view.swing;

import com.elebras1.message.ihm.view.IMessageView;
import com.elebras1.message.ihm.view.IMessagesView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MessagesView extends JPanel implements IMessagesView {
    private final ListElementView listElementView;
    private final BubbleTextEditableView newMessageView;
    private final JButton sendButton;
    private final SearchBarView searchBar;
    private final List<MessageView> allMessages = new ArrayList<>();

    private static final int MAX_CHARACTERS = 200;

    public MessagesView() {
        setLayout(new BorderLayout());

        searchBar = new SearchBarView("Rechercher dans les messages...");
        add(searchBar, BorderLayout.NORTH);

        listElementView = new ListElementView();
        add(listElementView, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout(8, 0));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        newMessageView = new BubbleTextEditableView(MAX_CHARACTERS);

        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(0xFDB92E));
        sendButton.setBorderPainted(false);
        sendButton.setPreferredSize(new Dimension(70, 35));

        inputPanel.add(newMessageView, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        searchBar.setOnSearchChanged(this::filterMessages);
    }

    private void filterMessages(String query) {
        this.listElementView.clearContent();
        String lowerQuery = query.toLowerCase().trim();
        for (MessageView messageView : allMessages) {
            if (lowerQuery.isEmpty() || messageView.getContent().toLowerCase().contains(lowerQuery) || messageView.getMetadata().toLowerCase().contains(lowerQuery)) {
                this.listElementView.addContent(messageView);
            }
        }
    }

    @Override
    public void setSendAction(Consumer<String> onSend) {
        sendButton.addActionListener(_ -> {
            String text = newMessageView.getText();
            if (!text.trim().isEmpty()) {
                onSend.accept(text);
                newMessageView.clear();
            }
        });
    }

    @Override
    public void clearMessages() {
        this.allMessages.clear();
        this.listElementView.clearContent();
        this.searchBar.clear();
    }

    @Override
    public void addMessage(IMessageView messageView) {
        MessageView messageViewImpl = (MessageView) messageView;
        this.allMessages.add(messageViewImpl);
        String query = this.searchBar.getText();
        if (query.isEmpty() || messageViewImpl.getContent().toLowerCase().contains(query.toLowerCase()) || messageViewImpl.getMetadata().toLowerCase().contains(query.toLowerCase())) {
            this.listElementView.addContent(messageViewImpl);
        }
    }
}