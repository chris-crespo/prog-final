package view;

import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import data.*;
import models.User;

public class UsersView extends Frame {
    private Db db;
    private List<User> users;

    public UsersView(Db db) {
        super();

        this.db = db;
    }
}
