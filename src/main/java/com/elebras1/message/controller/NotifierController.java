package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.database.EntityManager;
import com.elebras1.message.core.database.IDatabase;
import com.elebras1.message.core.database.IDatabaseObserver;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotifierController implements INotifierController, IDatabaseObserver {
    private final ISession session;

    public NotifierController(ISession session) {
        this.session = session;
    }

    private List<String> extractTags(String text) {
        List<String> tags = new ArrayList<>();
        Matcher matcher = Pattern.compile("@(\\w+)").matcher(text);
        while (matcher.find()) {
            tags.add(matcher.group(1));
        }
        return tags;
    }

    @Override
    public void notifyMessageAdded(Message addedMessage) {
        if(this.session.getConnectedUser() == null) {
            return;
        }
        List<String> tags = extractTags(addedMessage.getText());
        for (String tag : tags) {
            if (tag.equals(session.getConnectedUser().getUserTag())) {
                final String messagePreview = addedMessage.getText().substring(0, Math.min(20, addedMessage.getText().length()));
                Thread notifThread = new Thread(() -> {
                    try {
                        SystemTray tray = SystemTray.getSystemTray();
                        TrayIcon trayIcon = new TrayIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
                        tray.add(trayIcon);
                        trayIcon.displayMessage("Nouveau message", messagePreview, TrayIcon.MessageType.INFO);
                        Thread.sleep(3000);
                        tray.remove(trayIcon);
                    } catch (AWTException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                notifThread.setDaemon(true);
                notifThread.start();
            }
        }
    }

    @Override
    public void notifyMessageDeleted(Message deletedMessage) {

    }

    @Override
    public void notifyMessageModified(Message modifiedMessage) {

    }

    @Override
    public void notifyUserAdded(User addedUser) {

    }

    @Override
    public void notifyUserDeleted(User deletedUser) {

    }

    @Override
    public void notifyUserModified(User modifiedUser) {

    }

    @Override
    public void notifyChannelAdded(Channel addedChannel) {

    }

    @Override
    public void notifyChannelDeleted(Channel deletedChannel) {

    }

    @Override
    public void notifyChannelModified(Channel modifiedChannel) {

    }
}
