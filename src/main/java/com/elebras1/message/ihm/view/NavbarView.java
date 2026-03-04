package com.elebras1.message.ihm.view;

import com.elebras1.message.controller.INavBarController;
import com.elebras1.message.core.session.ISessionObserver;
import com.elebras1.message.datamodel.User;

import javax.swing.*;
import java.awt.*;

public class NavbarView extends JPanel implements ISessionObserver {
    private final INavBarController viewManager;
    private JButton subscribeButton;
    private JButton loginButton;
    private JButton logoutButton;

    public NavbarView(INavBarController viewManager) {
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

        this.add(loginButton);
        this.add(subscribeButton);
        this.add(logoutButton);
    }

    @Override
    public void notifyLogin(User connectedUser) {
        this.loginButton.setVisible(false);
        this.subscribeButton.setVisible(false);
        this.logoutButton.setVisible(true);
    }

    @Override
    public void notifyLogout() {
        this.loginButton.setVisible(true);
        this.subscribeButton.setVisible(true);
        this.logoutButton.setVisible(false);
    }
}
