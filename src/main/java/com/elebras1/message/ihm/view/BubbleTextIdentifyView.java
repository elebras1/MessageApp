package com.elebras1.message.ihm.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BubbleTextIdentifyView extends BubbleTextView {

    private static final Color DEFAULT_COLOR = new Color(0x71FFFF);
    private static final Color SELECTED_COLOR = new Color(0x00BFBF);

    private final UUID id;
    private final List<OnClickListener> listeners = new ArrayList<>();
    private boolean selected = false;

    public BubbleTextIdentifyView(UUID id, String content) {
        super(content);
        this.id = id;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(SELECTED_COLOR);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(DEFAULT_COLOR);
                repaint();
                listeners.forEach(l -> l.onClick(BubbleTextIdentifyView.this.id));
            }
        });
    }

    public UUID getId() {
        return id;
    }

    public void addOnClickListener(OnClickListener listener) {
        listeners.add(listener);
    }

    public void removeOnClickListener(OnClickListener listener) {
        listeners.remove(listener);
    }
}