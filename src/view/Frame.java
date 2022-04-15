package view;

import java.util.function.Consumer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import view.Panel;

public abstract class Frame extends JFrame {
    private static String title = "Torre de Control";
    public Frame() {
        super(title);
    }

    public Frame(int closeOp) {
        super(title);
        setDefaultCloseOperation(closeOp);
    }

    void withPanel(Consumer<Panel> fn) {
        var panel = new Panel();
        fn.accept(panel);
        add(panel);
        pack();
        setVisible(true);
    }

    protected abstract void build(Panel panel);
}
