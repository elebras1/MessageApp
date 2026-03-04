package com.elebras1.message.ihm.observer;

import java.util.UUID;

public interface IChatObserver {
    void notifySelectedChat(UUID channelUuid);
}
