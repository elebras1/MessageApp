package com.elebras1.message.ihm;

import com.elebras1.message.common.FxUiDispatcher;
import com.elebras1.message.controller.*;
import com.elebras1.message.controller.impl.*;
import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.session.Session;
import com.elebras1.message.factory.JavaFxViewFactory;
import com.elebras1.message.factory.ViewFactory;
import com.elebras1.message.ihm.view.IChatView;
import com.elebras1.message.ihm.view.javafx.*;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public class MessageAppJavaFx extends Application {

    private static DataManager dataManager;
    private static Session session;

    public static void setup(DataManager dm, Session s) {
        dataManager = dm;
        session = s;
    }

    @Override
    public void start(Stage primaryStage) {
        IMessageAppMainController messageAppMainController = new MessageAppMainController(dataManager);

        FxMessageAppMainView mainView = new FxMessageAppMainView(messageAppMainController);

        ISubscribeController subscribeController = new SubscribeController(dataManager);
        ILoginController loginController = new LoginController(dataManager, session);
        IEditProfilController editProfilController = new EditProfilController(dataManager, session);

        FxSubscribeView subscribeView = new FxSubscribeView(subscribeController);
        FxLoginView loginView = new FxLoginView(loginController);
        FxEditProfilView editProfilView = new FxEditProfilView(editProfilController);

        IRemoveUserController removeUserController = new RemoveUserController(session, dataManager);
        ILogoutController logoutController = new LogoutController(session, dataManager);

        INavBarController navBarController = new UserToolBarController(
                mainView, subscribeView, loginView, editProfilView,
                logoutController, removeUserController);

        FxUserToolBarView userToolBarView = new FxUserToolBarView(navBarController);
        mainView.setNavbarView(userToolBarView);

        ViewFactory viewFactory = new JavaFxViewFactory();
        IChatView chatView = viewFactory.createChatView();
        ChatController chatController = new ChatController(chatView, dataManager, session, mainView, viewFactory, new FxUiDispatcher());

        session.addObserver(chatController);
        session.addObserver(userToolBarView);

        NotifierController notifierController = new NotifierController(session);
        dataManager.addObserver(notifierController);

        mainView.show();
    }

    protected boolean isValidExchangeDirectory(File directory) {
        return directory != null && directory.exists()
                && directory.isDirectory()
                && directory.canRead() && directory.canWrite();
    }
}

