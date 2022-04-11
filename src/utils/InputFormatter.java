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
    private final CalculatorController controller;
    private List<String> buffer;
    private final List<String> tokens;
    private String lastNumBuffer = "";
    //TODO Pressing C in hexadecimal does not change the conversionPanel output altho upon deletingLast() it updates

    //TODO no hexadecimal operation works

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
     * i token comprendono tutte le stringhe che non sono valori alphanumerici utilizzati per le conversioni.
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
        if (!this.tokens.contains(input)) {
            this.lastNumBuffer = this.lastNumBuffer.concat(input);
        } else {
            this.lastNumBuffer = "";
        }
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
        this.controller.getManager().memory().clear();
        this.lastNumBuffer = "";
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
        if (this.conversionBase == 10) {
            return this.buffer;
        }
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
        if (!strNumber.isBlank()) {
            intNumber = ConversionAlgorithms.unsignedConversionToDecimal(conversionBase, strNumber);
            strNumber = String.valueOf(intNumber);
            List.of(strNumber.split("")).stream().forEach((dec) -> formattedList.add(dec));
            strNumber = "";
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
            if (this.lastNumBuffer.length() > 1) {
                this.lastNumBuffer = this.lastNumBuffer.substring(0, this.lastNumBuffer.length() - 1);
            } else {
                this.lastNumBuffer = "";
            }
        }
        //TODO 
    }
    /**
     * 
     * @return if the current base is not equal to 10 it returns the alphanumeric buffer
     *          otherwise it return the controller's current state
     */
    public String getOutput() {
        //System.out.println("my buffer's out" + this.buffer.toString());
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
     * dopo aver formattato tutto calcola il risultato e diventa il lastNumBuffer che poi verrà mostrato.
     */
    public void calculate() {
        //System.out.println("the engine before" + this.controller.getManager().memory().getCurrentState().toString());
        final var temp = this.format();
        //System.out.println("my input to the engine" + temp.toString());
        this.controller.getManager().memory().readAll(temp);
        this.controller.getManager().engine().calculate();
        //System.out.println("the engine's output" + this.controller.getManager().memory().getCurrentState().toString());
        //this.buffer.clear();
        this.lastNumBuffer = this.controller.getManager().memory().getCurrentState().stream().reduce("", (a, b) -> a + b);
        //System.out.println("before calc" + this.buffer.toString());
        this.buffer.clear();
        this.buffer.addAll(this.controller.getManager().memory().getCurrentState());
        //System.out.println("after calc" + this.buffer.toString());
        //this.buffer.addAll(this.controller.getManager().memory().getCurrentState());
        //System.out.println("clearing the engine's memory");
        this.controller.getManager().memory().clear();
        
        System.out.println("this is the result : " + lastNumBuffer);
    }
    /**
     * 
     * @return the last input value
     * 
     * if input = "A2" it will convert it and return 162
     * if input = "A2+F" it will return the conversion of F=>15
     */
    public int getLastValue() {
        System.out.println("my last num buffer's value" + lastNumBuffer);
        
        this.formatToNumbers();
        
        
        
        if (this.conversionBase == 10) {
            return Integer.parseInt(lastNumBuffer);
        }
        System.out.println("!isBlank(): " + !this.lastNumBuffer.isBlank() + " !equals(Syntax error): " + !this.lastNumBuffer.equals("Syntax error"));
        if (!this.lastNumBuffer.isBlank() && !this.lastNumBuffer.equals("Syntax error")) {
            System.out.println("the value converted is " + ConversionAlgorithms.conversionToDecimal(conversionBase, lastNumBuffer));
            return ConversionAlgorithms.conversionToDecimal(conversionBase, lastNumBuffer); 
        }
        return 0;
    }
    private void formatToNumbers() {
        
        if (this.lastNumBuffer.contains("E")) {
            System.out.println("oh shit");
        }
        
    }
}
