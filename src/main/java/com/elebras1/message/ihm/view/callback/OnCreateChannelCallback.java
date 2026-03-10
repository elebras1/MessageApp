package com.elebras1.message.ihm.view.callback;

@FunctionalInterface
public interface OnCreateChannelCallback {
    void onCreate(String name, boolean isPrivate);
}
