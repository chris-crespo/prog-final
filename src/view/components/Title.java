package view.components;

import javax.swing.*;
import java.awt.*;

public class Title extends JLabel {
    public Title(String text) {
        super(text);
        setFont(new Font("sans-serif", Font.BOLD, 22));
    }

    public GridBagConstraints constraints() {
        var constraints = new GridBagConstraints();

        constraints.insets = new Insets(0, 0, 20, 0);
        constraints.anchor = GridBagConstraints.WEST;

        return constraints;
    }
}

