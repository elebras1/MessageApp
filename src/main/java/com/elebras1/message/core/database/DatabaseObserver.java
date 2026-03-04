package com.elebras1.message.core.database;

import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;

public class DatabaseObserver implements IDatabaseObserver {
    @Override
    public void notifyMessageAdded(Message addedMessage) {
        System.out.println("notifyMessageAdded: " + addedMessage);
    }

    @Override
    public void notifyMessageDeleted(Message deletedMessage) {
        System.out.println("notifyMessageDeleted: " + deletedMessage);
    }

    @Override
    public void notifyMessageModified(Message modifiedMessage) {
        System.out.println("notifyMessageModified: " + modifiedMessage);
    }

    @Override
    public void notifyUserAdded(User addedUser) {
        System.out.println("notifyUserAdded: " + addedUser);
    }

    @Override
    public void notifyUserDeleted(User deletedUser) {
        System.out.println("notifyUserDeleted: " + deletedUser);
    }

    @Override
    public void notifyUserModified(User modifiedUser) {
        System.out.println("notifyUserModified: " + modifiedUser);
    }

    @Override
    public void notifyChannelAdded(Channel addedChannel) {
        System.out.println("notifyChannelAdded: " + addedChannel);
    }

    @Override
    public void notifyChannelDeleted(Channel deletedChannel) {
        System.out.println("notifyChannelDeleted: " + deletedChannel);
    }

    @Override
    public void notifyChannelModified(Channel modifiedChannel) {
        System.out.println("notifyChannelModified: " + modifiedChannel);
    }
}
