package com.elebras1.message.ihm.view.swing;

import com.elebras1.message.ihm.view.View;
import com.elebras1.message.ihm.view.callback.OnCreateChannelCallback;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateChannelView extends JDialog implements View {

    private static final Color BG_COLOR = new Color(0xD06565);
    private static final Color PANEL_COLOR = new Color(0x3C3C3C);
    private static final Color ACCENT_COLOR = new Color(0xFDB92E);
    private static final Color TEXT_COLOR = new Color(0xEEEEEE);
    private static final Color ADD_COLOR = new Color(0x2ECC71);

    public CreateChannelView(Component parent, OnCreateChannelCallback onChannelCreated) {
        super(SwingUtilities.getWindowAncestor(parent), "Créer un channel", ModalityType.APPLICATION_MODAL);
        setSize(360, 240);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(BG_COLOR);

        JPanel root = new JPanel(new BorderLayout(0, 12));
        root.setBackground(BG_COLOR);
        root.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Nouveau channel");
        title.setForeground(ACCENT_COLOR);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 15f));
        root.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(BG_COLOR);

        JLabel nameLabel = new JLabel("Nom du channel :");
        nameLabel.setForeground(TEXT_COLOR);
        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
        center.add(nameLabel);
        center.add(Box.createVerticalStrut(4));

        JTextField nameField = new JTextField();
        nameField.setBackground(PANEL_COLOR);
        nameField.setForeground(TEXT_COLOR);
        nameField.setCaretColor(TEXT_COLOR);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        nameField.setAlignmentX(LEFT_ALIGNMENT);
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_COLOR, 1),
                new EmptyBorder(4, 8, 4, 8)));
        center.add(nameField);
        center.add(Box.createVerticalStrut(10));

        JCheckBox privateCheck = new JCheckBox("Channel privé", true);
        privateCheck.setBackground(BG_COLOR);
        privateCheck.setForeground(TEXT_COLOR);
        privateCheck.setFocusPainted(false);
        privateCheck.setAlignmentX(LEFT_ALIGNMENT);
        center.add(privateCheck);

        root.add(center, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        footer.setBackground(BG_COLOR);

        JButton cancelBtn = styledButton("Annuler", new Color(0x555555), TEXT_COLOR);
        cancelBtn.addActionListener(_ -> dispose());

        JButton createBtn = styledButton("Créer", ADD_COLOR, Color.BLACK);
        createBtn.addActionListener(_ -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                onChannelCreated.onCreate(name, privateCheck.isSelected());
                dispose();
            } else {
                nameField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0xF85E5E), 1),
                        new EmptyBorder(4, 8, 4, 8)));
            }
        });

        nameField.addActionListener(_ -> createBtn.doClick());
        footer.add(cancelBtn);
        footer.add(createBtn);
        root.add(footer, BorderLayout.SOUTH);
        setContentPane(root);

        addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                nameField.requestFocusInWindow();
            }
        });
    }

    private JButton styledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(btn.getFont().deriveFont(Font.BOLD, 12f));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}