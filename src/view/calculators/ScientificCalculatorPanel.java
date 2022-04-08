package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.calculators.CalculatorController;
import model.calculators.ScientificCalculatorModelFactory;
import utils.OpTypeListener;
/**
 * 
 * MISSING JAVADOC.
 *
 */

public class ScientificCalculatorPanel extends StandardCalculatorPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1795172134074823312L;

    /**
     * 
     * @param controller
     * 
     */
    public ScientificCalculatorPanel(final CalculatorController controller) {
        super(controller);
        this.add(this.getScientificOperators(), BorderLayout.WEST);
    }

    private JPanel getScientificOperators() {
       final JPanel scientificOperators = new JPanel();
       scientificOperators.setLayout(new GridLayout(4, 3));
       for (final var entry : ScientificCalculatorModelFactory.create().getBinaryOpMap().entrySet()) {
           final var btn = new JButton(entry.getKey());
           btn.addActionListener(OpTypeListener.getBinaryListener(this.getDisplay(), this.getController()));
           scientificOperators.add(btn);
       }
       for (final var entry : ScientificCalculatorModelFactory.create().getUnaryOpMap().entrySet()) {
           final var btn = new JButton(entry.getKey());
           btn.addActionListener(OpTypeListener.getUnaryListener(this.getDisplay(), this.getController()));
           scientificOperators.add(btn);
       }
       return scientificOperators;
    }

}
