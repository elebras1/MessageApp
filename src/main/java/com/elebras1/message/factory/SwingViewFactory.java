package com.elebras1.message.factory;

import com.elebras1.message.ihm.view.IChannelView;
import com.elebras1.message.ihm.view.IMessageView;
import com.elebras1.message.ihm.view.IUserView;
import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;
import com.elebras1.message.ihm.view.swing.ChannelView;
import com.elebras1.message.ihm.view.swing.MessageView;
import com.elebras1.message.ihm.view.swing.UserView;

import java.util.UUID;

/**
 * Implémentation Swing de {@link ViewFactory}.
 * <p>
 * Crée des composants {@link javax.swing.JPanel} qui implémentent les interfaces de vue,
 * de sorte que les contrôleurs n'ont pas connaissance des classes Swing concrètes.
 * </p>
 */
public class SwingViewFactory implements ViewFactory {

    @Override
    public IChannelView createChannelView(UUID id, String channelName, boolean isCreator, boolean isPrivate,
                                          OnClickUuidCallback onRemove, OnClickUuidCallback onAddMember) {
        return new ChannelView(id, channelName, isCreator, isPrivate, onRemove, onAddMember);
    }

    @Override
    public IMessageView createMessageView(UUID id, String content, String metadata, boolean isMine) {
        return new MessageView(id, content, metadata, isMine);
    }

    @Override
    public IUserView createUserView(UUID id, String displayName, boolean online) {
        return new UserView(id, displayName, online);
    }
}

