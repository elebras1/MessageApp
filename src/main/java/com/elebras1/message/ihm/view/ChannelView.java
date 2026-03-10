package com.elebras1.message.ihm.view;

import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class ChannelView extends JPanel {
    private final BubbleTextIdentifyView bubble;

    public ChannelView(UUID id, String channelName, boolean isCreator, boolean isPrivate, OnClickUuidCallback onRemove, OnClickUuidCallback onAddMember) {
        this.setLayout(new BorderLayout(4, 0));
        this.setOpaque(false);

        this.bubble = new BubbleTextIdentifyView(id, channelName);
        this.add(this.bubble, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 0));
        buttonsPanel.setOpaque(false);

        if (isCreator && isPrivate) {
            JButton addMemberBtn = new JButton("+");
            addMemberBtn.setMargin(new Insets(2, 6, 2, 6));
            addMemberBtn.setBackground(new Color(0x2ECC71));
            addMemberBtn.setBorderPainted(false);
            addMemberBtn.setOpaque(true);
            addMemberBtn.setToolTipText("Ajouter un membre");
            addMemberBtn.addActionListener(_ -> onAddMember.onClick(this.bubble.getId()));
            buttonsPanel.add(addMemberBtn);
        }

        if(isCreator || isPrivate) {
            JButton removeChannel = new JButton("x");
            removeChannel.setMargin(new Insets(2, 6, 2, 6));
            removeChannel.setBackground(isCreator ? new Color(0xE74C3C) : new Color(0xFF8C00));
            removeChannel.setBorderPainted(false);
            removeChannel.setOpaque(true);
            removeChannel.setToolTipText(isCreator ? "Supprimer le channel" : "Quitter le channel");
            removeChannel.addActionListener(_ -> onRemove.onClick(this.bubble.getId()));
            buttonsPanel.add(removeChannel);
        }

        this.add(buttonsPanel, BorderLayout.EAST);
    }

    public void setOnClickCallback(OnClickUuidCallback callback) {
        this.bubble.setOnClickCallback(callback);
    }
}