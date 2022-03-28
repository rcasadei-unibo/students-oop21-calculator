package controller.calculators;


import model.calculators.StandardCalculatorModel;
import utils.AbstractCalculator;

import view.calculators.StandardCalculatorPanel;
/**
 * 
 * 
 *
 */
public class StandardCalculatorController extends AbstractCalculator{
    /**
     * 
     */
    public StandardCalculatorController() {
        this.model = new StandardCalculatorModel();
        this.panel = new StandardCalculatorPanel();
    }
        
    
}
