package com.elebras1.message.controller;

import com.elebras1.message.core.session.ISession;

public class LogoutController implements ILogoutController {
    private final ISession session;

    public LogoutController(ISession session) {
        this.session = session;
    }

    @Override
    public void logout() {
        this.session.disconnect();
    }
}
