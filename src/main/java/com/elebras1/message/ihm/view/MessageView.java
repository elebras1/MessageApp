package com.elebras1.message.ihm.view;

import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.UUID;
import java.util.function.Consumer;

public class MessageView extends JPanel {
    private final String content;
    private final String metadata;
    private OnClickUuidCallback onDeleteCallback;

    public MessageView(UUID id, String content, String metadata, boolean isMine) {
        this.content = content;
        this.metadata = metadata;
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel metaPanel = new JPanel(new BorderLayout());
        metaPanel.setOpaque(false);

        JLabel metadataLabel = new JLabel(metadata);
        metadataLabel.setForeground(new Color(0x91FD66));
        metadataLabel.setFont(metadataLabel.getFont().deriveFont(10f));
        metadataLabel.setBorder(new EmptyBorder(0, 5, 2, 0));

        JButton deleteButton = new JButton("✕");
        deleteButton.setForeground(new Color(0xFF5555));
        deleteButton.setBackground(new Color(0xEFFF21));
        deleteButton.setBorderPainted(false);
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteButton.setToolTipText("Supprimer le message");
        deleteButton.setMargin(new Insets(0, 4, 0, 4));
        deleteButton.addActionListener(_ -> {
            if (onDeleteCallback != null) {
                onDeleteCallback.onClick(id);
            }
        });

        metaPanel.add(metadataLabel, BorderLayout.CENTER);
        if(isMine) {
            metaPanel.add(deleteButton, BorderLayout.EAST);
        }

        BubbleTextView bubble = new BubbleTextView(content);

        add(metaPanel, BorderLayout.NORTH);
        add(bubble, BorderLayout.CENTER);
    }

    public MessageView(String content, String metadata) {
        this(UUID.randomUUID(), content, metadata, false);
    }

    public void setOnDeleteCallback(OnClickUuidCallback callback) {
        this.onDeleteCallback = callback;
    }

    public String getContent() {
        return content;
    }

    public String getMetadata() {
        return metadata;
    }
}
