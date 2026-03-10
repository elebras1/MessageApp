package com.elebras1.message.ihm.view;

import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class ChannelView extends JPanel {
    private final BubbleTextIdentifyView bubble;
    private final String channelName;

    public ChannelView(UUID id, String channelName, boolean isCreator, boolean isPrivate, OnClickUuidCallback onRemove, OnClickUuidCallback onAddMember) {
        this.channelName = channelName;
        this.setLayout(new BorderLayout(4, 0));
        this.setOpaque(false);

        this.bubble = new BubbleTextIdentifyView(id, channelName);
        this.add(this.bubble, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(Box.createHorizontalGlue());

        if (isCreator && isPrivate) {
            JButton addMemberBtn = createButton("➕", new Color(0x2ECC71), "Ajouter un membre");
            addMemberBtn.addActionListener(_ -> onAddMember.onClick(this.bubble.getId()));
            buttonsPanel.add(addMemberBtn);
            buttonsPanel.add(Box.createHorizontalStrut(4));
        }

        if (isCreator || isPrivate) {
            Color bg = isCreator ? new Color(0xE74C3C) : new Color(0xFF8C00);
            String tooltip = isCreator ? "Supprimer le canal" : "Quitter le canal";
            String icon = isCreator ? "🗑️" : "🚪";
            JButton removeChannel = createButton(icon, bg, tooltip);
            removeChannel.addActionListener(_ -> onRemove.onClick(this.bubble.getId()));
            buttonsPanel.add(removeChannel);
        }

        JPanel eastWrapper = new JPanel(new GridBagLayout());
        eastWrapper.setOpaque(false);
        eastWrapper.add(buttonsPanel);

        this.add(eastWrapper, BorderLayout.EAST);
    }

    private JButton createButton(String emoji, Color bgColor, String tooltip) {
        JButton btn = new JButton(emoji);
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(30, 30));
        btn.setMaximumSize(new Dimension(30, 30));
        btn.setMinimumSize(new Dimension(30, 30));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setToolTipText(tooltip);
        btn.setMargin(new Insets(0, 0, 0, 0));
        return btn;
    }

    public void setOnClickCallback(OnClickUuidCallback callback) {
        this.bubble.setOnClickCallback(callback);
    }

    public String getChannelName() {
        return channelName;
    }
}