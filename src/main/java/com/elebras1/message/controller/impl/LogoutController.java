package com.elebras1.message.controller.impl;

import com.elebras1.message.controller.ILogoutController;
import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.datamodel.User;

public class LogoutController implements ILogoutController {
    private final ISession session;
    private final DataManager dataManager;

    public LogoutController(ISession session, DataManager dataManager) {
        this.session = session;
        this.dataManager = dataManager;
    }

    @Override
    public void logout() {
        User user = this.session.getConnectedUser();
        user.setOnline(false);
        this.dataManager.sendUser(user);
        this.session.disconnect();
    }
}
