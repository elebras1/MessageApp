package com.elebras1.message.ihm.view;

import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.callback.OnCreateChannelCallback;
import com.elebras1.message.ihm.view.swing.ChannelView;

import java.util.List;
import java.util.function.Consumer;

public interface IChannelsView extends View {

    void setOnAddChannelAction(OnCreateChannelCallback onChannelCreated);

    void showMembersDialog(String channelName, List<User> members, List<User> candidates, Consumer<User> onAddMember, Consumer<User> onRemoveMember);

    void clearChannels();

    void addChannel(IChannelView channelView);
}
