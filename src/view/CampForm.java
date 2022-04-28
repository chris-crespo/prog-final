package view;

import java.util.function.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import view.components.*;
import models.Camp;
import data.Db;

public class CampForm extends Form {
    private Db db;

    public CampForm(Db db, Camp camp) {
        super(camp);
        this.db = db;
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

    private LocalDate parseDate(String str) {
        return LocalDate.parse(str, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private Predicate<String> withParsedDate(Predicate<LocalDate> pred) {
        return str -> {
            try {
                return pred.test(parseDate(str));
            }
            catch (Exception exn) {
                return false;
            }
        };
    }

    protected void buildForm(Object obj) {
        var camp = (Camp)obj;

        addRequiredField("Nombre", camp.name());
        addRequiredField("Tipo", camp.kind());
        addRequiredField("Descripción", camp.description());
        addRequiredField("Lugar", camp.location());
        addRequiredField("Comienzo (dd/mm/yyyy)", withParsedDate(
            date -> date.isAfter(LocalDate.now())
                && date.isBefore(parseDate(inputs.get("Fin (dd/mm/yyyy)").getText()))
        ));
        addRequiredField("Fin (dd/mm/yyyy)", withParsedDate(
            date -> date.isAfter(LocalDate.now())
                && date.isAfter(parseDate(inputs.get("Comienzo (dd/mm/yyyy)").getText()))
        ));
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
