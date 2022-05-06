package view.components;

import javax.swing.*;
import java.awt.*;

public class FormLabel extends JLabel {
    public FormLabel(String name) {
        super(name);
    }

    public GridBagConstraints constraints(int row) {
        var constraints = new GridBagConstraints();

        constraints.gridy  = row;
        constraints.anchor = GridBagConstraints.WEST; 
        constraints.insets = new Insets(0, 0, 20, 20);

        return constraints;
    }
}
