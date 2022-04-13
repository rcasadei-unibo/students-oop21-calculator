package view.calculators;

import java.awt.BorderLayout;

import javax.swing.JButton;
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
        final FunctionGrapher g = new FunctionGrapher();
        this.setLayout(new BorderLayout());
        this.add(g, BorderLayout.CENTER);
        final JButton b = new JButton("x+x-3");
        b.addActionListener(e -> {
        });
    }
}
