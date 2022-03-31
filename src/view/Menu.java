package view;

import java.util.LinkedHashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class Menu extends Frame {
    private LinkedHashMap<String, ActionListener> options;

    public Menu() {
        super();

        //this.control = control;
        this.options = new LinkedHashMap<>() {{
            put("Mostrar reservas", e -> {});
            put("Mostrar usuarios",  e -> {});
        }};

        withPanel(this::build);
    }

    private void build(JPanel panel) {
        panel.setLayout(new GridLayout(4, 1, 10, 20));
        panel.setBorder(new EmptyBorder(30, 44, 40, 44));

        panel.add(createLabel("Administración"));
        options.forEach((title, cb) -> panel.add(new Button(title)));
    }

    private void showLandingForm() {
        //new LandingForm(control);  
    }

    private void showTakeOffForm() {
        //new TakeOffForm(control);
    }

    private void auth() {
        /*
        var authorized = control.auth(); 
        if (authorized)
            new AuthSuccess(control);
        else 
            new AuthFailure(control);
            */
    }
}
