package com.elebras1.message.controller;

import java.util.UUID;

public interface IMessagesController {
    void loadMessagesByRecipientUuid(UUID recipientUuid);
    void sendMessage(String text);
    void onDeleteMessage(UUID messageUuid);
}
