package com.elebras1.message.common;

import javax.swing.SwingUtilities;

public class SwingUiDispatcher implements UiDispatcher {
    @Override
    public void dispatch(Runnable action) {
        SwingUtilities.invokeLater(action);
    }
}

