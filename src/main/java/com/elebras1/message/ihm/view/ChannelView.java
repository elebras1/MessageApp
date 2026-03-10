package com.elebras1.message.ihm.view;

import com.elebras1.message.controller.IChannelObserver;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ChannelView extends JPanel {
    private final BubbleTextIdentifyView bubble;
    private final JButton removeChannel;

    public ChannelView(UUID id, String channelName, UuidCallback callback) {
        this.setLayout(new BorderLayout());

        this.bubble = new BubbleTextIdentifyView(id, channelName);
        this.add(this.bubble, BorderLayout.WEST);

        this.removeChannel = new JButton("x");
        this.removeChannel.setMargin(new Insets(2, 6, 2, 6));
        this.removeChannel.setBackground(new Color(0x002CFF));
        this.removeChannel.setBorderPainted(false);
        this.removeChannel.addActionListener(_ -> callback.onClick(this.bubble.getId()));
        this.add(this.removeChannel, BorderLayout.EAST);
    }

    public void addOnClickListener(OnClickListener listener) {
        this.bubble.addOnClickListener(listener);
    }
}