package com.elebras1.message.ihm.view.swing;

import com.elebras1.message.ihm.view.View;
import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

/**
 * Vue représentant un utilisateur avec un indicateur de présence (rond coloré).
 * Vert = connecté, Bleu = déconnecté.
 */
public class UserView extends JPanel implements View {

    private static final Color ONLINE_COLOR = new Color(0x4CAF50);
    private static final Color OFFLINE_COLOR = new Color(0x2196F3);
    private static final int INDICATOR_SIZE = 12;

    private final BubbleTextIdentifyView bubbleView;
    private final OnlineIndicator indicator;

    public UserView(UUID id, String displayName, boolean online) {
        this.setLayout(new BorderLayout(6, 0));
        this.setOpaque(false);

        this.bubbleView = new BubbleTextIdentifyView(id, displayName);
        this.indicator = new OnlineIndicator(online);

        JPanel indicatorWrapper = new JPanel(new GridBagLayout());
        indicatorWrapper.setOpaque(false);
        indicatorWrapper.add(this.indicator);

        this.add(this.bubbleView, BorderLayout.CENTER);
        this.add(indicatorWrapper, BorderLayout.EAST);
    }

    /**
     * Met à jour la couleur de l'indicateur selon le statut de connexion.
     */
    public void setOnline(boolean online) {
        this.indicator.setOnline(online);
        this.indicator.repaint();
    }

    public String getText() {
        return this.bubbleView.getText();
    }

    public UUID getId() {
        return this.bubbleView.getId();
    }

    public void setOnClickCallback(OnClickUuidCallback callback) {
        this.bubbleView.setOnClickCallback(callback);
    }

    /**
     * Composant graphique représentant l'indicateur de présence (rond coloré).
     */
    private static class OnlineIndicator extends JPanel {

        private boolean online;

        public OnlineIndicator(boolean online) {
            this.online = online;
            this.setPreferredSize(new Dimension(INDICATOR_SIZE, INDICATOR_SIZE));
            this.setOpaque(false);
        }

        public void setOnline(boolean online) {
            this.online = online;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(online ? ONLINE_COLOR : OFFLINE_COLOR);
            int x = (getWidth() - INDICATOR_SIZE) / 2;
            int y = (getHeight() - INDICATOR_SIZE) / 2;
            g2.fillOval(x, y, INDICATOR_SIZE, INDICATOR_SIZE);
            g2.setColor(online ? ONLINE_COLOR.darker() : OFFLINE_COLOR.darker());
            g2.drawOval(x, y, INDICATOR_SIZE - 1, INDICATOR_SIZE - 1);
            g2.dispose();
        }
    }
}

