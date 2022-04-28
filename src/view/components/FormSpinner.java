package view.components;

import java.util.function.Predicate;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FormSpinner extends JSpinner {
    private boolean required;
    private int min;
    private int max;

    public FormSpinner(int min, int max) {
        this.min = min;
        this.max = max;

        this.build();
    }

    private void build() {
        setModel(new SpinnerNumberModel(0, min, max, 1));
    }

    public GridBagConstraints constraints(int row) {
        var constraints = new GridBagConstraints();
        constraints.gridy = row;
        constraints.insets = new Insets(0, 0, 20, 0);
        return constraints;
    }

    public void required() {
        this.required = true;
    }

    private void setBlackBorder() {
        setBorder(new LineBorder(Color.black, 1));
    }

    private void setRedBorder() {
        setBorder(new LineBorder(Color.red, 1));
    }

    public boolean valid() {
        /*
        var text = getText();
        var valid = (!required || text.length() != 0) && validator.test(text);

        if (!valid) setRedBorder();
        else setBlackBorder();

        return valid;
        */
        return true;
    }
}
