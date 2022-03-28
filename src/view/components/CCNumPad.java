package view.components;

import javax.swing.*;

import controller.manager.CCManager;
import utils.AbstractCalculator;


import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public class CCNumPad extends JPanel {

    private static final long serialVersionUID = -464468621586492647L;

    private final Map<String, JButton> buttons = new HashMap<>();

    /**
     * Numeric keypad component of a calculator.
     * It contains numbers from 0 to 9, decimal point and brackets.
     * @param btnAl
     * @param calculateAl
     * @param backspaceAl
     */
    public CCNumPad(final ActionListener btnAl, final ActionListener calculateAl, final ActionListener backspaceAl) {

        this.setLayout(new GridLayout(5, 3));

        final ActionListener al = btnAl;

        this.add(createBtn("(", al));
        this.add(createBtn(")", al));
        this.add(createBtn("🡄", backspaceAl));
        for(int j = 2; j >= 0; j --) {
            for (int k = 1; k <= 3; k++){
                this.add(createBtn(String.valueOf(3 * j + k), al));
            }
        }
        this.add(createBtn(".", al));
        this.add(createBtn("0", al));
        this.add(createBtn("=", calculateAl));

    }
    private JButton createBtn(final String s, final ActionListener al) {
        final var btn = new JButton(s);
        btn.addActionListener(al);
        this.buttons.put(s, btn);
        return btn;
    }
    public Map<String, JButton> getButtons(){
        return this.buttons;
    }
}
