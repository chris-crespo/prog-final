package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import view.Panel;

import data.*;
import models.Booking;

public class BookingsView extends ListView<Booking> {
    public BookingsView(Db db) {
        super(db, "Reservas", BookingCard.class, db::fetchBookings);
    
    }
