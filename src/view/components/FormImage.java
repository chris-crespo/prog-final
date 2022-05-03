package view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;

public class FormImage extends JLabel {
    public FormImage(BufferedImage img) {
        super(new ImageIcon(img));
    }

    public GridBagConstraints constraints(int row) {
        var constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = row;
        constraints.gridwidth = 1;
        return constraints;
    }
}
