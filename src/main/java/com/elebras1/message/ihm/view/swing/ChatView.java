package com.elebras1.message.ihm.view.swing;

import com.elebras1.message.ihm.view.IChatView;
import com.elebras1.message.ihm.view.View;

import javax.swing.*;
import java.awt.*;

public class ChatView extends JPanel implements IChatView {
    private final JSplitPane horizontalSplitPane;
    private final JSplitPane verticalSplitPane;

    public ChatView() {
        this.setLayout(new BorderLayout());

        this.verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        this.verticalSplitPane.setResizeWeight(0.5);
        this.verticalSplitPane.setDividerSize(6);
        this.verticalSplitPane.setContinuousLayout(true);

        this.horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.horizontalSplitPane.setResizeWeight(0.3);
        this.horizontalSplitPane.setDividerSize(6);
        this.horizontalSplitPane.setContinuousLayout(true);
        this.horizontalSplitPane.setLeftComponent(this.verticalSplitPane);

        this.add(this.horizontalSplitPane, BorderLayout.CENTER);
    }

    @Override
    public void setLeftUpSection(View leftUpSection) {
        JPanel leftUpSectionImpl = (JPanel) leftUpSection;
        this.verticalSplitPane.setTopComponent(leftUpSectionImpl);
        this.horizontalSplitPane.setDividerLocation(0.3);
    }

    @Override
    public void setLeftDownSection(View leftDownSection) {
        JPanel leftDownSectionImpl = (JPanel) leftDownSection;
        this.verticalSplitPane.setBottomComponent(leftDownSectionImpl);
        this.horizontalSplitPane.setDividerLocation(0.3);
    }

    @Override
    public void setRightSection(View rightSection) {
        JPanel rightSectionImpl = (JPanel) rightSection;
        this.horizontalSplitPane.setRightComponent(rightSectionImpl);
    }
}