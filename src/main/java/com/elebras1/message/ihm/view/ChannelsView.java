package com.elebras1.message.ihm.view;

import javax.swing.*;
import java.awt.*;

public class ChannelsView extends JPanel {
    private final ListElementView channelsList;
    private final JButton addChannelButton;

    public ChannelsView() {
        this.setLayout(new BorderLayout());
        this.channelsList = new ListElementView();
        this.addChannelButton = new JButton("+ Nouveau channel");
        this.addChannelButton.setBackground(new Color(0xFDB92E));
        this.addChannelButton.setBorderPainted(false);
        this.addChannelButton.setFocusPainted(false);
        this.add(this.addChannelButton, BorderLayout.NORTH);
        this.add(this.channelsList, BorderLayout.CENTER);
    }

    public void setOnAddChannelAction(OnClickCallback callback) {
        this.addChannelButton.addActionListener(_ -> callback.onClick());
    }

    public void clearChannels() {
        this.channelsList.clearContent();
    }

    public void addChannel(ChannelView channelView) {
        this.channelsList.addContent(channelView);
    }
}
