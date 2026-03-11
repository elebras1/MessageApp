package com.elebras1.message.ihm.view;

import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;

public interface IChannelView extends View {

    void setOnClickCallback(OnClickUuidCallback callback);

    String getChannelName();
}
