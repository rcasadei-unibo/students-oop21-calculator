package view.temp;

import java.awt.BorderLayout;

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
        this.add(new CCNumPad(display, controller.getManager()), BorderLayout.CENTER);


    }
}
