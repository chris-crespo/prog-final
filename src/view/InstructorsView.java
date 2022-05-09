package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import view.Panel;

import data.*;
import models.Instructor;

public class InstructorsView extends ListView<Instructor> {
    public InstructorsView(Db db) {
        super(db, "Instructores", InstructorCard.class, db::fetchInstructors);
    }
}
