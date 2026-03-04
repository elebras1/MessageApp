package com.elebras1.message;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.database.Database;
import com.elebras1.message.core.database.DatabaseObserver;
import com.elebras1.message.core.database.DbConnector;
import com.elebras1.message.core.database.EntityManager;
import com.elebras1.message.core.session.Session;
import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.MessageApp;
import com.elebras1.mock.MessageAppMock;

import java.util.List;

/**
 * Classe de lancement de l'application.
 *
 * @author S.Lucas
 */
public class MessageAppLauncher {

	/**
	 * Indique si le mode bouchoné est activé.
	 */
	protected static boolean IS_MOCK_ENABLED = true;

	/**
	 * Launcher.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		Database database = new Database();

		EntityManager entityManager = new EntityManager(database);
		entityManager.setExchangeDirectory("C:/Users/erwan/Documents/dev/");

		DataManager dataManager = new DataManager(database, entityManager);

		DatabaseObserver observer = new DatabaseObserver();

		dataManager.addObserver(observer);

		DbConnector dbConnector = new DbConnector(database);

		if (IS_MOCK_ENABLED) {
			MessageAppMock mock = new MessageAppMock(dbConnector, dataManager);
			mock.showGUI();
		}

		Session session = new Session();

		mockData(dbConnector);

		MessageApp messageApp = new MessageApp(dataManager, session);
		messageApp.init();
		messageApp.show();

	}

	private static void mockData(DbConnector dbConnector) {
		User alice = new User("alice", "pass123", "Alice Martin");
		User bob = new User("bob", "pass456", "Bob Dupont");
		User charlie = new User("charlie", "pass789", "Charlie Durand");
		User diana = new User("diana", "passabc", "Diana Leroy");
		User user = new User("user", "user", "User Test");

		dbConnector.addUser(alice);
		dbConnector.addUser(bob);
		dbConnector.addUser(charlie);
		dbConnector.addUser(diana);
		dbConnector.addUser(user);

		dbConnector.addMessage(new Message(alice, bob.getUuid(), "Salut Bob, t'es dispo ce soir ?"));
		dbConnector.addMessage(new Message(bob, alice.getUuid(), "Oui ! On fait quoi ?"));
		dbConnector.addMessage(new Message(alice, bob.getUuid(), "On pourrait regarder un film ?"));
		dbConnector.addMessage(new Message(bob, alice.getUuid(), "Bonne idée, lequel ?"));
		dbConnector.addMessage(new Message(alice, bob.getUuid(), "Surprise 😄"));

		dbConnector.addMessage(new Message(charlie, diana.getUuid(),"Diana, le rapport est prêt pour demain ?"));
		dbConnector.addMessage(new Message(diana, charlie.getUuid(), "Presque, il me reste la conclusion."));
		dbConnector.addMessage(new Message(charlie, diana.getUuid(), "OK, envoie-le moi avant 17h."));
		dbConnector.addMessage(new Message(diana,charlie.getUuid(), "Pas de souci, c'est noté."));

		dbConnector.addMessage(new Message(alice, charlie.getUuid(), "Charlie, réunion déplacée à 16h."));

		Channel general = new Channel(alice, "Général");
		Channel dev = new Channel(bob, "Développement");
		dbConnector.addChannel(general);
		dbConnector.addChannel(dev);

		dbConnector.addMessage(new Message(alice, general.getUuid(), "Bienvenue sur le channel Général !"));
		dbConnector.addMessage(new Message(bob, general.getUuid(), "Bonjour tout le monde 👋"));
		dbConnector.addMessage(new Message(charlie, general.getUuid(), "Salut la team !"));
		dbConnector.addMessage(new Message(diana, general.getUuid(), "Hello !"));

		dbConnector.addMessage(new Message(bob, dev.getUuid(), "Quelqu'un a regardé le bug #42 ?"));
		dbConnector.addMessage(new Message(charlie, dev.getUuid(), "Oui, c'est un NPE dans le DataManager."));
		dbConnector.addMessage(new Message(bob, dev.getUuid(), "J'ouvre une PR ce soir."));

		Channel projetAlpha = new Channel(alice, "Projet Alpha", List.of(alice, bob, charlie));
		Channel equipeRH    = new Channel(diana, "Équipe RH", List.of(diana, alice));
		dbConnector.addChannel(projetAlpha);
		dbConnector.addChannel(equipeRH);

		dbConnector.addMessage(new Message(alice, projetAlpha.getUuid(), "Kickoff du projet lundi 9h !"));
		dbConnector.addMessage(new Message(bob, projetAlpha.getUuid(), "Je serai là."));
		dbConnector.addMessage(new Message(charlie, projetAlpha.getUuid(), "Pareil, j'apporte les slides."));

		dbConnector.addMessage(new Message(diana, equipeRH.getUuid(), "Entretien de Bob planifié vendredi."));
		dbConnector.addMessage(new Message(alice, equipeRH.getUuid(), "Reçu, je bloque le créneau."));

		Channel annonces = new Channel(alice, "Annonces");
		dbConnector.addChannel(annonces);
	}
}
