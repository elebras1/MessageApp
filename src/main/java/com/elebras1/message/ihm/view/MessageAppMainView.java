package com.elebras1.message.ihm.view;

import com.elebras1.message.controller.IMessageAppMainController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

public class MessageAppMainView extends JFrame {
    private final IMessageAppMainController messageAppMainController;
    private JComponent currentPanel;

    public MessageAppMainView(IMessageAppMainController messageAppMainController) {
        super("Message App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        ImageIcon iconImage = this.loadIcon("/images/logo_20.png");
        this.setIconImage(iconImage.getImage());
        JMenuBar mainMenuBar = this.createMenuBar();
        this.setJMenuBar(mainMenuBar);
        this.messageAppMainController = messageAppMainController;
        this.currentPanel = null;
        this.getContentPane().setLayout(new BorderLayout());
    }

    public void setNavbarView(UserToolBarView userToolBarView) {
        this.getContentPane().add(userToolBarView, BorderLayout.SOUTH);
    }

    public void addPanel(JComponent panel) {
        if (this.currentPanel != null) {
            this.getContentPane().remove(this.currentPanel);
        }
        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.currentPanel = panel;
        this.revalidate();
        this.repaint();
    }

    public void removeCurrentPanel() {
        if (this.currentPanel != null) {
            this.getContentPane().remove(this.currentPanel);
            this.currentPanel = null;
            this.revalidate();
            this.repaint();
        }
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem menuItemNew = new JMenuItem("Exit");
        menuItemNew.setIcon(this.loadIcon("/images/exitIcon_20.png"));
        menuItemNew.addActionListener(_ -> System.exit(0));
        fileMenu.add(menuItemNew);
        JFileChooser fileChooser = this.createFileChooser();
        JMenuItem menuItemOpen = new JMenuItem("Open");
        menuItemOpen.setIcon(this.loadIcon("/images/editIcon_20.png"));
        menuItemOpen.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selected = fileChooser.getSelectedFile();
                this.messageAppMainController.selectExchangeDirectory(selected.getAbsolutePath());
            }
        });
        fileMenu.add(menuItemOpen);
        menuBar.add(fileMenu);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(_ -> this.showAboutMessageDialog());
        helpMenu.add(aboutMenuItem);
        menuBar.add(helpMenu);

        return menuBar;
    }

    private JFileChooser createFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return fileChooser;
    }

    private void showAboutMessageDialog() {
        JTextArea textArea = new JTextArea(" Message App v1.0\n UBO M2-TIIL\n Département Informatique");
        textArea.setEditable(false);
        ImageIcon iconImage = this.loadIcon("/images/logo_50.png");
        JOptionPane.showMessageDialog(this, textArea, "Help", JOptionPane.INFORMATION_MESSAGE, iconImage);
    }

    private ImageIcon loadIcon(String path) {
        return new ImageIcon(Objects.requireNonNull(this.getClass().getResource(path)));
    }
}
