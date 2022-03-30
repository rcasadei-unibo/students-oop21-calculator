package view.temp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.temp.TempCalculator;
import view.components.CCDisplay;
import view.components.CCNumPad;

/**
 * 
 */
public class TempCalcGUI extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 5985027936982853764L;

    /**
     * 
     * @param controller
     */
    public TempCalcGUI(final TempCalculator controller) {
        this.setLayout(new BorderLayout());

        final var display = new CCDisplay();
        this.add(display, BorderLayout.NORTH);
        controller.setDisplay(display);

        final ActionListener al1 = e -> {
            final var btn = (JButton) e.getSource();
            controller.getManager().read(btn.getText());
            display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
        };
        final ActionListener al2 = (e) -> {
            display.updateUpperText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + " " + b) + " =");
            controller.getManager().calculate();
            display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
        };
        final ActionListener al3 = (e) -> {
            controller.getManager().deleteLast();
            display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
        };

        this.add(new CCNumPad(al1, al2, al3), BorderLayout.CENTER);

        final JPanel left = new JPanel(new GridLayout(3, 3));
        left.add(createBtn("+", al1));
        left.add(createBtn("-", al1));
        left.add(createBtn("*", al1));
        left.add(createBtn("/", al1));
        left.add(createBtn("^", al1));
        left.add(createBtn("cos", al1));
        left.add(createBtn("sin", al1));

        this.add(left, BorderLayout.EAST);

    }

    private JButton createBtn(final String s, final ActionListener al) {
        final var btn = new JButton(s);
        btn.addActionListener(al);
        return btn;
    }
}
