package view.calculators;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import controller.calculators.CalculatorController;
import view.components.FunctionGrapher;
import view.components.FunctionsInsertionPanel;
/**
 * 
 *
 */
public class GraphicCalculatorPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 8441953837746059516L;
    /**
     *@param controller
     */
    public GraphicCalculatorPanel(final CalculatorController controller) {
        final GraphicCalculatorLogics logics = new GraphicCalculatorLogicsImpl(controller);
        final FunctionGrapher g = new FunctionGrapher();
        final FunctionsInsertionPanel p = new FunctionsInsertionPanel(logics, g);
        this.setLayout(new BorderLayout());
        this.add(g, BorderLayout.CENTER);
        this.add(p, BorderLayout.SOUTH);
    }
}

