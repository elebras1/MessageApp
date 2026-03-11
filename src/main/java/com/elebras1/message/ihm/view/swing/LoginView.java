package com.elebras1.message.ihm.view.swing;

import com.elebras1.message.controller.ILoginController;
import com.elebras1.message.ihm.view.View;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel implements View {
    private final JTextField tagField;
    private final JPasswordField passwordField;

    public LoginView(ILoginController controller) {
        super(new GridBagLayout());
        this.tagField = new JTextField(20);
        this.passwordField = new JPasswordField(20);

        this.buildLayout(controller);
    }

    private void buildLayout(ILoginController controller) {
        JLabel titleLabel = new JLabel("Sign in to Your Account");
        this.add(titleLabel, new GridBagConstraints(
                0, 0, 2, 1,
                0.0, 0.0,
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

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(_ -> {
            String tag = this.tagField.getText().trim();
            String password = new String(this.passwordField.getPassword()).trim();

            try {
                controller.login(tag, password);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.add(loginButton, new GridBagConstraints(
                0, 4, 2, 1,
                0.0, 0.0,
                GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                new Insets(6, 6, 6, 6),
                0, 0
        ));
    }
}