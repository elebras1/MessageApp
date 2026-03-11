package com.elebras1.message.factory;

import com.elebras1.message.ihm.view.*;
import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;
import com.elebras1.message.ihm.view.javafx.*;

import java.util.UUID;

public class JavaFxViewFactory implements ViewFactory {

    @Override
    public IChatView createChatView() {
        return new FxChatView();
    }

    @Override
    public IMessagesView createMessagesView() {
        return new FxMessagesView();
    }

    @Override
    public IUsersView createUsersView() {
        return new FxUsersView();
    }

    @Override
    public IChannelsView createChannelsView() {
        return new FxChannelsView();
    }

    @Override
    public IChannelView createChannelView(UUID id, String channelName, boolean isCreator,
                                          boolean isPrivate, OnClickUuidCallback onRemove,
                                          OnClickUuidCallback onAddMember) {
        return new FxChannelView(id, channelName, isCreator, isPrivate, onRemove, onAddMember);
    }

    @Override
    public IMessageView createMessageView(UUID id, String content, String metadata, boolean isMine) {
        return new FxMessageView(id, content, metadata, isMine);
    }

    @Override
    public IUserView createUserView(UUID id, String displayName, boolean online) {
        return new FxUserView(id, displayName, online);
    }
}
