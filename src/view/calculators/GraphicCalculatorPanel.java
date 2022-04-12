package view.calculators;

import java.awt.BorderLayout;

import javax.swing.JTextField;
import javax.swing.JPanel;

import controller.calculators.CalculatorController;
import view.components.FunctionGrapher;
/**
 * 
 * 
 *
 */
public class GraphicCalculatorPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 8441953837746059516L;
    /**
     * 
     * @param controller
     */
    public GraphicCalculatorPanel(final CalculatorController controller) {
        this.setLayout(new BorderLayout());
        this.add(new FunctionGrapher(), BorderLayout.CENTER);
        final JTextField t = new JTextField("aaa");
        this.add(t, BorderLayout.NORTH);
    }
}
