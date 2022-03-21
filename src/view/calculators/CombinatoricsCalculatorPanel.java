package view.calculators;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import utils.AbstractCalculator;
import view.components.CCDisplay;
import view.components.CCNumPad;
/**
 * 
 * MISSING JAVADOC.
 *
 */
public class CombinatoricsCalculatorPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 
     * @param controller
     */
    public CombinatoricsCalculatorPanel(final AbstractCalculator controller) {
        this.setLayout(new BorderLayout());
        final var display = new CCDisplay();
        this.add(display, BorderLayout.NORTH);
        controller.setDisplay(display);
        final var numpad = new CCNumPad(display, controller.getManager());
        numpad.getButtons().get("-").setEnabled(false);
        numpad.getButtons().get("(").setEnabled(false);
        numpad.getButtons().get(")").setEnabled(false);
        numpad.getButtons().get(".").setEnabled(false);
        this.add(numpad, BorderLayout.CENTER);
    }
}
