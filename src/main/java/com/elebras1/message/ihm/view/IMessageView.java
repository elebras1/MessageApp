package com.elebras1.message.ihm.view;

import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;

public interface IMessageView extends View {
    void setOnDeleteCallback(OnClickUuidCallback callback);
}
