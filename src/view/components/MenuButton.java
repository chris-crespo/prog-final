package view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuButton extends JButton {
    public MenuButton(String text, ActionListener cb) {
        super(text);
        addActionListener(cb);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setFont(new Font("sans-serif", Font.PLAIN, 13));
    }
}
