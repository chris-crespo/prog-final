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

public class CampsView extends ListView<Camp> {
    public CampsView(Db db) {
        super(db, "Campamentos", CampCard.class, db::fetchCamps, e -> {
            db.fetchCampKinds()
                .ifOk(kinds -> new NewCampForm(db, kinds.toArray(new String[] {})))
                .ifError(exn -> new CampKindFetchFailure());
        });
    }
}
