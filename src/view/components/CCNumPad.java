package view.components;


import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;


/**
 * 
 */
public class CCNumPad extends JPanel {

    private static final long serialVersionUID = -464468621586492647L;

    private final Map<String, JButton> buttons = new HashMap<>();

    /**
     * Numeric keypad component of a calculator.
     * It contains numbers from 0 to 9, decimal point, brackets, backspace and equal.
     * @param btnAl Action fired by clicking a number, decimal point or brackets
     * @param calculateAl Action fired by clicking the equal button
     * @param backspaceAl Action fired by clicking the backspace button
     */
    public CCNumPad(final ActionListener btnAl, final ActionListener calculateAl, final ActionListener backspaceAl) {
        final int rows = 5;
        final int cols = 3;
        this.setLayout(new BorderLayout());

        final JPanel grid = new JPanel(new GridLayout(rows, cols));

        final ActionListener al = btnAl;

        grid.add(createBtn("(", al));
        grid.add(createBtn(")", al));
        grid.add(createBtn("🡄", backspaceAl));
        for (int j = 2; j >= 0; j--) {
            for (int k = 1; k <= 3; k++) {
                grid.add(createBtn(String.valueOf(3 * j + k), al));
            }
        }
        grid.add(createBtn(".", al));
        grid.add(createBtn("0", al));
        grid.add(createBtn("=", calculateAl));
        this.add(grid, BorderLayout.CENTER);

    }
    private JButton createBtn(final String s, final ActionListener al) {
        final var btn = new JButton(s);
        btn.addActionListener(al);
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
