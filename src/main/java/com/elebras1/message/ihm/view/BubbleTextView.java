package com.elebras1.message.ihm.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BubbleTextView extends JPanel {

    private final String text;

    public BubbleTextView(String content) {
        this.text = content;
        setLayout(new BorderLayout());
        setBackground(new Color(0x71FFFF));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xFFFFFF), 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));

        JLabel label = new JLabel(content);
        add(label, BorderLayout.CENTER);
    }

    public String getText() {
        return text;
    }
}
