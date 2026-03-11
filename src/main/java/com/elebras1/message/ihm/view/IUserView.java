package com.elebras1.message.ihm.view;

import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;

public interface IUserView extends View {

    void setOnClickCallback(OnClickUuidCallback callback);

    String getText();
}

