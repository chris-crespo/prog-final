package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import view.components.Label;
import models.User;
import data.Db;

public class UserCard extends Panel {
    private User user;

    public UserCard(Db db, User user, Runnable disposeView) {
        super();
        this.user = user;

        this.build();
    }

    private void build() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 80));

        var bw = 8;
        setBorder(new EmptyBorder(bw, bw, bw, bw));

        add(new Label(user.name(), Font.BOLD, 15));
        add(new Label(user.email(), Font.PLAIN, 13));
        add(new Label(user.phone(), Font.PLAIN, 13));
    }
}
