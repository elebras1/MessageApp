package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.IChannelView;
import com.elebras1.message.ihm.view.IChannelsView;
import com.elebras1.message.ihm.view.callback.OnCreateChannelCallback;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FxChannelsView extends BorderPane implements IChannelsView {

    private final FxListElementView channelsList;
    private final Button addChannelButton;
    private final FxSearchBarView searchBar;
    private final List<FxChannelView> allChannels = new ArrayList<>();

    public FxChannelsView() {
        channelsList = new FxListElementView();
        searchBar = new FxSearchBarView("Rechercher un channel...");

        addChannelButton = new Button("+ Nouveau channel");
        addChannelButton.setStyle(
            "-fx-background-color: #FDB92E;" +
            "-fx-text-fill: #222222;" +
            "-fx-background-radius: 4;" +
            "-fx-border-color: transparent;" +
            "-fx-max-width: infinity;"
        );
        addChannelButton.setMaxWidth(Double.MAX_VALUE);

        VBox top = new VBox(4, addChannelButton, searchBar);
        top.setPadding(new Insets(4));

        setTop(top);
        setCenter(channelsList);

        searchBar.setOnSearchChanged(this::filterChannels);
    }

    private void filterChannels(String query) {
        channelsList.clearContent();
        String lq = query.toLowerCase().trim();
        for (FxChannelView ch : allChannels) {
            if (lq.isEmpty() || ch.getChannelName().toLowerCase().contains(lq)) {
                channelsList.addContent(ch);
            }
        }
    }

    @Override
    public void setOnAddChannelAction(OnCreateChannelCallback onChannelCreated) {
        addChannelButton.setOnAction(e -> new FxCreateChannelView(this, onChannelCreated).show());
    }

    @Override
    public void showMembersDialog(String channelName, List<User> members, List<User> candidates,
                                  Consumer<User> onAddMember, Consumer<User> onRemoveMember) {
        new FxChannelMembersView(this, channelName, members, candidates, onAddMember, onRemoveMember).show();
    }

    @Override
    public void clearChannels() {
        allChannels.clear();
        channelsList.clearContent();
        searchBar.clear();
    }

    @Override
    public void addChannel(IChannelView channelView) {
        FxChannelView fxChannelView = (FxChannelView) channelView;
        allChannels.add(fxChannelView);
        String query = searchBar.getText();
        if (query.isEmpty() || fxChannelView.getChannelName().toLowerCase().contains(query.toLowerCase())) {
            channelsList.addContent(fxChannelView);
        }
    }
}

