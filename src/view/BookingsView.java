package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import view.Panel;

import data.*;

public class BookingsView extends Frame {
    private Db db;

    public BookingsView(Db db) {
        super();
        this.db = db;

        withPanel(this::build);
    }

    protected void build(Panel panel) { }
}
