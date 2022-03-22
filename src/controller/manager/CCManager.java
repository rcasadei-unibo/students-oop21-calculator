package controller.manager;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

import utils.AbstractCalculator;
import utils.CalcException;
import utils.Type;
import model.manager.CCManagerModel;
import model.manager.CCManagerModel.Calculator;

/**
 * 
 *
 */
public class CCManager {

    private final CCManagerModel model = new CCManagerModel();

    /**
     * MISSING JAVADOC.
     */
    public CCManager() {
        for (final Calculator calc : Calculator.values()) {
            calc.getController().setManager(this);
        }
    }

    /**
     * @param s input string to be read
     */
    public void read(final String s) {
        if ("-".equals(s)) {
            final var buffer = model.getCurrentState();
            if (buffer.isEmpty() || buffer.get(buffer.size() - 1) == "(") {
                model.addInput("0");
            }
        }
        model.addInput(s);
    }

    /**
     * 
     * @param list
     */
    public void readAll(final List<String> list) {
        list.forEach(s -> this.read(s));
    }

    /**
     * 
     */
    public void printCurrentState() {
        System.out.println(model.getCurrentState());
    }

    /**
     * 
     * @return current state of the input list
     */
    public List<String> getCurrentState() {
        return model.getCurrentState();
    }

    /**
     * removes all elements from input list.
     */
    public void clear() {
        model.clearBuffer();
    }

    /**
     * 
     * @param calcName
     */
    public void mount(final Calculator calcName) {
        this.model.setMounted(calcName);
        this.clear();
//        this.getMounted().getController().setManager(this);
    }
    /**
     * 
     * @return ..
     */
    public Calculator getMounted() {
        return this.model.getMounted();
    }

    /**
     * 
     */
    public void deleteLast() {
        final var newState = this.model.getCurrentState();
        this.clear();
        IntStream.range(0, newState.size() - 1).forEach(i -> this.read(newState.get(i)));
    }

    /**
     * 
     */
    public void calculate()  {
        final List<String> input = model.getCurrentState();
        List<String> rpnInput;
        try {
            rpnInput = this.parseToRPN(this.unifyTerms(input));
            final double result = this.evaluateRPN(rpnInput);
            model.setCurrentState(String.valueOf(result));
        } catch (EmptyStackException e) {
            model.setCurrentState("Syntax error");
        } catch (CalcException e) {
            //e.printStackTrace();
            model.setCurrentState(e.getMessage());
        }
    }

    private List<String> unifyTerms(final List<String> input) {
        final List<String> unified = new ArrayList<>();

        final List<String> currentNumber = new ArrayList<>();
        input.forEach((s) -> {
            if (isNumber(s) || ".".equals(s)) {
                currentNumber.add(s);
            } else {
                 if (!currentNumber.isEmpty()) {
                    final double actualNum = convert(currentNumber);
                    currentNumber.clear();
                    unified.add(String.valueOf(actualNum));
                 }
                unified.add(s);
            }
        });
        if (!currentNumber.isEmpty()) {
            final double actualNum = convert(currentNumber);
            currentNumber.clear();
            unified.add(String.valueOf(actualNum));
        }
        return unified;
    }

    private double convert(final List<String> currentNumber) {
        final String num = currentNumber.stream().reduce("", (a, b) -> a + b);
        return Double.valueOf(num);
    }

    private List<String> parseToRPN(final List<String> infix) throws CalcException {

        final List<String> output = new ArrayList<>();
        final Stack<String> stack = new Stack<>();

        final var it = infix.iterator();
        while (it.hasNext()) {

            final String token = it.next();
            if (isNumber(token)) {
                output.add(token);
            } else if (isFunction(token)) {
                stack.add(token);
            } else if (isOperator(token)) {

                if (!stack.isEmpty()) {
                    String o2 = stack.lastElement();
                    while (!"(".equals(o2) && (precedence(o2) > precedence(token) || (precedence(o2) == precedence(token) && type(token) == Type.LEFT))) {
                        output.add(stack.pop());
                        if (stack.isEmpty()) {
                            break;
                        }
                        o2 = stack.lastElement();
                    }
                }

                stack.add(token);

            } else if ("(".equals(token)) {
                stack.add(token);
            } else if (")".equals(token)) {
                while (!stack.isEmpty() && !"(".equals(stack.lastElement())) {
                    output.add(stack.pop());
                }
                if (!stack.isEmpty() && "(".equals(stack.lastElement())) {
                    stack.pop();
                } else {
                    throw new CalcException("Error: parenthesis mismatch");
                }
                if (!stack.isEmpty() && isFunction(stack.lastElement())) {
                    output.add(stack.pop());
                }
            }

        }

        while (!stack.isEmpty()) {
            if ("(".equals(stack.lastElement())) {
                throw new CalcException("Error: parenthesis mismatch");
            }
            output.add(stack.pop());
        }

        return output;
    }

    private double evaluateRPN(final List<String> rpn) throws CalcException {
        //https://rosettacode.org/wiki/Parsing/RPN_calculator_algorithm#Java_2
        final Stack<Double> stack = new Stack<>();

        final var it = rpn.iterator();
        while (it.hasNext()) {
            final String token = it.next();

            if (isNumber(token)) {
                stack.add(Double.valueOf(token));
            } else if (isOperator(token)) {
                final double secondOperand = Double.valueOf(stack.pop());
                final double firstOperand = Double.valueOf(stack.pop());
                stack.add(getCalculator().applyBinaryOperation(token, firstOperand, secondOperand));
            } else if (isFunction(token)) {
                final double firstOperand = Double.valueOf(stack.pop());
                stack.add(getCalculator().applyUnaryOperation(token, firstOperand));
            }
        }

        if (stack.size() > 1) {
            throw new CalcException("Error: too many operands");
        }
        return stack.pop();
    }

    private AbstractCalculator getCalculator() {
        return this.model.getMounted().getController();
    }

    private boolean isFunction(final String token) {
        return getCalculator().isUnaryOperator(token);
    }

    private boolean isOperator(final String token) {
        return getCalculator().isBinaryOperator(token);
    }

    private boolean isNumber(final String token) {
        try {
            Double.valueOf(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private Type type(final String token) {
        return getCalculator().getType(token);
    }

    private int precedence(final String token) {
        return getCalculator().getPrecedence(token);
    }

    

}
