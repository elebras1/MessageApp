package com.elebras1.message.ihm.view;

import com.elebras1.message.datamodel.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

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

    public void setOnAddChannelAction(Consumer<String> onChannelNameChosen) {
        this.addChannelButton.addActionListener(_ ->
                new CreateChannelView(this, onChannelNameChosen).setVisible(true)
        );
    }

    public void showMembersDialog(String channelName, List<User> members, List<User> candidates, Consumer<User> onAddMember, Consumer<User> onRemoveMember) {
        new ChannelMembersView(this, channelName, members, candidates, onAddMember, onRemoveMember).setVisible(true);
    }

    public void clearChannels() {
        this.channelsList.clearContent();
    }

    public void addChannel(ChannelView channelView) {
        this.channelsList.addContent(channelView);
    }
}


