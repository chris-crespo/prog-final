package view;

import java.io.File;
import java.util.ArrayList;
import java.util.function.*;
import java.time.LocalDate;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import view.components.*;
import models.Camp;
import data.Db;
import utils.Parse;
import utils.Image;

public class NewCampForm extends Form {
    private Db db;
    private Camp camp;

    public NewCampForm(Db db, String[] campKinds) {
        super((Object)campKinds);
        this.db = db;
    }

    protected void buildForm(Object[] params) {
        var campKinds = (String[])params[0];

        addRequiredField("Nombre");
        addField("Tipo", campKinds);
        addRequiredField("Descripción");
        addRequiredField("Lugar");
        addRequiredField("Comienzo (dd/mm/yyyy)", Parse.withParsedDate(
            date -> date.isAfter(LocalDate.now())
                && date.isBefore(Parse.parseDate(inputs.get("Fin (dd/mm/yyyy)").getText()))
        ));
        addRequiredField("Fin (dd/mm/yyyy)", Parse.withParsedDate(
            date -> date.isAfter(LocalDate.now())
                && date.isAfter(Parse.parseDate(inputs.get("Comienzo (dd/mm/yyyy)").getText()))
        ));
        addRequiredField("Edad Mínima", Parse.withParsedString(
            x -> 4 <= x && x <= Parse.parseIntOrDefault(inputs.get("Edad Máxima").getText(), 16)
        ));
        addRequiredField("Edad Máxima", Parse.withParsedString(
            x -> Parse.parseIntOrDefault(inputs.get("Edad Mínima").getText(), 4) <= x && x <= 16
        ));
    }

    protected void onSubmit(ActionEvent e) {
        var name      = inputs.get("Nombre").getText();
        var kind      = (String) dropdowns.get("Tipo").getSelectedItem();
        var desc      = inputs.get("Descripción").getText();
        var loc       = inputs.get("Lugar").getText();
        var startDate = Parse.parseDate(inputs.get("Comienzo (dd/mm/yyyy)").getText());
        var endDate   = Parse.parseDate(inputs.get("Fin (dd/mm/yyyy)").getText());
        var minAge    = Integer.parseInt(inputs.get("Edad Mínima").getText());
        var maxAge    = Integer.parseInt(inputs.get("Edad Máxima").getText());

        db.addCamp(new Camp(camp.id(), name, kind, desc, loc, startDate, endDate, minAge, maxAge));
    }
}
