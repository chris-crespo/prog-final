package view;

import data.Db;
import models.Instructor;

public class InstructorsView extends ListView<Instructor> {
    public InstructorsView(Db db) {
        super(db, "Instructores", InstructorCard.class, db::fetchInstructors);
    }
}
