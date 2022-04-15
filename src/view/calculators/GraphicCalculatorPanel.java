package view.calculators;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
        final FunctionGrapher g = new FunctionGrapher();
        this.setLayout(new BorderLayout());
        this.add(g, BorderLayout.CENTER);
        final JButton b = new JButton("CREATE)");
        final JTextField t = new JTextField();
        this.add(t, BorderLayout.SOUTH);
        this.add(b, BorderLayout.NORTH);
        b.addActionListener(e -> {
            logics.setEquation(t.getText());
            g.paint(logics.getResult());
        });
    }
}
