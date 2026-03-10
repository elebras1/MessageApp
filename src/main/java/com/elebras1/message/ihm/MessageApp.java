package com.elebras1.message.ihm;

import java.io.File;

import com.elebras1.message.controller.*;
import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.session.ISessionObserver;
import com.elebras1.message.core.session.Session;
import com.elebras1.message.ihm.view.*;

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
	private SubscribeView subscribeView;
	private LoginView loginView;
	private NavbarView navbarView;
	private INavBarController navBarController;
	private ISubscribeController subscribeController;
	private ILoginController loginController;
	private IMessageAppMainController messageAppMainController;
	private MessagesController listMessageController;

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
		this.messageAppMainController = new MessageAppMainController(this.mDataManager);
		this.mMainView = new MessageAppMainView(this.messageAppMainController);
		this.subscribeController = new SubscribeController(this.mDataManager);
		this.loginController = new LoginController(this.mDataManager, this.session);
		this.subscribeView = new SubscribeView(this.subscribeController);
		this.loginView = new LoginView(this.loginController);
		LogoutController logoutController = new LogoutController(this.session);
		this.navBarController = new NavBarController(this.mMainView, this.subscribeView, this.loginView, logoutController);
		this.navbarView = new NavbarView(this.navBarController);
		this.mMainView.setNavbarView(this.navbarView);

		ChatView chatView = new ChatView();
		ISessionObserver chatController = new ChatController(chatView, this.mDataManager, this.session, this.mMainView);
		this.session.addObserver(chatController);
		this.session.addObserver(navbarView);
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
