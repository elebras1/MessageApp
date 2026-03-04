package com.elebras1.message.controller;

public interface IChatObservable {
    void addChatObserver(IChatObserver observer);
    void removeChatObserver(IChatObserver observer);
    void notifyRecipientSelected(java.util.UUID recipientUuid);
}

