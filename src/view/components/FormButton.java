package view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FormButton extends JButton {
    public FormButton(String text, ActionListener onSubmit) {
        super(text);
        addActionListener(e -> onSubmit.actionPerformed(e));
    }

    public GridBagConstraints constraints(int row) {
        var constraints = new GridBagConstraints();
        constraints.gridy = row;
        constraints.gridwidth = 1;
        return constraints;
    }

    public GridBagConstraints constraints(int row, int width) {
        var constraints = new GridBagConstraints();
        constraints.gridy = row;
        constraints.gridwidth = width;
        return constraints;
    }
}
