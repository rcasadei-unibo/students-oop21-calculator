package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.manager.CCManager;
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
        numpad.getButtons().get(".").setEnabled(false);
        this.add(numpad, BorderLayout.CENTER);
        this.add(new OperationsPanel(controller.getManager(), display), BorderLayout.EAST);
    }
    static class OperationsPanel extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        OperationsPanel(final CCManager ccManager, final CCDisplay display) {
            this.setLayout(new GridLayout(2, 1));
            final var part = new JButton("Partizioni");
            part.addActionListener(e -> {
                ccManager.read("Bell number");
                display.updateText(ccManager.getCurrentState().stream().reduce("", (a, b) -> a + b));
            });
            this.add(part);
            final var fact = new JButton("Factorial");
            fact.addActionListener(e -> {
                ccManager.read("factorial");
                display.updateText(ccManager.getCurrentState().stream().reduce("", (a, b) -> a + b));
            });
            this.add(fact);
        }
    }
}
