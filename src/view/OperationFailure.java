package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

import view.components.*;

public class OperationFailure extends Frame {
    public static String text = "No se pudo realizar la operación";
    public OperationFailure(Exception exn) {
        super(JFrame.EXIT_ON_CLOSE);
        withPanel(this::build);
    }

    protected void build(Panel panel) {
        panel.setBorder(new EmptyBorder(30, 44, 30, 44)); 
        
        panel.add(new Label(text, Font.BOLD, 15));
    }
}
