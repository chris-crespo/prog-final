package view;

import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import view.components.Label;
import models.User;
import data.Db;

public class UserCard extends Panel {
    private User user;
    private Db db;

    public UserCard(Db db, User user, Runnable disposeView) {
        super();
        this.db   = db;
        this.user = user;

        this.build();
    }

    private void build() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 80));

        var bw = 8;
        setBorder(new EmptyBorder(bw, bw, bw, bw));
        System.out.println(user);
        add(new Label(user.name(), Font.BOLD, 15));
        add(new Label(user.email(), Font.PLAIN, 13));
        add(new Label(user.phone(), Font.PLAIN, 13));
    }
}
