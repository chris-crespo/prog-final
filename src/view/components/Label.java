package view.components;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {
    public Label(String name) {
        this(name, Font.PLAIN, 13);
    }

    public Label(String name, int style, int size) {
        super(name);
        setFont(new Font("sans-serif", style, size));
    }

    public GridBagConstraints constraints(int row) {
        var constraints = new GridBagConstraints();

        constraints.gridy = row;
        constraints.insets = new Insets(0, 0, 10, 0);
        constraints.anchor = GridBagConstraints.WEST;

        return constraints;
    }
}
