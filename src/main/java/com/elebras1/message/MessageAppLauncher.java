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

		DbConnector dbConnector = new DbConnector(database);

		DataManager dataManager = new DataManager(database, entityManager);
		dataManager.setExchangeDirectory("C:/Users/erwan/Documents/dev/repertoire_echanges");

		DatabaseObserver observer = new DatabaseObserver();

		dataManager.addObserver(observer);


		if (IS_MOCK_ENABLED) {
			MessageAppMock mock = new MessageAppMock(dbConnector, dataManager);
			mock.showGUI();
		}

		Session session = new Session();
		Session session2 = new Session();

		mockData(dataManager);

		MessageApp messageApp = new MessageApp(dataManager, session);
		messageApp.init();
		messageApp.show();

		MessageApp messageApp2 = new MessageApp(dataManager, session2);
		messageApp2.init();
		messageApp2.show();

	}

	private static void mockData(DataManager dataManager) {
		User alice = new User("alice", "alice", "Alice Martin");
		User bob = new User("bob", "pass456", "Bob Dupont");
		User charlie = new User("charlie", "pass789", "Charlie Durand");
		User diana = new User("diana", "passabc", "Diana Leroy");
		User user = new User("user", "user", "User Test");
		if(dataManager.getUsers().contains(alice) || dataManager.getUsers().contains(bob) || dataManager.getUsers().contains(charlie) || dataManager.getUsers().contains(diana) || dataManager.getUsers().contains(user)) {
			System.out.println("Les utilisateurs de test existent déjà, pas de doublons créés.");
			return;
		}

		dataManager.sendUser(alice);
		dataManager.sendUser(bob);
		dataManager.sendUser(charlie);
		dataManager.sendUser(diana);
		dataManager.sendUser(user);

		dataManager.sendMessage(new Message(alice, bob.getUuid(), "Salut Bob, t'es dispo ce soir ?"));
		dataManager.sendMessage(new Message(bob, alice.getUuid(), "Oui ! On fait quoi ?"));
		dataManager.sendMessage(new Message(alice, bob.getUuid(), "On pourrait regarder un film ?"));
		dataManager.sendMessage(new Message(bob, alice.getUuid(), "Bonne idée, lequel ?"));
		dataManager.sendMessage(new Message(alice, bob.getUuid(), "Surprise 😄"));

		dataManager.sendMessage(new Message(charlie, diana.getUuid(),"Diana, le rapport est prêt pour demain ?"));
		dataManager.sendMessage(new Message(diana, charlie.getUuid(), "Presque, il me reste la conclusion."));
		dataManager.sendMessage(new Message(charlie, diana.getUuid(), "OK, envoie-le moi avant 17h."));
		dataManager.sendMessage(new Message(diana,charlie.getUuid(), "Pas de souci, c'est noté."));

		dataManager.sendMessage(new Message(alice, charlie.getUuid(), "Charlie, réunion déplacée à 16h."));

		Channel general = new Channel(alice, "Général");
		Channel dev = new Channel(bob, "Développement");
		dataManager.sendChannel(general);
		dataManager.sendChannel(dev);

		dataManager.sendMessage(new Message(alice, general.getUuid(), "Bienvenue sur le channel Général !"));
		dataManager.sendMessage(new Message(bob, general.getUuid(), "Bonjour tout le monde 👋"));
		dataManager.sendMessage(new Message(charlie, general.getUuid(), "Salut la team !"));
		dataManager.sendMessage(new Message(diana, general.getUuid(), "Hello !"));

		dataManager.sendMessage(new Message(bob, dev.getUuid(), "Quelqu'un a regardé le bug #42 ?"));
		dataManager.sendMessage(new Message(charlie, dev.getUuid(), "Oui, c'est un NPE dans le DataManager."));
		dataManager.sendMessage(new Message(bob, dev.getUuid(), "J'ouvre une PR ce soir."));

		Channel projetAlpha = new Channel(alice, "Projet Alpha", List.of(alice, bob, charlie));
		Channel equipeRH = new Channel(diana, "Équipe RH", List.of(diana, alice));
		dataManager.sendChannel(projetAlpha);
		dataManager.sendChannel(equipeRH);

		dataManager.sendMessage(new Message(alice, projetAlpha.getUuid(), "Kickoff du projet lundi 9h !"));
		dataManager.sendMessage(new Message(bob, projetAlpha.getUuid(), "Je serai là."));
		dataManager.sendMessage(new Message(charlie, projetAlpha.getUuid(), "Pareil, j'apporte les slides."));

		dataManager.sendMessage(new Message(diana, equipeRH.getUuid(), "Entretien de Bob planifié vendredi."));
		dataManager.sendMessage(new Message(alice, equipeRH.getUuid(), "Reçu, je bloque le créneau."));

		Channel annonces = new Channel(alice, "Annonces");
		dataManager.sendChannel(annonces);
	}
}
