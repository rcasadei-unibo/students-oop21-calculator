package view.components;

import javax.swing.*;

import controller.temp.TempCalculator;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

/**
 * 
 */
public class CCNumPad extends JPanel {

    private static final long serialVersionUID = -464468621586492647L;
    private final TempCalculator controller;

    /**
     * @param contr controller of the calculator that will use this component
     */
    public CCNumPad(final TempCalculator contr) {
        this.controller = contr;


        this.setLayout(new GridLayout(5, 3));
        final ActionListener al = e -> {
            final var btn = (JButton) e.getSource();
            this.controller.read(btn.getText());
        };

        this.add(createBtn("(", al));
        this.add(createBtn(")", al));
        this.add(createBtn("-", al));
        for (int k = 1; k <= 9; k++){
            this.add(createBtn(String.valueOf(k), al));
        }
        this.add(createBtn(".", al));
        this.add(createBtn("0", al));
        this.add(createBtn("=", (e) -> this.controller.calculate()));

    }
    private JButton createBtn(final String s, final ActionListener al) {
        final var btn = new JButton(s);
        btn.addActionListener(al);
        return btn;
    }
}
