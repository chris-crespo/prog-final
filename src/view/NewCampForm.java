package view;

import java.io.File;
import java.util.ArrayList;
import java.util.function.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import view.components.*;
import models.Camp;
import data.Db;
import utils.Image;

public class NewCampForm extends Form {
    private Db db;
    private Camp camp;

    public NewCampForm(Db db, String[] campKinds) {
        super((Object)campKinds);
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

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    protected void buildForm(Object[] params) {
        var campKinds = (String[])params[0];
        System.out.println(campKinds);

        addRequiredField("Nombre");
        addField("Tipo", campKinds);
        addRequiredField("Descripción");
        addRequiredField("Lugar");
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

        Image.load("assets/placeholder.png")
            .ifOk(img -> addImage("Añadir/Cambiar imagen", img))
            .ifError(err -> System.out.println(err.getMessage()));
    }

    protected void onSubmit(ActionEvent e) {
        var name      = inputs.get("Nombre").getText();
        var kind      = (String) dropdowns.get("Tipo").getSelectedItem();
        var desc      = inputs.get("Descripción").getText();
        var loc       = inputs.get("Lugar").getText();
        var startDate = parseDate(inputs.get("Comienzo (dd/mm/yyyy)").getText());
        var endDate   = parseDate(inputs.get("Fin (dd/mm/yyyy)").getText());
        var minAge    = Integer.parseInt(inputs.get("Edad Mínima").getText());
        var maxAge    = Integer.parseInt(inputs.get("Edad Máxima").getText());

        db.addCamp(new Camp(camp.id(), name, kind, desc, loc, startDate, endDate, minAge, maxAge));
    }
}
