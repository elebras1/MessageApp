package com.elebras1.message.ihm.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MessageView extends JPanel {
    public MessageView(String content, String metadata) {
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel metadataLabel = new JLabel(metadata);
        metadataLabel.setForeground(new Color(0x91FD66));
        metadataLabel.setFont(metadataLabel.getFont().deriveFont(10f));
        metadataLabel.setBorder(new EmptyBorder(0, 5, 2, 0));

        BubbleTextView bubble = new BubbleTextView(content);

        add(metadataLabel, BorderLayout.NORTH);
        add(bubble, BorderLayout.CENTER);
    }
}
