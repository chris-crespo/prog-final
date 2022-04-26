package view;

import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import view.Panel;
import view.components.*;
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

    private void attachCamps(List<Camp> camps) {
        var innerpanel = new JPanel() {{
            setLayout(new GridBagLayout());
        }};
        
        var scroll = new JScrollPane(innerpanel);
        scroll.setPreferredSize(new Dimension(300, 200));

        var row = 1;
        for (var camp : camps) {
            var constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = row++;
            constraints.anchor = GridBagConstraints.WEST;
            innerpanel.add(new CampCard(camp), constraints);
        };

        panel.add(scroll);
    }

    private void showCamps(List<Camp> camps) {
        setPreferredSize(new Dimension(340, 300));

        var title = new Title("Campamentos");
        panel.add(title); 

        if (camps.size() == 0) {
            var text = "No hay campamentos registrados.";
            panel.add(new Label(text, Font.BOLD, 15));
        }
        else {
            attachCamps(camps);
        }
    }

    protected void build(Panel panel) {
        this.panel = panel;

        db.fetchCamps()
            .ifOk(this::showCamps)
            .ifError(e -> failedToFetch());
    }
}
