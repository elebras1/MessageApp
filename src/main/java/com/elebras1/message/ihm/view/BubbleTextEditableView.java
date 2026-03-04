package com.elebras1.message.ihm.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BubbleTextEditableView extends JPanel {

    private final JTextArea textArea;

    public BubbleTextEditableView() {
        setLayout(new BorderLayout());
        setBackground(new Color(0x71FFFF));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xFFFFFF), 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));

        textArea = new JTextArea("");
        textArea.setBackground(new Color(0x71FFFF));
        textArea.setBorder(BorderFactory.createEmptyBorder());
        textArea.setOpaque(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(UIManager.getFont("Label.font"));
        add(textArea, BorderLayout.CENTER);
    }

    public String getText() {
        return textArea.getText();
    }

    public void clear() {
        textArea.setText("");
    }
}
