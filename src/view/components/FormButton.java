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
        constraints.insets = new Insets(0, 0, 0, 20);
        return constraints;
    }
}
