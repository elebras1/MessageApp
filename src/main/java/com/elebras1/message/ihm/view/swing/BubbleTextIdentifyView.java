package com.elebras1.message.ihm.view.swing;

import com.elebras1.message.ihm.view.View;
import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

public class BubbleTextIdentifyView extends BubbleTextView implements View {

    private static final Color DEFAULT_COLOR = new Color(0x71FFFF);
    private static final Color SELECTED_COLOR = new Color(0x00BFBF);

    private final UUID id;
    private OnClickUuidCallback onClickCallback;

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
                if (onClickCallback != null) {
                    onClickCallback.onClick(BubbleTextIdentifyView.this.id);
                }
            }
        });
    }

    public UUID getId() {
        return id;
    }

    public void setOnClickCallback(OnClickUuidCallback callback) {
        this.onClickCallback = callback;
    }
}