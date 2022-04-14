package view;

import java.util.function.Consumer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    public Frame() {
        super("Dbistración");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.white);
    }

    void withPanel(Consumer<JPanel> fn) {
        var panel = new JPanel();
        fn.accept(panel);
        add(panel);
        pack();
        setVisible(true);
    }

    JLabel createLabel(String text) {
        var label = new JLabel(text);
        label.setFont(new Font("sans-serif", Font.BOLD, 22));
        return label;
    }

    JButton createButton(String title, ActionListener cb) {
        var btn = new JButton();
        btn.setText(title);
        btn.addActionListener(e -> {
            dispose();
            cb.actionPerformed(e);
        });
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("sans-serif", Font.PLAIN, 13));
        return btn;
    }
}
