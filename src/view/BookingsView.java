package view;

import data.Db;
import models.Booking;

public class BookingsView extends ListView<Booking> {
    public BookingsView(Db db) {
        super(db, "Reservas", BookingCard.class, db::fetchBookings);
    }
}
