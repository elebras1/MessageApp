package com.elebras1.message.ihm.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ListElementView extends JScrollPane {
    private final JPanel elementsPanel;

    public ListElementView() {
        this.elementsPanel = new JPanel();
        this.elementsPanel.setLayout(new BoxLayout(this.elementsPanel, BoxLayout.Y_AXIS));
        this.elementsPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        this.elementsPanel.setBackground(new Color(0xD06565));

        this.setViewportView(this.elementsPanel);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    public void clearContent() {
        this.elementsPanel.removeAll();
        this.elementsPanel.revalidate();
        this.elementsPanel.repaint();
    }

    public void addContent(JPanel view) {
        if (this.elementsPanel.getComponentCount() > 0) {
            this.elementsPanel.add(Box.createVerticalStrut(6));
        }
        view.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.elementsPanel.add(view);
        this.elementsPanel.revalidate();
        this.elementsPanel.repaint();

        SwingUtilities.invokeLater(() -> {
            view.setMaximumSize(new Dimension(Integer.MAX_VALUE, view.getPreferredSize().height));
            this.elementsPanel.revalidate();
            this.elementsPanel.repaint();
            JScrollBar vertical = this.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }
}
