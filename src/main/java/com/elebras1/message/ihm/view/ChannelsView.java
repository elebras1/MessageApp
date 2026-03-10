package com.elebras1.message.ihm.view;

import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.callback.OnCreateChannelCallback;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ChannelsView extends JPanel {
    private final ListElementView channelsList;
    private final JButton addChannelButton;
    private final SearchBarView searchBar;
    private final List<ChannelView> allChannels = new ArrayList<>();

    public ChannelsView() {
        this.setLayout(new BorderLayout());
        this.channelsList = new ListElementView();
        this.searchBar = new SearchBarView("Rechercher un channel...");
        this.addChannelButton = new JButton("+ Nouveau channel");
        this.addChannelButton.setBackground(new Color(0xFDB92E));
        this.addChannelButton.setBorderPainted(false);
        this.addChannelButton.setFocusPainted(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(this.addChannelButton, BorderLayout.NORTH);
        topPanel.add(this.searchBar, BorderLayout.SOUTH);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(this.channelsList, BorderLayout.CENTER);

        this.searchBar.setOnSearchChanged(this::filterChannels);
    }

    private void filterChannels(String query) {
        this.channelsList.clearContent();
        String lowerQuery = query.toLowerCase().trim();
        for (ChannelView channelView : allChannels) {
            if (lowerQuery.isEmpty() || channelView.getChannelName().toLowerCase().contains(lowerQuery)) {
                this.channelsList.addContent(channelView);
            }
        }
    }

    public void setOnAddChannelAction(OnCreateChannelCallback onChannelCreated) {
        this.addChannelButton.addActionListener(_ ->
                new CreateChannelView(this, onChannelCreated).setVisible(true)
        );
    }

    public void showMembersDialog(String channelName, List<User> members, List<User> candidates, Consumer<User> onAddMember, Consumer<User> onRemoveMember) {
        new ChannelMembersView(this, channelName, members, candidates, onAddMember, onRemoveMember).setVisible(true);
    }

    public void clearChannels() {
        this.allChannels.clear();
        this.channelsList.clearContent();
        this.searchBar.clear();
    }

    public void addChannel(ChannelView channelView) {
        this.allChannels.add(channelView);
        String query = this.searchBar.getText();
        if (query.isEmpty() || channelView.getChannelName().toLowerCase().contains(query.toLowerCase())) {
            this.channelsList.addContent(channelView);
        }
    }
}