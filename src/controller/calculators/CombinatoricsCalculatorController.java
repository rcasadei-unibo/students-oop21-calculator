package controller.calculators;

import javax.swing.JPanel;

import model.calculators.CalculatorModel;
import model.calculators.CombinatoricsCalculatorModel;
import utils.AbstractCalculator;
import view.calculators.CombinatoricsCalculatorPanel;

/**
 * 
 * MISSING JAVADOC.
 *
 */
public class CombinatoricsCalculatorController extends AbstractCalculator {
    @Override
    public void setCalculator() {
       this.model = new CombinatoricsCalculatorModel();
       this.panel = new CombinatoricsCalculatorPanel();
    }
}
