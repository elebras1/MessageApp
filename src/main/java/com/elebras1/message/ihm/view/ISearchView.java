package com.elebras1.message.ihm.view;

import java.util.function.Consumer;

public interface ISearchView extends View {

    void setOnSearchChanged(Consumer<String> onSearchChanged);

    void clear();

    String getText();
}
