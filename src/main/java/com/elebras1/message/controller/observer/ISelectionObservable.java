package com.elebras1.message.controller.observer;

public interface ISelectionObservable {
    void addSelectionObserver(ISelectionObserver observer);
    void removeSelectionObserver(ISelectionObserver observer);
    void notifyRecipientSelected(java.util.UUID recipientUuid);
}

