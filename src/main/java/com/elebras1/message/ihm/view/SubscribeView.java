package com.elebras1.message.ihm.view;

import com.elebras1.message.controller.ISubscribeController;

import javax.swing.*;
import java.awt.*;

public class SubscribeView extends JPanel {
    private final JTextField usernameField;
    private final JTextField tagField;
    private final JPasswordField passwordField;

    public SubscribeView(ISubscribeController controller) {
        super(new GridBagLayout());
        this.usernameField = new JTextField(20);
        this.tagField = new JTextField(20);
        this.passwordField = new JPasswordField(20);

        this.buildLayout(controller);
    }

    private void buildLayout(ISubscribeController controller) {
        JLabel titleLabel = new JLabel("Create account");
        this.add(titleLabel, new GridBagConstraints(
                0, 0, 2, 1,
                0.0, 0.0,
                GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(6, 6, 6, 6),
                0, 0
        ));

        this.add(new JLabel("Username"), new GridBagConstraints(
                0, 1, 1, 1,
                0.0, 0.0,
                GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(6, 6, 6, 6),
                0, 0
        ));

        this.add(this.usernameField, new GridBagConstraints(
                1, 1, 1, 1,
                1.0, 0.0,
                GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(6, 6, 6, 6),
                0, 0
        ));

        this.add(new JLabel("Tag"), new GridBagConstraints(
                0, 2, 1, 1,
                0.0, 0.0,
                GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(6, 6, 6, 6),
                0, 0
        ));

        this.add(this.tagField, new GridBagConstraints(
                1, 2, 1, 1,
                1.0, 0.0,
                GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(6, 6, 6, 6),
                0, 0
        ));

        this.add(new JLabel("Password"), new GridBagConstraints(
                0, 3, 1, 1,
                0.0, 0.0,
                GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(6, 6, 6, 6),
                0, 0
        ));

        this.add(this.passwordField, new GridBagConstraints(
                1, 3, 1, 1,
                1.0, 0.0,
                GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(6, 6, 6, 6),
                0, 0
        ));

        JButton subscribeButton = new JButton("Subscribe");
        subscribeButton.addActionListener(_ -> controller.subscribe(this.usernameField.getText().trim(), this.tagField.getText().trim(), new String(this.passwordField.getPassword()).trim()));
        this.add(subscribeButton, new GridBagConstraints(
                0, 4, 2, 1,
                0.0, 0.0,
                GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                new Insets(6, 6, 6, 6),
                0, 0
        ));
    }
}
