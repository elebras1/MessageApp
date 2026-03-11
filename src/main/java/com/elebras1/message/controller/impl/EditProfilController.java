package com.elebras1.message.controller.impl;

import com.elebras1.message.controller.IEditProfilController;
import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.datamodel.User;

public class EditProfilController implements IEditProfilController {
    private final DataManager dataManager;
    private final ISession session;

    public EditProfilController(DataManager dataManager, ISession session) {
        this.dataManager = dataManager;
        this.session = session;
    }

    @Override
    public void editProfil(String name) {
        User connectedUser = this.session.getConnectedUser();
        connectedUser.setName(name);
        dataManager.sendUser(connectedUser);
    }
}
