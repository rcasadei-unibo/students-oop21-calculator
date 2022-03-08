package controller.manager;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import controller.temp.TempCalculator;
import model.manager.CCManagerModel;
import view.main.CCMainGUI;

/**
 * 
 *
 */
public class CCManager {

    CCManagerModel model = new CCManagerModel();
    CCMainGUI view = new CCMainGUI();
    TempCalculator calculator = new TempCalculator();
    
    public void read(String s) {
        model.addInput(s);
//        this.printCurrentState();
    }
    
    public void printCurrentState() {
        view.log(model.getCurrentState());
    }
    
    public List<String> getCurrentState() {
        return model.getCurrentState();
    }
    
    public void clear() {
        model.clearBuffer();
    }

    public void calculate()  {
        List<String> input = model.getCurrentState();
        List<String> RPNInput;
        try {
            RPNInput = this.parseRPN(this.unifyTerms(input));
//            view.log("RPN:");
//            view.log(RPNInput);

            double result = this.evaluateRPN(RPNInput);
//            view.log("Result: ");
            model.setCurrentState(String.valueOf(result));
        } catch(EmptyStackException e) {
            model.setCurrentState("Syntax error");
        }
        catch (Exception e) {
            e.printStackTrace();
            model.setCurrentState(e.getMessage());
        }
        
    }
    
    private List<String> unifyTerms(List<String> input) {
        List<String> unified = new ArrayList<>();
        
        List<String> currentNumber = new ArrayList<>();
        input.forEach((s) -> {
            if(isNumber(s) || s == ".") {
                currentNumber.add(s);
//                view.log(currentNumber);
            } else{
                 if(currentNumber.size() > 0) {
                    double actualNum = convert(currentNumber);
                    currentNumber.clear();
                    unified.add(String.valueOf(actualNum));
                 }
                unified.add(s);
            }
        });
        if(currentNumber.size() > 0) {
            double actualNum = convert(currentNumber);
            currentNumber.clear();
            unified.add(String.valueOf(actualNum));
        }
        return unified;
    }

    private double convert(List<String> currentNumber) {
        String num = currentNumber.stream().reduce("", (a,b) -> a + b);
        return Double.valueOf(num);
    }

    private List<String> parseRPN(List<String> infix) throws Exception {
        
        List<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        
        
        var it = infix.iterator();
        while(it.hasNext()) {
            
            String token = it.next();
            if( isNumber(token) ) {
                output.add(token);
            }else if ( isFunction(token) ){
                stack.add(token);
            }else if ( isOperator(token) ) {
                
                if(!stack.isEmpty()) {
                    String o2 = stack.lastElement();
                    while(o2 != "(" && ( precedence(o2) > precedence(token) || (precedence(o2) == precedence(token) && type(token) == "left") ) ) {
                        output.add(stack.pop());
                        if(stack.isEmpty()) break;
                         o2 = stack.lastElement();
                    }
                }
            
                stack.add(token);
                
            } else if (token == "(") {
                stack.add(token);
            }else if (token == ")") {
                while(!stack.isEmpty() && stack.lastElement() != "(") {
                    output.add(stack.pop());
                }
                if(!stack.isEmpty() && stack.lastElement() == "(") {
                    stack.pop();
                } else {
                    //errore sulle parentesi
                    throw new Exception("Error: parenthesis mismatch");
                }
                if(!stack.isEmpty() && isFunction( stack.lastElement())) {
                    output.add(stack.pop());
                }
            }
            
        }
        
        while(!stack.isEmpty()) {
            if(stack.lastElement() == "(") {
                //errore sulle parentesi
                throw new Exception("Error: parenthesis mismatch");
            }
            output.add(stack.pop());
        }
        
        return output;
    }
    
    private double evaluateRPN(List<String> rpn) throws Exception {
        //https://rosettacode.org/wiki/Parsing/RPN_calculator_algorithm#Java_2
        Stack<Double> stack = new Stack<>();

        var it = rpn.iterator();
        while(it.hasNext()) {
            String token = it.next();
            
            if(isNumber(token)) {
                stack.add(Double.valueOf(token));
            }else if( isOperator(token) ) {
                double secondOperand = Double.valueOf(stack.pop());
                double firstOperand = Double.valueOf(stack.pop());
                stack.add(calculator.applyBinaryOperation(token, firstOperand, secondOperand));
            }
            else if( isFunction(token) ) {
                double firstOperand = Double.valueOf(stack.pop());
                stack.add(calculator.applyUnaryOperation(token, firstOperand));
            }
        }
        
        if(stack.size() > 1) {
            throw new Exception("Error: too many operands");
        }
        return stack.pop();
    }

    

    private boolean isFunction(String token) {
        return calculator.isUnaryOperator(token);
    }

    private boolean isOperator(String token) {
        return calculator.isBinaryOperator(token);
    }

    private boolean isNumber(String token) {
        try {
            Double.valueOf(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private String type(String token) {
        return calculator.getType(token);
    }

    private int precedence(String o2) {
        return calculator.getPrecedence(o2);
    }

}
