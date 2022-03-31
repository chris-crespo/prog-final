package view;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    public Button(String text) {
        super(text);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setFont(new Font("sans-serif", Font.PLAIN, 13));
        setBackground(Color.black);
        setForeground(Color.white);
    }
}
