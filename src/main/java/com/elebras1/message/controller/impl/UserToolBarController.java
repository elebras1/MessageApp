package com.elebras1.message.controller.impl;

import com.elebras1.message.controller.ILogoutController;
import com.elebras1.message.controller.INavBarController;
import com.elebras1.message.controller.IRemoveUserController;
import com.elebras1.message.ihm.view.IMessageAppMainView;
import com.elebras1.message.ihm.view.View;

public class UserToolBarController implements INavBarController {
    private final IMessageAppMainView mainView;
    private final View subscribeView;
    private final View loginView;
    private final View editProfilView;
    private final ILogoutController logoutController;
    private final IRemoveUserController removeUserController;

    public UserToolBarController(IMessageAppMainView mainView, View subscribeView, View loginView,
                                  View editProfilView, ILogoutController logoutController,
                                  IRemoveUserController removeUserController) {
        this.mainView = mainView;
        this.subscribeView = subscribeView;
        this.loginView = loginView;
        this.logoutController = logoutController;
        this.editProfilView = editProfilView;
        this.removeUserController = removeUserController;
    }

    @Override
    public void showLoginView() {
        this.mainView.addPanel(this.loginView);
    }

    @Override
    public void showEditProfilView() {
        this.mainView.addPanel(this.editProfilView);
    }

    @Override
    public void removeUser() {
        this.removeUserController.removeUser();
    }

    @Override
    public void showSubscribeView() {
        this.mainView.addPanel(this.subscribeView);
    }

    @Override
    public void logout() {
        this.logoutController.logout();
    }
}
