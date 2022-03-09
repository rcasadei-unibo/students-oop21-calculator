package controller.temp;

/**
 */
public class TempCalculator extends AbstractCalculator {

    /**
     */
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

    /**
     */
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

    /**
     */
    public String getType(String token) {
        String res = "";
        switch (token) {
        case "+": 
            res = "left";
            break;
        case "-": 
            res = "left";
            break;
        case "*": 
            res = "left";
            break;
        case "/": 
            res = "left";
            break;
        case "^": 
            res = "right";
            break;
        default:
        }
        return res;
    }

    /**
     */
    public Double applyUnaryOperation(final String token,final double a) {
        // TODO Auto-generated method stub
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

    /**
     */
    public boolean isUnaryOperator(final String token) {
        // TODO Auto-generated method stub
        return token == "sin" || token == "cos";
    }

    /**
     */
    public boolean isBinaryOperator(final String token) {
        // TODO Auto-generated method stub
        return token == "+" || token == "-"|| token == "/"|| token == "*" || token == "^";
    }
    
}
