package view.components;

import javax.swing.*;
import java.awt.*;

public class FormDropdown extends JComboBox<String> {
    public FormDropdown(String[] options) {
        super(options);
        setSelectedIndex(0);
        setPreferredSize(new Dimension(180, 22));
    }

    public GridBagConstraints constraints(int row) {
        var constraints = new GridBagConstraints();
        constraints.gridy = row;
        constraints.insets = new Insets(0, 0, 20, 0);
        return constraints;
    }
}
