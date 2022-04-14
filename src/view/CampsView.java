package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import data.*;

public class CampsView extends Frame {
    private Db db;
    public CampsView(Db db) {
        super();
        this.db = db;
        db.fetchCamps();
    }
}
