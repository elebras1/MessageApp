package com.elebras1.message.datamodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Classe du modèle représentant un canal.
 *
 * @author S.Lucas
 */
public class Channel extends AbstractMessageAppObject implements IMessageRecipient {

	/**
	 * Créateur du canal.
	 */
	protected final User mCreator;

	/**
	 * Nom du canal.
	 */
	protected final String mName;

	/**
	 * Statut privé ou public du canal.
	 */
	protected boolean mPrivate;

	/**
	 * Liste des Utilisateurs du canal.
	 */
	protected final Set<User> mUsers = new HashSet<User>();

	/**
	 * Constructeur.
	 *
	 * @param creator utilisateur à l'origine du canal.
	 * @param name   Nom du canal.
	 */
	public Channel(User creator, String name, boolean isPrivate) {
		this(UUID.randomUUID(), creator, name, isPrivate);
	}

	/**
	 * Constructeur.
	 *
	 * @param channelUuid identifiant du canal.
	 * @param creator      utilisateur à l'origine du canal.
	 * @param name        Nom du canal.
	 * @param isPrivate   Statut privé ou public du canal.
	 */
	public Channel(UUID channelUuid, User creator, String name, boolean isPrivate) {
		super(channelUuid);
		mCreator = creator;
		mName = name;
		mPrivate = isPrivate;
		if (isPrivate) {
			mUsers.add(creator);
		}
	}

	/**
	 * Constructeur pour un canal privé.
	 *
	 * @param creator utilisateur à l'origine du canal.
	 * @param name   Nom du canal.
	 * @param users  Liste des utilisateurs du canal privé.
	 */
	public Channel(User creator, String name, List<User> users) {
		this(UUID.randomUUID(), creator, name, users);
	}

	/**
	 * Constructeur pour un canal avec liste d'utilisateurs et statut explicite.
	 *
	 * @param messageUuid identifiant du canal.
	 * @param creator      utilisateur à l'origine du canal.
	 * @param name        Nom du canal.
	 * @param users       Liste des utilisateurs du canal.
	 * @param isPrivate   Statut privé ou public du canal.
	 */
	public Channel(UUID messageUuid, User creator, String name, List<User> users, boolean isPrivate) {
		this(messageUuid, creator, name, isPrivate);
		mUsers.addAll(users);
	}

	/**
	 * Constructeur pour un canal privé (rétrocompatibilité).
	 *
	 * @param messageUuid identifiant du canal.
	 * @param creator      utilisateur à l'origine du canal.
	 * @param name        Nom du canal.
	 * @param users       Liste des utilisateurs du canal privé.
	 */
	public Channel(UUID messageUuid, User creator, String name, List<User> users) {
		this(messageUuid, creator, name, users, true);
	}

	/**
	 * @return l'utilisateur source du canal.
	 */
	public User getCreator() {
		return mCreator;
	}

	/**
	 * @return le corps du message.
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @return la liste des utilisateurs de ce canal.
	 */
	public List<User> getUsers() {
		return new ArrayList<User>(mUsers);
	}

	/**
	 * @return true si le canal est privé, false sinon.
	 */
	public boolean isPrivate() {
		return mPrivate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("[");
		sb.append(this.getClass().getName());
		sb.append("] : ");
		sb.append(this.getUuid());
		sb.append(" {");
		sb.append(this.getName());
		sb.append("}");

		return sb.toString();
	}

}
