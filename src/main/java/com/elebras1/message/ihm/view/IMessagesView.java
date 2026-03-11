package com.elebras1.message.ihm.view;

import java.util.function.Consumer;

public interface IMessagesView extends View {

    void setSendAction(Consumer<String> onSend);

    void addMessage(IMessageView messageView);

    void clearMessages();
}
