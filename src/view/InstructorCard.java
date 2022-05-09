package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import view.components.Label;
import data.Db;
import models.Instructor;

public class InstructorCard extends Panel {
    private Instructor instructor;

    public InstructorCard(Db db, Instructor instructor) {
        super();
        this.instructor = instructor;

        this.build();
    }

    private void build() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 80));

        var bw = 8;
        setBorder(new EmptyBorder(bw, bw, bw, bw));

        add(new Label(String.format("%s (%s)", instructor.name(), instructor.dni()), Font.BOLD, 15));
        add(new Label(instructor.phone(), Font.PLAIN, 13));
        add(new Label(String.format("%s (%s)", instructor.campName(), instructor.activity()), Font.PLAIN, 13));
    }

}
