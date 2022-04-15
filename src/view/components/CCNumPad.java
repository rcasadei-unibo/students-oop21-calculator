package view.components;


import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import utils.CCColors;

import java.awt.Dimension;


/**
 * Numeric keypad component of a calculator.
 * It contains numbers from 0 to 9, decimal point, brackets, backspace and equal.
 */
public class CCNumPad extends JPanel {

    private static final long serialVersionUID = -464468621586492647L;

    private final Map<String, JButton> buttons = new HashMap<>();

    /**
     * Constructs a numeric keypad component for a calculator.
     * It contains numbers from 0 to 9, decimal point, brackets, backspace and equal.
     * @param btnAl Action fired by clicking a number, decimal point or brackets
     * @param calculateAl Action fired by clicking the equal button
     * @param backspaceAl Action fired by clicking the backspace button
     */
    public CCNumPad(final ActionListener btnAl, final ActionListener calculateAl, final ActionListener backspaceAl) {
        final int rows = 5;
        final int cols = 3;
        this.setLayout(new GridLayout(rows, cols));
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double width = screenSize.getWidth() * 0.35;
        final double height = screenSize.getHeight() / 2;
        this.setPreferredSize(new Dimension((int) width, (int) height));

        final ActionListener al = btnAl;

        this.add(createBtn("(", al));
        this.add(createBtn(")", al));
        this.add(createBtn("⌫", backspaceAl));
        for (int j = 2; j >= 0; j--) {
            for (int k = 1; k <= 3; k++) {
                this.add(createBtn(String.valueOf(3 * j + k), al));
            }
        }
        this.add(createBtn(".", al));
        this.add(createBtn("0", al));
        final var eqBtn = createBtn("=", calculateAl);
        eqBtn.setBackground(CCColors.EQUAL_BUTTON);
        this.add(eqBtn);

    }
    private JButton createBtn(final String s, final ActionListener al) {
        final var btn = new JButton(s);
        btn.addActionListener(al);
        btn.setBackground(CCColors.NUMBER_BUTTON);
        this.buttons.put(s, btn);
        return btn;
    }
    /**
     * Returns the mapping from a string to the button that has that string as text.
     * @return Map from a string to a button
     */
    public Map<String, JButton> getButtons() {
        return this.buttons;
    }
}
