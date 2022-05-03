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

    private void failedToFetch(Exception exn) {
        var text = "No se pudo obtener los datos.";
        panel.add(new Label(text, Font.BOLD, 15)); 
        panel.add(new Label(exn.getMessage(), Font.BOLD, 13)); 
    }

    private void attachCamps(List<Camp> camps) {
        var scroll = new JScrollPane();
        scroll.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        var innerPanel = new Panel();
        innerPanel.setBorder(BorderFactory.createEmptyBorder());
        scroll.setViewportView(innerPanel);
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (var camp : camps)
            innerPanel.add(new CampCard(db, camp, this::dispose));

        panel.add(scroll, BorderLayout.CENTER);
    }

    private void showCamps(List<Camp> camps) {
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(610, 400));

        var title = new Title("Campamentos");
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH); 

        if (camps.size() == 0) {
            var text = "No hay campamentos registrados.";
            panel.add(new Label(text, Font.BOLD, 15));
        }
        else {
            attachCamps(camps);
        }

        var btn = new JButton("Añadir campamento");
        btn.addActionListener(e -> db.fetchCampKinds()
            .ifOk(kinds -> new NewCampForm(db, kinds.toArray(new String[] {})))
            .ifError(exn -> new CampKindFetchFailure()));;
        panel.add(btn, BorderLayout.SOUTH);
    }

    protected void build(Panel panel) {
        this.panel = panel;

        db.fetchCamps()
            .ifOk(this::showCamps)
            .ifError(this::failedToFetch);
    }
}
