package view.calculators;

import java.awt.BorderLayout;

import model.manager.EngineModelInterface.Calculator;

import javax.swing.JPanel;

import view.components.FunctionGrapher;
import view.components.FunctionsInsertionPanel;
import view.logics.FunctionCalculator;
import view.logics.FunctionCalculatorImpl;
/**
 * 
 * Graphic Calculator GUI.
 * 
 */
public class GraphicCalculatorPanel extends JPanel {
    private static final long serialVersionUID = 8441953837746059516L;
    /**
     *
     *Puts toghether the FunctionGrapher component and the FunctionsInsertionPanel component in a single border layout JPanel.
     *The two components communicate between each other in order to paint or delete the given functions.
     *
     */
    public GraphicCalculatorPanel() {
        this.setLayout(new BorderLayout());
        final FunctionGrapher g = new FunctionGrapher();
        final FunctionsInsertionPanel p = new FunctionsInsertionPanel(g);
        this.add(g, BorderLayout.CENTER);
        this.add(p, BorderLayout.SOUTH);
    }
}

