package com.elebras1.message.common;

import javafx.application.Platform;

public class FxUiDispatcher implements UiDispatcher {
    @Override
    public void dispatch(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            Platform.runLater(action);
        }
    }
}

