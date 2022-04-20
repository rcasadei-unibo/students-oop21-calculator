package view.logics;
import java.util.ArrayList;
import java.util.List;
import controller.calculators.CalculatorController;
import model.calculators.ProgrammerCalculatorModelFactory;
import model.manager.EngineModelInterface.Calculator;
import utils.ConversionAlgorithms;
/**
 * This class acts as an intermediate between the ProgrammerCalculatorPanel and CalculatorController's engine.
 */
public class ProgrammerInputFormatter implements InputFormatterLogics {
    private int conversionBase = 10;
    private final CalculatorController controller;
    private List<String> buffer;
    private final List<String> tokens;
    private String lastNumBuffer = "";
    private String history = "";
    /**
     * 
     */
    public ProgrammerInputFormatter() {
        this.controller = Calculator.PROGRAMMER.getController();
        this.buffer = new ArrayList<>();
        this.tokens = new ArrayList<>();
        this.addTokens();
    }
    /**
     * Tokens are every non-number possible input.
     */
    private void addTokens() {
        tokens.add("(");
        tokens.add(")");
        ProgrammerCalculatorModelFactory.create().getBinaryOpMap().entrySet().stream().forEach((entry) -> tokens.add(entry.getKey()));
        ProgrammerCalculatorModelFactory.create().getUnaryOpMap().entrySet().stream().forEach((entry) -> tokens.add(entry.getKey()));
    }
    /**
     * This methods reads from the button pressed.
     * @param input
     */
    @Override
    public void read(final String input) {
        //this.removeSyntaxError(this.buffer);
        if ("not".equals(input)) {
            this.buffer.add(0, "(");
            this.buffer.add(0, "not");
            this.buffer.add(")");
            this.calculate();
            this.updateHistory();
        } else {
            if (!this.tokens.contains(input)) {
                this.lastNumBuffer = this.lastNumBuffer.concat(input);
            } else {
                this.lastNumBuffer = "";
            }
            this.buffer.add(input);
        }
    }
    /**
     * Usually called when switching from a conversion base to another.
     * This method clears:
     * -the engine's memory
     * -the value converted
     * -the current buffer
     * @param base will be the new conversionBase.
     */
    public void reset(final int base) {
        this.conversionBase = base;
        this.buffer.clear();
        this.controller.getManager().memory().clear();
        this.lastNumBuffer = "";
    }
    /**
     * This method searches the buffer "(" ")" and operators and converts every possible alpha-numeric
     * string to its decimal value.
     * ["(","F","F","+","0","1",")"]
     * ["(","255","+","1",")"]
     * 
     * @return the new converted list.
     */
    private List<String> format() {
        if (this.conversionBase == 10) {
            return this.buffer;
        }
        String strNumber = "";
        long intNumber;
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
        }
        return formattedList;
    }
    /**
     * This methods deletes the last input.
     */
    @Override
    public void deleteLast() {
        if ("Syntax error".equals(lastNumBuffer)) {
            this.reset(conversionBase);
        }
        if (!this.buffer.isEmpty()) {
            this.buffer.remove(this.buffer.size() - 1);
            final List<String> numbers = new ArrayList<>();
            this.buffer.stream().forEach((str) -> {
                if (!this.tokens.contains(str)) {
                    numbers.add(str);
                } else {
                    numbers.clear();
                }
            });
            this.lastNumBuffer = numbers.stream().reduce("", (a, b) -> a + b);
        }
    }
    /**
     * 
     * @return a String containing the current buffer converted to String.
     */
    public String getOutput() {
        return this.buffer.stream().reduce("", (a, b) -> a + b);
    }
    /**
     * This methods uses the engine's calculate() to return the result of its buffer.
     */
    @Override
    public void calculate() {
        if (!this.buffer.isEmpty()) {
            this.removeSyntaxError(this.buffer);
            this.history = this.buffer.stream().reduce("", (a, b) -> a + b);
            final var temp = this.format();
            this.controller.getManager().memory().readAll(temp);
            this.controller.getManager().engine().calculate();
            this.buffer.clear();
            if (this.controller.getManager().memory().getCurrentState().stream().reduce("", (a, b) -> a + b).contains("-")) {
                List.of(this.controller.getManager().memory().getCurrentState().get(0).split("")).stream().forEach((str) -> this.buffer.add(str));
            } else {
                this.buffer.addAll(this.controller.getManager().memory().getCurrentState());
            }
            this.inverseFormat();
            this.lastNumBuffer = this.buffer.stream().reduce("", (a, b) -> a + b);
            this.controller.getManager().memory().clear();
        }
    }
    private void removeSyntaxError(final List<String> input) {
        input.remove("Syntax error");
    }
    private void inverseFormat() {
        if (this.conversionBase != 10) {
            final var toChange = new ArrayList<String>();
            String toConv = "";
            for (final var num : this.buffer) {
                if (!this.tokens.contains(num)) {
                    toConv = toConv.concat(num);
                } else {
                    toChange.add(num);
                }
            }
            final var value = ConversionAlgorithms.conversionToStringBase(conversionBase, (long) Double.parseDouble(toConv)).replace("+", "");
            List.of(value.split("")).forEach((str) -> toChange.add(str));
            this.buffer = toChange;
        }
    }
    /**
     * @return the last input value
     * if input == "A2" it will convert it and return 162
     * if input == "A2+F" it will return the conversion of F=>15
     */
    public long getLastValue() {
        int sign = 1;
        if (this.lastNumBuffer.contains("-")) {
            this.lastNumBuffer = this.lastNumBuffer.replace("-", "");
            sign = -1;
        }
        final var value = this.adjust();
        return sign * value;
    }
    private long adjust() {
        if (this.lastNumBuffer.contains(".")) {
            return Math.round(Double.parseDouble(lastNumBuffer));
        }
        if ("Syntax error".equals(lastNumBuffer)) {
            return 0L;
        }
        if (!this.lastNumBuffer.isBlank()) {
            return ConversionAlgorithms.unsignedConversionToDecimal(conversionBase, lastNumBuffer);
        }
        return ConversionAlgorithms.unsignedConversionToDecimal(conversionBase, lastNumBuffer);
    }
    /**
     * Updates the memory's history.
     */
    public void updateHistory() {
        if (!this.lastNumBuffer.contains("Syntax error")) {
            controller.getManager().memory().addResult(history.concat(" =").concat(lastNumBuffer));
        }
    }
}
