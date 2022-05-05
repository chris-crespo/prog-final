package view;

import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import view.Panel;

import data.*;
import models.User;

public class UsersView extends ListView<User> {
    public UsersView(Db db) {
        super(db, "Usuarios", UserCard.class, db::fetchUsers);
    }
}
