package view;

import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import view.Panel;
import view.components.Label;

import data.*;
import models.Camp;

public class CampsView extends Frame {
    private Db db;
    private Panel panel;

    public CampsView(Db db) {
        super();
        this.db = db;

        withPanel(this::build);
    }

    private void failedToFetch() {
        var text = "No se pudo obtener los datos.";
        panel.add(new Label(text, Font.BOLD, 15)); 
    }

    private void showCamps(List<Camp> camps) {
        var text = "No hay campamentos registrados.";
        panel.add(new Label(text, Font.BOLD, 15));
    }

    protected void build(Panel panel) {
        this.panel = panel;

        db.fetchCamps()
            .ifOk(this::showCamps)
            .ifError(e -> failedToFetch());
    }
}
