package com.elebras1.message.ihm.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ListBubbleTextView extends JScrollPane {
    private final JPanel messagesPanel;

    public ListBubbleTextView() {
        this.messagesPanel = new JPanel();
        this.messagesPanel.setLayout(new BoxLayout(this.messagesPanel, BoxLayout.Y_AXIS));
        this.messagesPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        this.messagesPanel.setBackground(new Color(0xD06565));

        this.setViewportView(this.messagesPanel);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    public void clearContent() {
        this.messagesPanel.removeAll();
        this.messagesPanel.revalidate();
        this.messagesPanel.repaint();
    }

    public void addContent(BubbleTextView view) {
        if (this.messagesPanel.getComponentCount() > 0) {
            this.messagesPanel.add(Box.createVerticalStrut(6));
        }
        view.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.messagesPanel.add(view);
        this.messagesPanel.revalidate();
        this.messagesPanel.repaint();

        SwingUtilities.invokeLater(() -> {
            view.setMaximumSize(new Dimension(Integer.MAX_VALUE, view.getPreferredSize().height));
            this.messagesPanel.revalidate();
            this.messagesPanel.repaint();
            JScrollBar vertical = this.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }
}
