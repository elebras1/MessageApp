package com.elebras1.message.ihm.view;

import com.elebras1.message.datamodel.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class ChannelMembersView extends JDialog {

    private static final Color BG_COLOR = new Color(0xD06565);
    private static final Color PANEL_COLOR = new Color(0x3C3C3C);
    private static final Color ACCENT_COLOR = new Color(0xFDB92E);
    private static final Color ADD_COLOR  = new Color(0x2ECC71);
    private static final Color REMOVE_COLOR = new Color(0xF85E5E);
    private static final Color TEXT_COLOR = new Color(0xEEEEEE);
    private static final Color BUBBLE_COLOR = new Color(0x71FFFF);
    private static final Color SEPARATOR_COLOR = new Color(0x555555);

    public ChannelMembersView(Component parent, String channelName, List<User> members, List<User> candidates, Consumer<User> onAddMember, Consumer<User> onRemoveMember) {
        super(SwingUtilities.getWindowAncestor(parent), "Gérer les membres — " + channelName, ModalityType.APPLICATION_MODAL);
        setSize(420, 520);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(BG_COLOR);

        JPanel root = new JPanel(new BorderLayout(0, 12));
        root.setBackground(BG_COLOR);
        root.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("# " + channelName);
        title.setForeground(ACCENT_COLOR);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        root.add(title, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(BG_COLOR);

        body.add(sectionLabel("Membres actuels"));
        body.add(Box.createVerticalStrut(6));

        JPanel membersPanel = buildMembersPanel(members, onRemoveMember);
        JScrollPane membersScroll = styledScroll(membersPanel);
        membersScroll.setPreferredSize(new Dimension(380, 160));
        membersScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        body.add(membersScroll);

        body.add(Box.createVerticalStrut(14));
        body.add(separator());
        body.add(Box.createVerticalStrut(14));

        body.add(sectionLabel("Ajouter un membre"));
        body.add(Box.createVerticalStrut(6));

        if (candidates.isEmpty()) {
            JLabel noCandidate = new JLabel("Tous les utilisateurs sont déjà membres.");
            noCandidate.setForeground(new Color(0xAAAAAA));
            noCandidate.setFont(noCandidate.getFont().deriveFont(Font.ITALIC, 12f));
            noCandidate.setAlignmentX(LEFT_ALIGNMENT);
            body.add(noCandidate);
        } else {
            JPanel addPanel = buildAddPanel(candidates, onAddMember);
            body.add(addPanel);
        }

        root.add(body, BorderLayout.CENTER);

        JButton closeBtn = styledButton("Fermer", ACCENT_COLOR, Color.BLACK);
        closeBtn.addActionListener(_ -> dispose());
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        footer.setBackground(BG_COLOR);
        footer.add(closeBtn);
        root.add(footer, BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JPanel buildMembersPanel(List<User> members, Consumer<User> onRemove) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PANEL_COLOR);
        panel.setBorder(new EmptyBorder(8, 8, 8, 8));

        for (User member : members) {
            JPanel row = new JPanel(new BorderLayout(8, 0));
            row.setBackground(PANEL_COLOR);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

            JLabel nameLabel = new JLabel(member.getName() + " (@" + member.getUserTag() + ")");
            nameLabel.setForeground(BUBBLE_COLOR);
            nameLabel.setFont(nameLabel.getFont().deriveFont(13f));
            row.add(nameLabel, BorderLayout.CENTER);

            JButton removeBtn = styledButton("−", REMOVE_COLOR, Color.WHITE);
            removeBtn.setToolTipText("Retirer du channel");
            removeBtn.addActionListener(_ -> {
                onRemove.accept(member);
                dispose();
            });
            row.add(removeBtn, BorderLayout.EAST);

            panel.add(row);
            panel.add(Box.createVerticalStrut(4));
        }
        return panel;
    }

    private JPanel buildAddPanel(List<User> candidates, Consumer<User> onAdd) {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.setBackground(BG_COLOR);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        panel.setAlignmentX(LEFT_ALIGNMENT);

        JComboBox<String> combo = new JComboBox<>();
        combo.setBackground(PANEL_COLOR);
        combo.setForeground(TEXT_COLOR);
        combo.setFont(combo.getFont().deriveFont(13f));
        for (User u : candidates) {
            combo.addItem(u.getName() + " (@" + u.getUserTag() + ")");
        }
        panel.add(combo, BorderLayout.CENTER);

        JButton addBtn = styledButton("+ Ajouter", ADD_COLOR, Color.BLACK);
        addBtn.addActionListener(_ -> {
            int idx = combo.getSelectedIndex();
            if (idx >= 0) {
                onAdd.accept(candidates.get(idx));
                dispose();
            }
        });
        panel.add(addBtn, BorderLayout.EAST);

        return panel;
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel(text.toUpperCase());
        label.setForeground(new Color(0xAAAAAA));
        label.setFont(label.getFont().deriveFont(Font.BOLD, 11f));
        label.setAlignmentX(LEFT_ALIGNMENT);
        return label;
    }

    private JSeparator separator() {
        JSeparator sep = new JSeparator();
        sep.setForeground(SEPARATOR_COLOR);
        sep.setBackground(SEPARATOR_COLOR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    private JScrollPane styledScroll(JPanel content) {
        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(BorderFactory.createLineBorder(SEPARATOR_COLOR));
        scroll.getViewport().setBackground(PANEL_COLOR);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setAlignmentX(LEFT_ALIGNMENT);
        return scroll;
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

