package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;

public class MessageAppMainController implements IMessageAppMainController {
    private final DataManager dataManager;

    public MessageAppMainController(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void selectExchangeDirectory(String path) {
        this.dataManager.setExchangeDirectory(path);
    }
}
