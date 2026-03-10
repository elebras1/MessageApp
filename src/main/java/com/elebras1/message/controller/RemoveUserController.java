package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.datamodel.User;

public class RemoveUserController implements IRemoveUserController {
    public final ISession session;
    public final DataManager dataManager;

    public RemoveUserController(ISession session, DataManager dataManager) {
        this.session = session;
        this.dataManager = dataManager;
    }

    @Override
    public void removeUser() {
        User connectedUser = this.session.getConnectedUser();
        this.session.disconnect();
        dataManager.deleteUser(connectedUser);
    }
}
