package com.elebras1.message.controller;

import com.elebras1.message.datamodel.User;

import java.util.UUID;

public interface IMessagesController {
    void setConnectedUser(User connectedUser);
    void loadMessagesByRecipientUuid(UUID recipientUuid);
    void sendMessage(String text);
}
