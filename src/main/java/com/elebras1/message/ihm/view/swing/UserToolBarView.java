package com.elebras1.message.ihm.view.swing;

import com.elebras1.message.controller.INavBarController;
import com.elebras1.message.core.session.ISessionObserver;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.View;

import javax.swing.*;
import java.awt.*;

public class UserToolBarView extends JPanel implements ISessionObserver, View {
    private final INavBarController viewManager;
    private JButton subscribeButton;
    private JButton loginButton;
    private JButton logoutButton;
    private JButton renameButton;
    private JButton deleteAccountButton;
    private JLabel connectedUserLabel;

    public UserToolBarView(INavBarController viewManager) {
        this.viewManager = viewManager;
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        this.setBackground(new Color(0xEFD522));

        subscribeButton = new JButton("Subscribe");
        subscribeButton.setBackground(new Color(0x5CFA69));
        subscribeButton.setBorderPainted(false);
        subscribeButton.addActionListener(_ -> this.viewManager.showSubscribeView());

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0x5E7BF8));
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(_ -> this.viewManager.showLoginView());

        logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(0xF85E5E));
        logoutButton.setBorderPainted(false);
        logoutButton.addActionListener(_ -> this.viewManager.logout());
        logoutButton.setVisible(false);

        renameButton = new JButton("Rename");
        renameButton.setBackground(new Color(0xFDB92E));
        renameButton.setBorderPainted(false);
        renameButton.addActionListener(_ -> this.viewManager.showEditProfilView());
        renameButton.setVisible(false);

        deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.setBackground(new Color(0xF85E5E));
        deleteAccountButton.setBorderPainted(false);
        deleteAccountButton.addActionListener(_ -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete your account? This action cannot be undone.",
                    "Confirm Account Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (result == JOptionPane.YES_OPTION) {
                this.viewManager.removeUser();
            }
        });
        deleteAccountButton.setVisible(false);

        connectedUserLabel = new JLabel();
        connectedUserLabel.setForeground(new Color(0x333333));
        connectedUserLabel.setFont(connectedUserLabel.getFont().deriveFont(Font.BOLD, 13f));
        connectedUserLabel.setVisible(false);

        this.add(loginButton);
        this.add(subscribeButton);
        this.add(logoutButton);
        this.add(renameButton);
        this.add(deleteAccountButton);
        this.add(connectedUserLabel);
    }

    @Override
    public void notifyLogin(User connectedUser) {
        this.loginButton.setVisible(false);
        this.subscribeButton.setVisible(false);
        this.logoutButton.setVisible(true);
        this.renameButton.setVisible(true);
        this.deleteAccountButton.setVisible(true);
        this.connectedUserLabel.setText("👤 " + connectedUser.getName() + " (@" + connectedUser.getUserTag() + ")");
        this.connectedUserLabel.setVisible(true);
    }

    @Override
    public void notifyLogout() {
        this.loginButton.setVisible(true);
        this.subscribeButton.setVisible(true);
        this.logoutButton.setVisible(false);
        this.renameButton.setVisible(false);
        this.deleteAccountButton.setVisible(false);
        this.connectedUserLabel.setText("");
        this.connectedUserLabel.setVisible(false);
    }
}