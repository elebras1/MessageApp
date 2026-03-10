package com.elebras1.message.controller;

import com.elebras1.message.ihm.view.EditProfilView;
import com.elebras1.message.ihm.view.LoginView;
import com.elebras1.message.ihm.view.MessageAppMainView;
import com.elebras1.message.ihm.view.SubscribeView;

public class NavBarController implements INavBarController {
    private final MessageAppMainView mainView;
    private final SubscribeView subscribeView;
    private final LoginView loginView;
    private final EditProfilView editProfilView;
    private final ILogoutController logoutController;
    private final IRemoveUserController removeUserController;

    public NavBarController(MessageAppMainView mainView, SubscribeView subscribeView, LoginView loginView, EditProfilView editProfilView, ILogoutController logoutController, IRemoveUserController removeUserController) {
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
