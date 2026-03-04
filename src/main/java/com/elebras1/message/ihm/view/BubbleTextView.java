package com.elebras1.message.ihm.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BubbleTextView extends JPanel {

    public BubbleTextView(String content) {
        setLayout(new BorderLayout());
        setBackground(new Color(0x71FFFF));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xFFFFFF), 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));

        JLabel label = new JLabel(content);
        add(label, BorderLayout.CENTER);
    }
}
