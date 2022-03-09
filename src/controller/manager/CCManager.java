package controller.manager;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import controller.temp.AbstractCalculator;
import controller.temp.TempCalculator;
import model.manager.CCManagerModel;
import view.main.CCMainGUI;

/**
 * 
 *
 */
public class CCManager {

    private final CCManagerModel model = new CCManagerModel();
    private final CCMainGUI view = new CCMainGUI();

    /**
     * 
     * @param s input string to be read
     */
    public void read(final String s) {
        model.addInput(s);
//        this.printCurrentState();
    }

    /**
     * 
     */
    public void printCurrentState() {
        view.log(model.getCurrentState());
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
    public void mount(final CCManagerModel.Calculator calcName) {
        this.model.setMounted(calcName);
    }

    /**
     * 
     */
    public void calculate()  {
        final List<String> input = model.getCurrentState();
        List<String> rpnInput;
        try {
            rpnInput = this.parseToRPN(this.unifyTerms(input));
//            view.log("RPN:");
//            view.log(rpnInput);

            final double result = this.evaluateRPN(rpnInput);
//            view.log("Result: ");
            model.setCurrentState(String.valueOf(result));
        } catch (EmptyStackException e) {
            model.setCurrentState("Syntax error");
        } catch (Exception e) {
//            e.printStackTrace();
            model.setCurrentState(e.getMessage());
        }
    }

    private List<String> unifyTerms(final List<String> input) {
        final List<String> unified = new ArrayList<>();

        final List<String> currentNumber = new ArrayList<>();
        input.forEach((s) -> {
            if (isNumber(s) || s == ".") {
                currentNumber.add(s);
//                view.log(currentNumber);
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

    private List<String> parseToRPN(final List<String> infix) throws Exception {

        final List<String> output = new ArrayList<>();
        final Stack<String> stack = new Stack<>();

        final var it = infix.iterator();
        while (it.hasNext()) {

            String token = it.next();
            if (isNumber(token)) {
                output.add(token);
            } else if (isFunction(token)) {
                stack.add(token);
            } else if (isOperator(token)) {

                if (!stack.isEmpty()) {
                    String o2 = stack.lastElement();
                    while (o2 != "(" && (precedence(o2) > precedence(token) || (precedence(o2) == precedence(token) && type(token) == "left"))) {
                        output.add(stack.pop());
                        if (stack.isEmpty()) {
                            break;
                        }
                        o2 = stack.lastElement();
                    }
                }

                stack.add(token);

            } else if (token == "(") {
                stack.add(token);
            } else if (token == ")") {
                while (!stack.isEmpty() && stack.lastElement() != "(") {
                    output.add(stack.pop());
                }
                if (!stack.isEmpty() && stack.lastElement() == "(") {
                    stack.pop();
                } else {
                    //errore sulle parentesi
                    throw new Exception("Error: parenthesis mismatch");
                }
                if (!stack.isEmpty() && isFunction(stack.lastElement())) {
                    output.add(stack.pop());
                }
            }

        }

        while (!stack.isEmpty()) {
            if (stack.lastElement() == "(") {
                //errore sulle parentesi
                throw new Exception("Error: parenthesis mismatch");
            }
            output.add(stack.pop());
        }

        return output;
    }

    private double evaluateRPN(final List<String> rpn) throws Exception {
        //https://rosettacode.org/wiki/Parsing/RPN_calculator_algorithm#Java_2
        final Stack<Double> stack = new Stack<>();

        final var it = rpn.iterator();
        while (it.hasNext()) {
            final String token = it.next();

            if (isNumber(token)) {
                stack.add(Double.valueOf(token));
            } else if (isOperator(token)) {
                double secondOperand = Double.valueOf(stack.pop());
                double firstOperand = Double.valueOf(stack.pop());
                stack.add(getCalculator().applyBinaryOperation(token, firstOperand, secondOperand));
            } else if (isFunction(token)) {
                double firstOperand = Double.valueOf(stack.pop());
                stack.add(getCalculator().applyUnaryOperation(token, firstOperand));
            }
        }

        if (stack.size() > 1) {
            throw new Exception("Error: too many operands");
        }
        return stack.pop();
    }



    private AbstractCalculator getCalculator() {
        return model.getMounted();
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
    private String type(final String token) {
        return getCalculator().getType(token);
    }

    private int precedence(final String token) {
        return getCalculator().getPrecedence(token);
    }

}
