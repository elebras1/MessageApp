package com.elebras1.message.ihm.view;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class MessagesView extends JPanel {
    private final ListElementView listElementView;
    private final BubbleTextEditableView newMessageView;
    private final JButton sendButton;

    private static final int MAX_CHARACTERS = 200;

    public MessagesView() {
        setLayout(new BorderLayout());

        listElementView = new ListElementView();
        add(listElementView, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout(8, 0));
        inputPanel.setOpaque(false);

        inputPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        newMessageView = new BubbleTextEditableView();

        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(0xFDB92E));
        sendButton.setBorderPainted(false);

        sendButton.setPreferredSize(new Dimension(70, 35));

        inputPanel.add(newMessageView, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);
    }

    public void setSendAction(Consumer<String> onSend) {
        sendButton.addActionListener(_ -> {
            String text = newMessageView.getText();

            if (text.length() > MAX_CHARACTERS) {
                JOptionPane.showMessageDialog(
                        this,
                        "Le message est limité à " + MAX_CHARACTERS + " caractères.\nVotre message en contient " + text.length() + ".",
                        "Message trop long",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (!text.trim().isEmpty()) {
                onSend.accept(text);
                newMessageView.clear();
            }
        });
    }

    public void clearMessages() {
        listElementView.clearContent();
    }

    public void addMessage(MessageView messageView) {
        listElementView.addContent(messageView);
    }
}