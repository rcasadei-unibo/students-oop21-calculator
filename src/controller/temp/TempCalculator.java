package controller.temp;

import javax.swing.JPanel;

import controller.manager.CCManager;
import utils.AbstractCalculator;
import utils.Type;
import view.components.CCDisplay;
import view.temp.TempCalcGUI;

/**
 */
public class TempCalculator extends AbstractCalculator {
    private CCDisplay display;
    

    @Override
    public double applyBinaryOperation(final String op, final Double a, final Double b) {
        double res = 0.0;
        switch (op) {
        case "+": 
            res = a + b;
            break;
        case "-": 
            res = a - b;
            break;
        case "*": 
            res = a * b;
            break;
        case "/": 
            res = a / b;
            break;
        case "^": 
            res = Math.pow(a, b);
            break;
        default: 
        }
        return res;
    }

    @Override
    public int getPrecedence(final String o2) {
        int res = 0;
        switch (o2) {
        case "+": 
            res = 1;
            break;
        case "-": 
            res = 1;
            break;
        case "*": 
            res = 2;
            break;
        case "/": 
            res = 2;
            break;
        case "^": 
            res = 3;
            break;
        default:
        }
        return res;
    }

    @Override
    public Type getType(final String token) {
        Type res = null;
        switch (token) {
        case "+": 
            res = Type.LEFT;
            break;
        case "-": 
            res = Type.LEFT;
            break;
        case "*": 
            res = Type.LEFT;
            break;
        case "/": 
            res = Type.LEFT;
            break;
        case "^": 
            res = Type.RIGHT;
            break;
        default:
        }
        return res;
    }

    @Override
    public Double applyUnaryOperation(final String token, final double a) {
        double res = 0.0;
        switch (token) {
        case "sin": 
            res = Math.sin(a);
            break;
        case "cos": 
            res = Math.cos(a);
            break;
        default:
        }
        return res;
    }

    @Override
    public boolean isUnaryOperator(final String token) {
        return token == "sin" || token == "cos";
    }

    @Override
    public boolean isBinaryOperator(final String token) {
        return token == "+" || token == "-" || token == "/" || token == "*" || token == "^";
    }

    public void read(final String text) {
        this.manager.read(text);
        this.display.updateText(this.manager.getCurrentState().stream().reduce("", (a, b) -> a + b));
    }

    public void calculate() {
        // TODO Auto-generated method stub
        this.manager.calculate();
        this.display.updateText(this.manager.getCurrentState().stream().reduce("", (a, b) -> a + b));
    }
    
    public void show() {
        this.display.updateText(this.manager.getCurrentState().stream().reduce("", (a, b) -> a + b));
    }

    public void setDisplay(final CCDisplay display) {
        this.display = display;
    }

    public JPanel getGUI() {
        return this.panel;
    }

    @Override
    public void setCalculator() {
        // TODO Auto-generated method stub
        
    }

    public CCManager getManager() {
        // TODO Auto-generated method stub
        return this.manager;
    }
    
    @Override
    public void setManager(final CCManager mng) {
        super.setManager(mng);
        this.panel = new TempCalcGUI(this);
    }
}
