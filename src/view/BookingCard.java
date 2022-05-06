package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import view.components.Label;
import models.Booking;
import data.Db;

public class BookingCard extends Panel {
    private Booking booking;

    public BookingCard(Db db, Booking booking, Runnable disposeView) {
        super();
        this.booking = booking;

        this.build();
    }

    private void build() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 80));

        var bw = 8;
        setBorder(new EmptyBorder(bw, bw, bw, bw));

        // kidname (email)
        // dni
        // campName
        add(new Label(booking.kidName(), Font.BOLD, 15));
        add(new Label(booking.email(), Font.PLAIN, 13));
        add(new Label(booking.kidDni(), Font.PLAIN, 13));
        add(new Label(booking.campName(), Font.PLAIN, 13));
    }
}
