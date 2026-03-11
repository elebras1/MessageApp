package com.elebras1.message.ihm.view;

import javax.swing.*;

public interface IChatView extends View {

    void setLeftUpSection(View leftUpSection);

    void setLeftDownSection(View leftDownSection);

    public void setRightSection(View rightSection);
}
