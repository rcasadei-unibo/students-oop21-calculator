package view.calculators;

import view.components.CCDisplay;
import view.components.CCNumPad;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.manager.CCManager;
import utils.AbstractCalculator;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 
 * MISSING JAVADOC.
 *
 */

public class ScientificCalculatorPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 2101590681430721350L;
    /**
     *@param controller
     * 
     */
    public ScientificCalculatorPanel(final AbstractCalculator controller) {
        final JPanel display = new CCDisplay();
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);

        this.add(new NumPadPanel(), BorderLayout.SOUTH);


    }

    static class NumPadPanel extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = 6923624366313497579L;

         NumPadPanel() {
            this.setLayout(new GridLayout(7,5));
            final JButton log = new JButton("log");
            log.addActionListener(e -> {

            });

        }
    }
}
