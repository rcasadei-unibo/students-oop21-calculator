package view.calculators;

import java.awt.BorderLayout;
import java.awt.Color;

import model.manager.EngineModelInterface.Calculator;

import javax.swing.JPanel;

import view.components.FunctionGrapher;
import view.components.FunctionsInsertionPanel;
import view.logics.FunctionCalculator;
import view.logics.FunctionCalculatorImpl;
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
     *
     */
    public GraphicCalculatorPanel() {
        final FunctionCalculator calc = new FunctionCalculatorImpl(Calculator.GRAPHIC.getController());
        final FunctionGrapher g = new FunctionGrapher();
        final FunctionsInsertionPanel p = new FunctionsInsertionPanel(calc, g);
        this.setLayout(new BorderLayout());
        this.add(g, BorderLayout.CENTER);
        this.add(p, BorderLayout.SOUTH);
    }
}

