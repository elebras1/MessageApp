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
import com.elebras1.message.ihm.MessageAppJavaFx;
import com.elebras1.mock.MessageAppMock;
import javafx.application.Application;

import java.io.File;
import java.util.List;

public class MessageAppJavaFxLauncher {

    protected static boolean IS_MOCK_ENABLED = true;

    private static final String EXCHANGE_DIR = "C:/Users/erwan/Documents/dev/repertoire_echanges";

    public static void main(String[] args) {
        Database database = new Database();
        EntityManager entityManager = new EntityManager(database);
        DbConnector dbConnector = new DbConnector(database);

        DataManager dataManager = new DataManager(database, entityManager);
        dataManager.setExchangeDirectory(EXCHANGE_DIR);

        DatabaseObserver observer = new DatabaseObserver();
        dataManager.addObserver(observer);

        if (IS_MOCK_ENABLED) {
            MessageAppMock mock = new MessageAppMock(dbConnector, dataManager);
            mock.showGUI();
        }

        if (isExchangeDirectoryEmpty(EXCHANGE_DIR)) {
            System.out.println("Répertoire d'échange vide. Injection des données de test...");
            mockData(dataManager);
        } else {
            System.out.println("Données existantes détectées dans le répertoire. Pas d'injection de mockData.");
        }

        Session session = new Session();

        MessageAppJavaFx.setup(dataManager, session);
        Application.launch(MessageAppJavaFx.class, args);
    }

    private static boolean isExchangeDirectoryEmpty(String path) {
        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) return true;
        String[] files = directory.list();
        return (files == null || files.length == 0);
    }

    private static void mockData(DataManager dataManager) {
        User alice   = new User("alice",   "alice",   "Alice Martin");
        User bob     = new User("bob",     "pass456", "Bob Dupont");
        User charlie = new User("charlie", "pass789", "Charlie Durand");
        User diana   = new User("diana",   "passabc", "Diana Leroy");
        User user    = new User("user",    "user",    "User Test");

        if (dataManager.getUsers().contains(alice) || dataManager.getUsers().contains(bob)) {
            System.out.println("Les utilisateurs de test existent déjà en base, arrêt de l'injection.");
            return;
        }

        dataManager.sendUser(alice);
        dataManager.sendUser(bob);
        dataManager.sendUser(charlie);
        dataManager.sendUser(diana);
        dataManager.sendUser(user);

        dataManager.sendMessage(new Message(alice, bob.getUuid(), "Salut Bob, t'es dispo ce soir ?"));
        dataManager.sendMessage(new Message(bob, alice.getUuid(), "Oui ! On fait quoi ?"));

        Channel general = new Channel(alice, "Général", false);
        Channel dev     = new Channel(bob,   "Développement", false);
        dataManager.sendChannel(general);
        dataManager.sendChannel(dev);

        dataManager.sendMessage(new Message(alice, general.getUuid(), "Bienvenue sur le channel Général !"));
        dataManager.sendMessage(new Message(bob,   general.getUuid(), "Bonjour tout le monde 👋"));

        Channel projetAlpha = new Channel(alice, "Projet Alpha", List.of(alice, bob, charlie));
        dataManager.sendChannel(projetAlpha);
    }
}

