package view;

import java.util.LinkedHashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import view.components.MenuButton;

import data.*;

public class Menu extends Frame {
    private LinkedHashMap<String, ActionListener> options;
    private Db db;

    public Menu(Db db) {
        super();

        this.db = db;
        this.options = new LinkedHashMap<>() {{
            put("Campamentos", e -> new CampsView(db));
            put("Instructores", e -> new InstructorsView(db));
            put("Reservas", e -> new BookingsView(db));
            put("Usuarios", e -> new UsersView(db));
        }};

        withPanel(this::build);
    }

    private void build(JPanel panel) {
        panel.setLayout(new GridLayout(options.size() + 1, 1, 10, 20));
        panel.setBorder(new EmptyBorder(30, 44, 40, 44));

        panel.add(createLabel("Administración"));
        options.forEach((title, cb) -> panel.add(new MenuButton(title, cb)));
    }
}
