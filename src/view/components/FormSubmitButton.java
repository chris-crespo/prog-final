package view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FormSubmitButton extends JButton {
    public FormSubmitButton(ActionListener onSubmit) {
        super("OK");
        addActionListener(e -> onSubmit.actionPerformed(e));
    }

    public GridBagConstraints constraints(int row) {
        var constraints = new GridBagConstraints();
        constraints.gridy = row;
        constraints.gridwidth = 2;
        return constraints;
    }
}
