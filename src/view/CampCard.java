package view;

import javax.swing.*;
import java.awt.*;

import view.components.Label;
import models.Camp;

public class CampCard extends Panel {
    private Camp camp;

    public CampCard(Camp camp) {
        super();
        this.camp = camp;
        this.build();
    }

    private String formatDescription(String desc) {
        if (desc.length() > 20)
            return desc.substring(0, 25).trim() + "...";
        return desc;
    }

    private void build() {
        setLayout(new GridLayout(3, 1, 0, 4));
        add(new Label(camp.name(), Font.BOLD, 15));
        add(new Label(camp.kind()));
        add(new Label(formatDescription(camp.description())));
    }
}
