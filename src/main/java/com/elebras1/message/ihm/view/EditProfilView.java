package com.elebras1.message.ihm.view;

import com.elebras1.message.controller.IEditProfilController;
import com.elebras1.message.controller.ILoginController;

import javax.swing.*;
import java.awt.*;

public class EditProfilView extends JPanel {
    private final JTextField nameField;

    public EditProfilView(IEditProfilController controller) {
        super(new GridBagLayout());
        this.nameField = new JTextField(20);

        this.buildLayout(controller);
    }

    private void buildLayout(IEditProfilController controller) {
        this.add(new JLabel("Name"), new GridBagConstraints(
                0, 2, 1, 1,
                0.0, 0.0,
                GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(6, 6, 6, 6),
                0, 0
        ));

        this.add(this.nameField, new GridBagConstraints(
                1, 2, 1, 1,
                1.0, 0.0,
                GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL,
                new Insets(6, 6, 6, 6),
                0, 0
        ));

        JButton loginButton = new JButton("Edit");
        loginButton.addActionListener(_ -> controller.editProfil(this.nameField.getText().trim()));
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
