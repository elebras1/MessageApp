package com.elebras1.message.controller.observer;

import java.util.UUID;

public interface ISelectionObserver {
    void onRecipientSelected(UUID recipientUuid);
}

