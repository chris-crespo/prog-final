package view;

import java.util.List;
import java.util.function.Supplier;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import data.Db;
import utils.Result;

import view.components.Label;
import view.components.*;

public abstract class ListView<T> extends Frame {
    private Db db;
    private Panel panel;

    private String name;
    private Class cardClass;
    private Supplier<Result<List<T>>> fetch;
    private ActionListener btnCb;

    public ListView(Db db, String name, Class cardClass, Supplier<Result<List<T>>> fetch) {
        super();
        this.db = db;

        this.name = name;
        this.cardClass = cardClass;
        this.fetch = fetch;
        this.btnCb = null;

        withPanel(this::build);
    }

    public ListView(Db db, String name, Class cardClass, Supplier<Result<List<T>>> fetch, ActionListener cb) {
        super();
        this.db = db;

        this.name = name;
        this.cardClass = cardClass;
        this.fetch = fetch;
        this.btnCb = cb;

        withPanel(this::build);
    }

    private void failedToFetch(Exception exn) {
        var text = "No se pudo obtener los datos.";
        panel.add(new Label(text, Font.BOLD, 15));
    }

    private void attachItems(List<T> items) {
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

        var constructor = cardClass.getDeclaredConstructors()[0];
        for (var item : items) {
            try {
                innerPanel.add((Panel) constructor.newInstance(db, item, (Runnable) this::dispose));
            } catch (Exception exn) {
                System.out.println("Exception trying to instantiate class: " + exn.getMessage());
            }
        }

        panel.add(scroll, BorderLayout.CENTER);
    }

    private void attachButton() {
        var btn = new JButton(String.format("Añadir %s", name.toLowerCase()));
        btn.addActionListener(e -> {
            btnCb.actionPerformed(e);
            dispose();
        });
        panel.add(btn, BorderLayout.SOUTH);
    }

    private void show(List<T> items) {
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(610, 400));

        var title = new Title(name);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        if (items.size() == 0) {
            var text = String.format("No hay %s registrados.", name.toLowerCase());
            panel.add(new Label(text, Font.BOLD, 15));
        } else {
            attachItems(items);
        }

        if (btnCb != null)
            attachButton();
    }

    protected void build(Panel panel) {
        this.panel = panel;

        fetch.get()
                .ifOk(this::show)
                .ifError(this::failedToFetch);
    }
}
