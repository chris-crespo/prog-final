package view;

import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import view.components.Label;
import models.Camp;
import data.Db;

public class CampCard extends Panel {
    private Camp camp;
    private Db db;

    public CampCard(Db db, Camp camp, Runnable disposeView) {
        super();
        this.db = db;
        this.camp = camp;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(200, 200, 200));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(238, 238, 238));
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                db.fetchCampKinds()
                        .ifOk(kinds -> new CampForm(db, camp, kinds.toArray(new String[] {})))
                        .ifError(exn -> new CampKindFetchFailure());

                disposeView.run();
            }
        });

        this.build();
    }

    private String limitStr(String str, int length) {
        var words = Arrays.asList(str.split(" "));
        assert words.size() > 1 : "'limitStr' called with one or less words";

        var builder = new StringBuilder(words.get(0));

        for (var word : words.subList(1, words.size())) {
            if (builder.length() + word.length() < length) {
                builder.append(" ");
                builder.append(word);
            }
        }

        return builder.toString();
    }

    private String formatDescription(String desc) {
        if (desc.length() > 60)
            return limitStr(desc, 60) + "...";
        return desc;
    }

    private void build() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 40));

        var bw = 8;
        setBorder(new EmptyBorder(bw, bw, bw, bw));

        add(new Label(camp.name(), Font.BOLD, 15));
        add(new Label(camp.kind()));
        add(new Label(formatDescription(camp.description())));
    }
}
