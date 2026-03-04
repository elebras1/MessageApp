package com.elebras1.message.ihm.view;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class MessagesView extends JPanel {
    private final ListBubbleTextView listBubbleTextView;
    private final BubbleTextEditableView newMessageView;
    private final JButton sendButton;

    public MessagesView() {
        setLayout(new BorderLayout());

        listBubbleTextView = new ListBubbleTextView();
        add(listBubbleTextView, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        newMessageView = new BubbleTextEditableView();
        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(0xFDB92E));
        sendButton.setBorderPainted(false);

        inputPanel.add(newMessageView);
        inputPanel.add(sendButton);

        add(inputPanel, BorderLayout.SOUTH);
    }

    public void setSendAction(Consumer<String> onSend) {
        sendButton.addActionListener(_ -> {
            String text = newMessageView.getText();
            if (!text.trim().isEmpty()) {
                onSend.accept(text);
                newMessageView.clear();
            }
        });
    }

    public void clearMessages() {
        listBubbleTextView.clearContent();
    }

    public void addMessage(BubbleTextView messageView) {
        listBubbleTextView.addContent(messageView);
    }
}