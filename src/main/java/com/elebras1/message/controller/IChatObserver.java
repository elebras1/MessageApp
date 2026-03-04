package com.elebras1.message.controller;

import java.util.UUID;

public interface IChatObserver {
    void onRecipientSelected(UUID recipientUuid);
}

