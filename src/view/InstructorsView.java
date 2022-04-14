package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import data.*;

public class InstructorsView extends Frame {
    private Db db;

    public InstructorsView(Db db) {
        super();
        this.db = db;
    }
}
