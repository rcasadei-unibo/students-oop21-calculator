package utils;

import controller.calculators.CalculatorController;

//TODO javadoc.
/**
 * MISSING JAVADOC.
 * @author Utente
 *
 */
public class InputFormatter{
    
    private int conversionBase = 10;
    private CalculatorController controller;
    private String buffer = "";
    
    public InputFormatter(final CalculatorController controller) {
        this.controller = controller;
    }
    public void read(final String input) {
        if (this.conversionBase == 10) {
            controller.getManager().read(input);
        }
        else {
            this.buffer = this.buffer.concat(input);
        }
    }
    public void reset(final int base) {
        this.conversionBase = base;
        this.buffer = "";
        controller.getManager().clear();
    }
    public void format() {
        
    }
    
}
