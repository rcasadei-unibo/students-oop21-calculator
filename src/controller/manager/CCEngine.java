package controller.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import controller.calculators.CalculatorTemplate;
import utils.CalcException;
import utils.Type;

/**
 * 
 */
public class CCEngine implements EngineInterface {

    private final CalculatorTemplate calcController;

    /**
     * 
     * @param calcController
     */
    public CCEngine(final CalculatorTemplate calcController) {
        this.calcController = calcController;
    }

    @Override
    public double calculate(final List<String> input) throws CalcException {
        List<String> rpnInput;

        rpnInput = this.parseToRPN(this.unifyTerms(input));
        return this.evaluateRPN(rpnInput);
    }

    private List<String> unifyTerms(final List<String> input) throws CalcException {
        final List<String> unified = new ArrayList<>();

        final List<String> currentNumber = new ArrayList<>();

        for (final String s : input) {
            if (isNumber(s) || ".".equals(s)) {
                currentNumber.add(s);
            } else {
                 if (!currentNumber.isEmpty()) {
                    double actualNum;
                    actualNum = convert(currentNumber);
                    currentNumber.clear();
                    unified.add(String.valueOf(actualNum));
                 }
                unified.add(s);
            }
        }

        if (!currentNumber.isEmpty()) {
            final double actualNum = convert(currentNumber);
            currentNumber.clear();
            unified.add(String.valueOf(actualNum));
        }
        return unified;
    }

    private double convert(final List<String> currentNumber) throws CalcException {
        if (currentNumber.stream().filter(s -> ".".equals(s)).count() > 1) {
            throw new CalcException("Syntax error");
        }
        final String num = currentNumber.stream().reduce("", (a, b) -> a + b);
        return Double.parseDouble(num);

    }

    private List<String> parseToRPN(final List<String> infix) throws CalcException {

        final List<String> output = new ArrayList<>();
        final Stack<String> stack = new Stack<>();

        final var it = infix.iterator();
        while (it.hasNext()) {

            final String token = it.next();
            if (isNumber(token)) {
                output.add(token);
            } else if (isUnaryOperator(token)) {
                stack.add(token);
            } else if (isBinaryOperator(token)) {

                if (!stack.isEmpty()) {
                    String o2 = stack.lastElement();
                    while (!"(".equals(o2) && (precedence(o2) > precedence(token) || precedence(o2) == precedence(token) && type(token) == Type.LEFT)) {
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
                    throw new CalcException("Parenthesis mismatch");
                }
                if (!stack.isEmpty() && isUnaryOperator(stack.lastElement())) {
                    output.add(stack.pop());
                }
            }

        }

        while (!stack.isEmpty()) {
            if ("(".equals(stack.lastElement())) {
                throw new CalcException("Parenthesis mismatch");
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
            } else if (isBinaryOperator(token)) {
                if (stack.size() < 2) {
                    throw new CalcException("Syntax error");
                }
                final double secondOperand = Double.valueOf(stack.pop());
                final double firstOperand = Double.valueOf(stack.pop());
                stack.add(getCalculator().applyBinaryOperation(token, firstOperand, secondOperand));
            } else if (isUnaryOperator(token)) {
                if (stack.isEmpty()) {
                    throw new CalcException("Syntax error");
                }
                final double firstOperand = Double.valueOf(stack.pop());
                stack.add(getCalculator().applyUnaryOperation(token, firstOperand));
            }
        }

        if (stack.size() > 1 || stack.isEmpty()) {
            throw new CalcException("Syntax Error");
        }
        return stack.pop();
    }

    private CalculatorTemplate getCalculator() {
        return this.calcController;
    }

    private boolean isUnaryOperator(final String token) {
        return getCalculator().isUnaryOperator(token);
    }

    private boolean isBinaryOperator(final String token) {
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
