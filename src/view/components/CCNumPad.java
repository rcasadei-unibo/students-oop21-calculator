package view.components;

import javax.swing.*;

import controller.manager.CCManager;
import utils.AbstractCalculator;


import java.awt.GridLayout;
import java.awt.event.ActionListener;

/**
 * 
 */
public class CCNumPad extends JPanel {

    private static final long serialVersionUID = -464468621586492647L;
    private final CCDisplay display;
    private final CCManager manager;

    /**
     * Numeric keypad component of a calculator.
     * It contains numbers from 0 to 9, decimal point and brackets.
     * 
     * @param disp Display component of the calculator
     * @param mng Manager of the system
     */
    public CCNumPad(final CCDisplay disp, final CCManager mng) {
        this.display = disp;
        this.manager = mng;


        this.setLayout(new GridLayout(5, 3));
        final ActionListener al = e -> {
            final var btn = (JButton) e.getSource();
            this.manager.read(btn.getText());
            this.updateDisplay();
        };

        this.add(createBtn("(", al));
        this.add(createBtn(")", al));
        this.add(createBtn("-", al));
        for (int k = 1; k <= 9; k++){
            this.add(createBtn(String.valueOf(k), al));
        }
        this.add(createBtn(".", al));
        this.add(createBtn("0", al));
        this.add(createBtn("=", (e) -> {
            this.display.updateUpperText(this.manager.getCurrentState().stream().reduce("", (a, b) -> a + " " + b) + " =");
            this.manager.calculate();
            this.updateDisplay();
        }));

    }
    private JButton createBtn(final String s, final ActionListener al) {
        final var btn = new JButton(s);
        btn.addActionListener(al);
        return btn;
    }

    private void updateDisplay() {
        this.display.updateText(this.manager.getCurrentState().stream().reduce("", (a, b) -> a+b));
    }
}
