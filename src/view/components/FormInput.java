package view.components;

import java.util.function.Predicate;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FormInput extends JTextField {
    private boolean required;
    private Predicate<String> validator;

    public FormInput() {
        this(t -> true);
    }

    public FormInput(Predicate<String> validator) {
        super();

        this.validator = validator;
        this.required  = false;

        setPreferredSize(new Dimension(180, 22));
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
        var text = getText();
        var valid = (!required || text.length() != 0) && validator.test(text);

        if (!valid) setRedBorder();
        else setBlackBorder();

        return valid;
    }
}
