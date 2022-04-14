package view.calculators;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.calculators.CalculatorController;
import view.components.FunctionGrapher;
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
        final FunctionGrapher g = new FunctionGrapher(logics);
        this.setLayout(new BorderLayout());
        this.add(g, BorderLayout.CENTER);
        final JButton b = new JButton("sin(x)");
        this.add(b, BorderLayout.NORTH);
        b.addActionListener(e -> {
            logics.setEquation(b.getText());
            g.safe(logics.getResult());
        });
    }
}
