package utils;

import java.util.List;

import controller.calculators.CalculatorController;

//TODO javadoc.
/**
 * MISSING JAVADOC.
 *
 */
public class InputFormatter{
    
    private int conversionBase = 10;
    private CalculatorController controller;
    private String buffer = "";
    //TODO MISSING JAVADOC.
    /**
     * missing javadoc.
     * @param controller
     */
    public InputFormatter(final CalculatorController controller) {
        this.controller = controller;
    }
    /**
     * 
     * @param input
     * 
     *  se è in base 10 lo da al controller
     *  altrimenti lo concatena nel buffer
     */
    public void read(final String input) {
        if (this.conversionBase == 10) {
            controller.getManager().read(input);
        }
        else {
            this.buffer = this.buffer.concat(input);
        }
    }
    /**
     * 
     * @param base will be the new conversionBase.
     *  buffer = "";
     *  controller.clear().
     * 
     */
    public void reset(final int base) {
        this.conversionBase = base;
        this.buffer = "";
        controller.getManager().clear();
    }
    /**
     * RIFARE.
     */
    public void format() {
        final String conv = String.valueOf(ConversionAlgorithms.unsignedConversionToDecimal(conversionBase, buffer));
        controller.getManager().readAll(List.of(conv.split("")));
    }
    /**
     * 
     */
    public void deleteLast() {
        /*if (buffer.length() != 0) {
            buffer = buffer.substring(0, buffer.length() - 1);
        }*/
    }
    /**
     * 
     * @return if the current base is not equal to 10 it returns the alphanumeric buffer
     *          otherwise it return the controller's current state
     */
    public String getOutput() {
        return this.conversionBase != 10 ? this.buffer : this.controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b);
    }
    /**
     * buffer = "".
     */
    public void clearBuffer() {
        this.buffer = "";
    }
    
    /**
     * 
     * @return the int of the last current value of input 
     */
    public int getCurrentValue() {
        if (this.conversionBase == 10) {
            return Integer.parseInt(this.getOutput());
        } else {
            return ConversionAlgorithms.conversionToDecimal(conversionBase, buffer);
        }
    }

    private String integerParser(final String input) {
        if (input.contains(".")) {
            if (input.contains("E")) {
                return "0";
            }
            return input.substring(0, input.indexOf('.'));
        }
        return input;
    }
    /**
     * // TODO format every string to a decimal make the controller read it
        //and then make it calculate it.
     */
    public void calculate() {
        
        
    }
}
