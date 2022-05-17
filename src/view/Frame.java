package view;

import java.util.function.Consumer;

import javax.swing.JFrame;

public abstract class Frame extends JFrame {
    private static String title = "Gestion de Campamiento";

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
