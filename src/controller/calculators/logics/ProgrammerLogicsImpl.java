package controller.calculators.logics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.calculators.CalculatorController;
import model.calculators.ProgrammerCalculatorModelFactory;
import model.manager.EngineModelInterface.Calculator;
import utils.ConversionAlgorithms;
import view.components.CCDisplay;
/**
 * This class acts as an intermediate between the ProgrammerCalculatorPanel and CalculatorController's engine.
 */
public class ProgrammerLogicsImpl implements InputFormatterLogics, OutputFormatterLogics {
    private int conversionBase = 10;
    private final CalculatorController controller;
    private List<String> buffer;
    private final List<String> tokens;
    private String lastNumBuffer = "";
    private final CCDisplay display;
    /**
     * @param display
     */
    public ProgrammerLogicsImpl(final CCDisplay display) {
        this.display = display;
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
        if ("not".equals(input)) {
            this.handleNotInput();
        } else {
            if (!this.tokens.contains(input)) {
                this.lastNumBuffer = this.lastNumBuffer.concat(input);
            } else {
                this.lastNumBuffer = "";
            }
            this.buffer.add(input);
          this.removeError();
        }
    }
    private void handleNotInput() {
        this.buffer.add(0, "(");
        this.buffer.add(0, "not");
        this.buffer.add(")");
        this.removeError();
        final String before = this.getBuffer();
        this.updateDisplayUpperText();
        this.calculate();
        this.updateDisplay();
        this.addResult(before);
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
    private List<String> formatToDecimal() {
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
    public String getBuffer() {
        return this.buffer.stream().reduce("", (a, b) -> a + b);
    }
    /**
     * This methods uses the engine's calculate() to return the result of its buffer.
     */
    @Override
    public void calculate() {
        if (!this.buffer.isEmpty()) {
            this.removeError();
            final var temp = this.formatToDecimal();
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
    private void removeError() {
        this.buffer.remove("Syntax error");
        this.buffer.remove("Parenthesis mismatch");
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
        //since the IEEE754 hasn't been implemented I simply round the number
        if (this.lastNumBuffer.contains(".")) {
            return Math.round(Double.parseDouble(lastNumBuffer));
        }
        try {
            return ConversionAlgorithms.unsignedConversionToDecimal(conversionBase, lastNumBuffer);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
    @Override
    public void updateDisplay() {
        this.display.updateText(this.getBuffer());
    }
    @Override
    public String format() {
        return this.lastNumBuffer;
    }
    @Override
    public void updateDisplayUpperText() {
        if (!this.getBuffer().isBlank()) {
            display.updateUpperText(this.getBuffer().concat(" ="));
        }
    }
    /**
     * This method adds the last valid Operation to the History.
     * @param before a string containing the last operation executed.
     */
    public void addResult(final String before) {
        if (this.checkForError(before) && !before.isBlank()) {
            final var baseMap = Map.of(2, "₂", 8, "₈", 10, "₁₀", 16, "₁₆");
            final var text = before + " = " + this.format() + " " + baseMap.get(this.conversionBase);
            this.controller.getManager().memory().addResult(text);
        }
    }
}
