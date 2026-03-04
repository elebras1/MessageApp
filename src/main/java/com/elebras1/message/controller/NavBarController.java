package com.elebras1.message.controller;

import com.elebras1.message.ihm.view.LoginView;
import com.elebras1.message.ihm.view.MessageAppMainView;
import com.elebras1.message.ihm.view.SubscribeView;

public class NavBarController implements INavBarController {
    private final MessageAppMainView mainView;
    private final SubscribeView subscribeView;
    private final LoginView loginView;
    private final ILogoutController logoutController;

    public NavBarController(MessageAppMainView mainView, SubscribeView subscribeView, LoginView loginView, ILogoutController logoutController) {
        this.mainView = mainView;
        this.subscribeView = subscribeView;
        this.loginView = loginView;
        this.logoutController = logoutController;
    }

    @Override
    public void showLoginView() {
        this.mainView.addPanel(this.loginView);
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
