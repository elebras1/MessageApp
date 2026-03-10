package com.elebras1.message.ihm.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class BubbleTextEditableView extends JPanel {

    private final JTextArea textArea;

    public BubbleTextEditableView(int maxCharacters) {
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

        ((AbstractDocument) textArea.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                int remaining = maxCharacters - fb.getDocument().getLength();
                if (remaining <= 0 || string == null) return;
                if (string.length() > remaining) string = string.substring(0, remaining);
                super.insertString(fb, offset, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                int remaining = maxCharacters - (fb.getDocument().getLength() - length);
                if (remaining <= 0 || text == null) return;
                if (text.length() > remaining) text = text.substring(0, remaining);
                super.replace(fb, offset, length, text, attrs);
            }
        });

        add(textArea, BorderLayout.CENTER);
    }

    public String getText() {
        return textArea.getText();
    }

    public void clear() {
        textArea.setText("");
    }
}