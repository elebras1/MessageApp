package com.elebras1.message.ihm;

import java.io.File;

import com.elebras1.message.controller.*;
import com.elebras1.message.controller.impl.*;
import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.session.Session;
import com.elebras1.message.factory.SwingViewFactory;
import com.elebras1.message.factory.ViewFactory;
import com.elebras1.message.ihm.view.swing.*;

import javax.swing.*;

/**
 * Classe principale l'application.
 *
 * @author S.Lucas
 */
public class MessageApp {
	/**
	 * Base de données.
	 */
	private final DataManager mDataManager;

	private final Session session;
	/**
	 * Vue principale de l'application.
	 */
	private MessageAppMainView mMainView;

	/**
	 * Constructeur.
	 *
	 * @param dataManager
	 */
	public MessageApp(DataManager dataManager, Session session) {
		this.mDataManager = dataManager;
		this.session = session;
	}

	/**
	 * Initialisation de l'application.
	 */
	public void init() {
		// Init du look and feel de l'application
		this.initLookAndFeel();

		// Initialisation de l'IHM
		this.initGui();

		// Initialisation du répertoire d'échange
		this.initDirectory();
	}

	/**
	 * Initialisation du look and feel de l'application.
	 */
	protected void initLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialisation de l'interface graphique.
	 */
	protected void initGui() {
		IMessageAppMainController messageAppMainController = new MessageAppMainController(this.mDataManager);
		this.mMainView = new MessageAppMainView(messageAppMainController);
		ISubscribeController subscribeController = new SubscribeController(this.mDataManager);
		ILoginController loginController = new LoginController(this.mDataManager, this.session);
		SubscribeView subscribeView = new SubscribeView(subscribeController);
		LoginView loginView = new LoginView(loginController);
		EditProfilController editProfilController = new EditProfilController(this.mDataManager, this.session);
		EditProfilView editProfilView = new EditProfilView(editProfilController);
		RemoveUserController removeUserController = new RemoveUserController(this.session, this.mDataManager);
		LogoutController logoutController = new LogoutController(this.session, this.mDataManager);
		INavBarController navBarController = new UserToolBarController(this.mMainView, subscribeView, loginView, editProfilView, logoutController, removeUserController);
		UserToolBarView userToolBarView = new UserToolBarView(navBarController);
		this.mMainView.setNavbarView(userToolBarView);

		ChatView chatView = new ChatView();
		ViewFactory viewFactory = new SwingViewFactory();
		ChatController chatController = new ChatController(chatView, this.mDataManager, this.session, this.mMainView, viewFactory);
		this.session.addObserver(chatController);
		this.session.addObserver(userToolBarView);
	}

	/**
	 * Initialisation du répertoire d'échange (depuis la conf ou depuis un file
	 * chooser). <br/>
	 * <b>Le chemin doit obligatoirement avoir été saisi et être valide avant de
	 * pouvoir utiliser l'application</b>
	 */
	protected void initDirectory() {
	}

	/**
	 * Indique si le fichier donné est valide pour servir de répertoire d'échange
	 *
	 * @param directory , Répertoire à tester.
	 */
	protected boolean isValidExchangeDirectory(File directory) {
		// Valide si répertoire disponible en lecture et écriture
		return directory != null && directory.exists() && directory.isDirectory() && directory.canRead()
				&& directory.canWrite();
	}

	/**
	 * Initialisation du répertoire d'échange.
	 *
	 * @param directoryPath
	 */
	protected void initDirectory(String directoryPath) {
		mDataManager.setExchangeDirectory(directoryPath);
	}

	public void show() {
		this.mMainView.setVisible(true);
	}
}
