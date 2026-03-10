package com.elebras1.message.controller;

public interface ISelectionObservable {
    void addSelectionObserver(ISelectionObserver observer);
    void removeSelectionObserver(ISelectionObserver observer);
    void notifyRecipientSelected(java.util.UUID recipientUuid);
}

