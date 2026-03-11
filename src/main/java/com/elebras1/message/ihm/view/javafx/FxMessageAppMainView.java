package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.controller.IMessageAppMainController;
import com.elebras1.message.ihm.view.IMessageAppMainView;
import com.elebras1.message.ihm.view.View;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

/**
 * Fenêtre principale JavaFX.
 * Remplace MessageAppMainView (Swing) en implémentant la même logique
 * mais via des nœuds JavaFX.
 */
public class FxMessageAppMainView extends Stage implements IMessageAppMainView {

    private final IMessageAppMainController messageAppMainController;
    private final BorderPane root;
    private Node currentPanel;

    public FxMessageAppMainView(IMessageAppMainController messageAppMainController) {
        this.messageAppMainController = messageAppMainController;
        this.currentPanel = null;

        setTitle("Message App");
        try {
            getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/images/logo_20.png"))));
        } catch (Exception ignored) {}

        root = new BorderPane();
        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        Scene scene = new Scene(root, 800, 600);
        setScene(scene);
        setOnCloseRequest(e -> Platform.exit());
    }

    public void setNavbarView(FxUserToolBarView toolBarView) {
        root.setBottom(toolBarView);
    }

    public void addFxPanel(Node panel) {
        addPanel((View) panel);
    }

    public void removeFxCurrentPanel() {
        removeCurrentPanel();
    }

    @Override
    public void addPanel(View panel) {
        if (currentPanel != null) {
            root.setCenter(null);
        }
        root.setCenter((Node) panel);
        currentPanel = (Node) panel;
    }

    @Override
    public void removeCurrentPanel() {
        if (currentPanel != null) {
            root.setCenter(null);
            currentPanel = null;
        }
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> Platform.exit());

        MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Sélectionner le répertoire d'échanges");
            File dir = chooser.showDialog(this);
            if (dir != null) {
                messageAppMainController.selectExchangeDirectory(dir.getAbsolutePath());
            }
        });

        fileMenu.getItems().addAll(exitItem, openItem);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> showAboutDialog());
        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);
        return menuBar;
    }

    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Message App v1.0");
        alert.setContentText("UBO M2-TIIL\nDépartement Informatique");
        alert.showAndWait();
    }
}

