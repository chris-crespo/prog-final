package view;

import java.util.function.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import view.components.*;
import models.Camp;

public class CampForm extends Form {
    public CampForm(Camp camp) {
        super(camp);
    }

    private Predicate<String> withParsedString(Predicate<Integer> pred) {
        return str -> {
            try {
                var x = Integer.parseInt(str);
                return pred.test(x);
            }
            catch (Exception exn) {
                return false;
            }
        };
    }

    private int parseIntOrDefault(String str, int def) {
        try {
            return Integer.parseInt(str);
        }
        catch (Exception exn) {
            return def;
        }
    }

    protected void buildForm(Object obj) {
        var camp = (Camp)obj;

        addRequiredField("Nombre", camp.name());
        addField("Tipo", new String[] { "Tecnológico" });
        addRequiredField("Descripción", camp.description());
        addRequiredField("Lugar", camp.location());
        addRequiredField("Comienzo");
        addRequiredField("Fin");
        addRequiredField("Edad Mínima", withParsedString(
            x -> 4 <= x && x <= parseIntOrDefault(inputs.get("Edad Máxima").getText(), 16)
        ));
        addRequiredField("Edad Máxima", withParsedString(
            x -> parseIntOrDefault(inputs.get("Edad Mínima").getText(), 4) <= x && x <= 16
        ));
    }

    protected void onSubmit(ActionEvent e) {
        System.out.println("Submitted");
    }
}
