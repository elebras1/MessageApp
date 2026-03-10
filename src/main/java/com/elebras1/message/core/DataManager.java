package com.elebras1.message.core;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.elebras1.message.core.database.EntityManager;
import com.elebras1.message.core.database.IDatabase;
import com.elebras1.message.core.database.IDatabaseObserver;
import com.elebras1.message.core.directory.IWatchableDirectory;
import com.elebras1.message.core.directory.WatchableDirectory;
import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.IMessageRecipient;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;

/**
 * Classe permettant de manipuler les données de l'application.
 *
 * @author S.Lucas
 */
public class DataManager {

	/**
	 * Base de donnée de l'application.
	 */
	protected final IDatabase mDatabase;

	/**
	 * Gestionnaire des entités contenu de la base de données.
	 */
	protected final EntityManager mEntityManager;

	/**
	 * Classe de surveillance de répertoire
	 */
	protected IWatchableDirectory mWatchableDirectory;

	/**
	 * Constructeur.
	 */
	public DataManager(IDatabase database, EntityManager entityManager) {
		mDatabase = database;
		mEntityManager = entityManager;
	}

	/**
	 * Ajoute un observateur sur les modifications de la base de données.
	 *
	 * @param observer
	 */
	public void addObserver(IDatabaseObserver observer) {
		this.mDatabase.addObserver(observer);
	}

	/**
	 * Supprime un observateur sur les modifications de la base de données.
	 *
	 * @param observer
	 */
	public void removeObserver(IDatabaseObserver observer) {
		this.mDatabase.removeObserver(observer);
	}

	/**
	 * Retourne la liste des Utilisateurs.
	 */
	public Set<User> getUsers() {
		return this.mDatabase.getUsers();
	}

	/**
	 * Retourne la liste des Messages.
	 */
	public Set<Message> getMessages() {
		return this.mDatabase.getMessages();
	}

	/**
	 * Retourne la liste des Canaux.
	 */
	public Set<Channel> getChannels() {
		return this.mDatabase.getChannels();
	}

	public Channel getChannel(UUID channelUuid) {
		for (Channel channel : this.getChannels()) {
			if (channel.getUuid().equals(channelUuid)) {
				return channel;
			}
		}
		return null;
	}

	/**
	 * Ecrit un message.
	 *
	 * @param message
	 */
	public void sendMessage(Message message) {
		// Ecrit un message
		this.mEntityManager.writeMessageFile(message);
	}

	/**
	 * Ecrit un Utilisateur.
	 *
	 * @param user
	 */
	public void sendUser(User user) {
		// Ecrit un utilisateur
		this.mEntityManager.writeUserFile(user);
	}

	/**
	 * Ecrit un Canal.
	 *
	 * @param channel
	 */
	public void sendChannel(Channel channel) {
		this.mEntityManager.writeChannelFile(channel);
	}

	public Set<Message> getMessagesFrom(UUID senderUuid) {
		Set<Message> userMessages = new HashSet<>();
		for (Message message : this.getMessages()) {
			if (message.getSender().getUuid().equals(senderUuid)) {
				userMessages.add(message);
			}
		}
		return userMessages;
	}

	public Set<Message> getMessagesFrom(UUID senderUuid, UUID recipientUuid) {
		Set<Message> userMessages = new HashSet<>();
		for (Message message : this.getMessagesFrom(senderUuid)) {
			if (message.getRecipient().equals(recipientUuid)) {
				userMessages.add(message);
			}
		}
		return userMessages;
	}

	public Set<Message> getMessagesTo(UUID recipientUuid) {
		Set<Message> userMessages = new HashSet<>();
		for (Message message : this.getMessages()) {
			if (message.getRecipient().equals(recipientUuid)) {
				userMessages.add(message);
			}
		}
		return userMessages;
	}

	public Set<Message> getMessagesTo(UUID recipientUuid, UUID senderUuid) {
		Set<Message> userMessages = new HashSet<>();
		for (Message message : this.getMessagesTo(recipientUuid)) {
			if (message.getSender().getUuid().equals(senderUuid)) {
				userMessages.add(message);
			}
		}
		return userMessages;
	}

	/**
	 * Assignation du répertoire d'échange.
	 * 
	 * @param directoryPath
	 */
	public void setExchangeDirectory(String directoryPath) {
		mEntityManager.setExchangeDirectory(directoryPath);

		mWatchableDirectory = new WatchableDirectory(directoryPath);
		mWatchableDirectory.initWatching();
		mWatchableDirectory.addObserver(mEntityManager);
	}
}
