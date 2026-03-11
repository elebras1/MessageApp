package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.ihm.view.ISearchView;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.function.Consumer;

public class FxSearchBarView extends HBox implements ISearchView {

    private final TextField searchField;
    private final String placeholder;

    public FxSearchBarView(String placeholder) {
        this.placeholder = placeholder;
        setSpacing(6);
        setPadding(new Insets(6, 8, 6, 8));

        Label icon = new Label("🔍");
        icon.setStyle("-fx-font-size: 14;");

        searchField = new TextField(placeholder);
        searchField.setStyle(
            "-fx-background-color: #71FFFF;" +
            "-fx-background-radius: 6;" +
            "-fx-border-color: #FFFFFF;" +
            "-fx-border-radius: 6;" +
            "-fx-border-width: 1;" +
            "-fx-text-fill: #444444;" +
            "-fx-padding: 4 8 4 8;"
        );
        searchField.setStyle(searchField.getStyle() + "-fx-prompt-text-fill: #888888;");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        getChildren().addAll(icon, searchField);
    }

    @Override
    public void setOnSearchChanged(Consumer<String> onSearchChanged) {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String val = newVal.equals(placeholder) ? "" : newVal;
            onSearchChanged.accept(val);
        });
    }

    @Override
    public String getText() {
        String t = searchField.getText();
        return t.equals(placeholder) ? "" : t;
    }

    @Override
    public void clear() {
        searchField.clear();
    }
}

