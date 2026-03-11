package com.elebras1.message.factory;

import com.elebras1.message.ihm.view.IChannelView;
import com.elebras1.message.ihm.view.IMessageView;
import com.elebras1.message.ihm.view.IUserView;
import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;

import java.util.UUID;

public interface ViewFactory {

    /**
     * Crée une vue représentant un canal.
     *
     * @param id          UUID du canal
     * @param channelName nom du canal
     * @param isCreator   vrai si l'utilisateur connecté est le créateur
     * @param isPrivate   vrai si le canal est privé
     * @param onRemove    callback déclenché lors de la suppression / départ du canal
     * @param onAddMember callback déclenché lors de la demande d'ajout de membre
     * @return une implémentation de {@link IChannelView}
     */
    IChannelView createChannelView(UUID id, String channelName, boolean isCreator, boolean isPrivate,
                                   OnClickUuidCallback onRemove, OnClickUuidCallback onAddMember);

    /**
     * Crée une vue représentant un message.
     *
     * @param id       UUID du message
     * @param content  contenu textuel du message
     * @param metadata métadonnées affichées (auteur, date…)
     * @param isMine   vrai si le message appartient à l'utilisateur connecté
     * @return une implémentation de {@link IMessageView}
     */
    IMessageView createMessageView(UUID id, String content, String metadata, boolean isMine);

    /**
     * Crée une vue représentant un utilisateur.
     *
     * @param id          UUID de l'utilisateur
     * @param displayName nom affiché
     * @param online      statut de connexion
     * @return une implémentation de {@link IUserView}
     */
    IUserView createUserView(UUID id, String displayName, boolean online);
}
