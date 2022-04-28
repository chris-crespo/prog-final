package view;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import view.components.*;

public abstract class Form extends Frame {
    LinkedHashMap<String, FormInput> inputs;
    LinkedHashMap<String, FormDropdown>  dropdowns;

    private Object obj;

    private Panel panel;
    private int rows;

    public Form() {
        this(null);
    }

    public Form(Object obj) {
        super();

        this.inputs    = new LinkedHashMap<>();
        this.dropdowns = new LinkedHashMap<>();

        // Guardamos un objeto al que podremos acceder durante la construcción
        // del formulario. Esto nos permitirá usar sus atributos como valores
        // por defecto, entre otros.
        this.obj = obj;
        this.rows = 0;

        withPanel(this::build);
    }

    protected abstract void buildForm(Object obj);

    protected void build(Panel panel) {
        this.panel = panel;
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(30, 44, 40, 44));

        buildForm(obj);

        var submitButton = new FormSubmitButton(this::handleSubmit);
        panel.add(submitButton, submitButton.constraints(rows));
    }

    void addRequiredField(String name) {
        addRequiredField(name, t -> true);
    }

    void addRequiredField(String name, String def) {
        addRequiredField(name, def, t -> true);
    }

    void addRequiredField(String name, Predicate<String> validator) {
        addRequiredField(name, "", validator);
    }

    void addRequiredField(String name, String def, Predicate<String> validator) {
        addField(name, def, validator);
        inputs.get(name).required();
    }

    void addField(String name) {
        addField(name, "", t -> true);
    }

    void addField(String name, String def) {
        addField(name, def, t -> true); 
    }

    void addField(String name, Predicate<String> validator) {
        addField(name, "", validator);
    }

    void addField(String name, String def, Predicate<String> validator) {
        var label = new FormLabel(name);
        var input = new FormInput(validator);
        input.setText(def);
        input.setCaretPosition(0);

        inputs.put(name, input);
        panel.add(label, label.constraints(rows));
        panel.add(input, input.constraints(rows++));
    }

    void addField(String name, String[] options) {
        var label    = new FormLabel(name);
        var dropdown = new FormDropdown(options);

        dropdowns.put(name, dropdown);
        panel.add(label, label.constraints(rows));
        panel.add(dropdown, dropdown.constraints(rows++));
    }

    private boolean valid() {
        return inputs.entrySet().stream()
            .map(Map.Entry::getValue)
            /* Usamos map->reduce en vez de allMatch(FormInput::valid)
             * para asegurarnos de que todos los elementos son validados
             * y alterados como corresponda */
            .map(FormInput::valid)
            .reduce(true, (acc, x) -> acc && x);
    }

    private void handleSubmit(ActionEvent e) {
        if (valid()) {
            onSubmit(e);
            dispose();
        }
    }

    abstract void onSubmit(ActionEvent e);
}
