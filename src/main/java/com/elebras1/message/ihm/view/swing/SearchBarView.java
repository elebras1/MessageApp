package com.elebras1.message.ihm.view.swing;

import com.elebras1.message.ihm.view.ISearchView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.function.Consumer;

public class SearchBarView extends JPanel implements ISearchView {

    private final JTextField searchField;
    private final String placeholder;

    public SearchBarView(String placeholder) {
        this.placeholder = placeholder;
        setLayout(new BorderLayout(6, 0));
        setOpaque(false);
        setBorder(new EmptyBorder(6, 8, 6, 8));

        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));

        searchField = new JTextField();
        searchField.setFont(UIManager.getFont("Label.font"));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xFFFFFF), 1, true),
                new EmptyBorder(4, 8, 4, 8)
        ));
        searchField.setBackground(new Color(0x71FFFF));
        searchField.setForeground(Color.DARK_GRAY);

        setPlaceholder(placeholder);

        add(searchIcon, BorderLayout.WEST);
        add(searchField, BorderLayout.CENTER);
    }

    @Override
    public void setOnSearchChanged(Consumer<String> onSearchChanged) {
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            private void notify(DocumentEvent e) {
                String text = searchField.getText();
                onSearchChanged.accept(text.equals(placeholder) ? "" : text);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { notify(e); }

            @Override
            public void removeUpdate(DocumentEvent e) { notify(e); }

            @Override
            public void changedUpdate(DocumentEvent e) { notify(e); }
        });
    }

    @Override
    public String getText() {
        String text = searchField.getText();
        return text.equals(placeholder) ? "" : text;
    }

    @Override
    public void clear() {
        searchField.setText("");
    }

    private void setPlaceholder(String placeholder) {
        searchField.setText(placeholder);
        searchField.setForeground(Color.GRAY);

        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(Color.DARK_GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText(placeholder);
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
    }
}

