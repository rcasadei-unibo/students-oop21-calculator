package utils;

import java.util.ArrayList;
import java.util.List;

import controller.calculators.CalculatorController;
import model.calculators.ProgrammerCalculatorModelFactory;

//TODO javadoc.
/**
 * MISSING JAVADOC.
 *
 */
public class InputFormatter{
    private int conversionBase = 10;
    private CalculatorController controller;
    private final List<String> buffer;
    private final List<String> tokens;
    //TODO MISSING JAVADOC.
    /**
     * missing javadoc.
     * @param controller
     */
    public InputFormatter(final CalculatorController controller) {
        this.controller = controller;
        this.buffer = new ArrayList<>();
        this.tokens = new ArrayList<>();
        this.addTokens();
    }
    /**
     * i token comprendono tutte le stringhe che non sono valori alphanumerici utilizzati per le conversioni
     */
    private void addTokens() {
        tokens.add("(");
        tokens.add(")");
        ProgrammerCalculatorModelFactory.create().getBinaryOpMap().entrySet().stream().forEach((entry) -> tokens.add(entry.getKey()));
        ProgrammerCalculatorModelFactory.create().getUnaryOpMap().entrySet().stream().forEach((entry) -> tokens.add(entry.getKey()));
    }
    /**
     * 
     * @param input
     * 
     *  se è in base 10 lo da al controller
     *  altrimenti lo concatena nel buffer
     */
    public void read(final String input) {
        this.buffer.add(input);
    }
    /**
     * usually called when switching from a conversion base to another.
     * @param base will be the new conversionBase.
     *  buffer.clear();
     *  controller.clear().
     * 
     */
    public void reset(final int base) {
        this.conversionBase = base;
        this.buffer.clear();
        this.controller.getManager().clear();
    }
    /**
     * questo metodo cerca all'interno di tutto il buffer per "(" ")" "operatori" e converte
     * ogni possibile codice alfanumerico in un numero intero da poi passare al controller.
     * ["(","F","F","+","0","1",")"]
     * ["(","255","+","1",")"]
     * 
     * @return a.
     */
    private List<String> format() {
        String strNumber = "";
        int intNumber = 0;
        final List<String> formattedList = new ArrayList<>();
        for (final var str : this.buffer) {
            if (!this.tokens.contains(str)) {
                strNumber = strNumber.concat(str);
            } else {
                if (!strNumber.isBlank()) {
                    intNumber = ConversionAlgorithms.unsignedConversionToDecimal(conversionBase, strNumber);
                    strNumber = String.valueOf(intNumber);
                    List.of(strNumber.split("")).stream().forEach((dec) -> formattedList.add(dec));
                    strNumber = "";
                }
                    formattedList.add(str);
            }
        }
        return formattedList;
    }
    /**
     * esattamente come il getManager().deleteLast() cancella l'ultimo valore inserito nel buffer
     * quando si cambia base di conversione si cancella tutto.
     */
    public void deleteLast() {
        if (!this.buffer.isEmpty()) {
            this.buffer.remove(this.buffer.size() - 1);
        }
    }
    /**
     * 
     * @return if the current base is not equal to 10 it returns the alphanumeric buffer
     *          otherwise it return the controller's current state
     */
    public String getOutput() {
        return this.buffer.stream().reduce("", (a, b) -> a + b);
    }
    /**
     * 
     * @return the int of the last current value of input 
     */

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
        this.format();
        this.controller.getManager().readAll(buffer);
        this.controller.getManager().calculate();
    }
    /**
     * 
     * @return the last input value
     * 
     * if input = "A2" it will convert it and return 162
     * if input = "A2+F" it will return the conversion of F=>15
     */
    public int getLastValue() {
        return 0;
    }
}
