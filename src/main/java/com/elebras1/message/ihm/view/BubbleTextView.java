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

        JLabel label = new JLabel(hasTag(content) ? toHtml(content) : content);
        add(label, BorderLayout.CENTER);
    }

    private boolean hasTag(String content) {
        return content.contains("@");
    }

    private String toHtml(String content) {
        String escaped = content
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");

        String highlighted = escaped.replaceAll(
                "@([\\w\\u00C0-\\u024F]+)",
                "<span style='color:#FDB92E;font-weight:bold;'>@$1</span>"
        );

        return "<html>" + highlighted + "</html>";
    }

    public String getText() {
        return text;
    }
}