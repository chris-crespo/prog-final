package view;

import java.util.LinkedHashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import view.Panel;
import view.components.*;

import data.*;

public class Menu extends Frame {
    private LinkedHashMap<String, ActionListener> options;
    private Db db;

    public Menu(Db db) {
        super(JFrame.EXIT_ON_CLOSE);

        this.db = db;
        this.options = new LinkedHashMap<>() {{
            put("Campamentos",  e -> new CampsView(db));
            put("Instructores", e -> new InstructorsView(db));
            put("Reservas",     e -> new BookingsView(db));
            put("Usuarios",     e -> new UsersView(db));
        }};

        withPanel(this::build);
    }

    protected void build(Panel panel) {
        panel.setLayout(new GridLayout(options.size() + 1, 1, 10, 20));

        panel.add(new Title("Administración"));
        options.forEach((title, cb) -> panel.add(new MenuButton(title, cb)));
    }
}
