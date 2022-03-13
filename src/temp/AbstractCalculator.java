package temp;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import brodmo_test.OurBinaryOperator;


/**
 * 
 */
public abstract class AbstractCalculator {
	public enum Type{
		LEFT,RIGHT;
	}
	public abstract Map<String,CCBinaryOperator> getCalculator();
    /**
     * 
     * @param op string representing the operation
     * @param a first operand
     * @param b second operand
     * @return result of the given operation
     */
    protected double applyBinaryOperation(String op, Double a, Double b) {
    	return this.getCalculator().get(op).apply(a,b);
    }
    /**
     * @param op string representing the operation
     * @return precedence
     */
   // public  int getPrecedence(String op);

    /**
     * @param op string representing the operation
     * @return "left" or "right" (dovrebbe essere una enum)
     */
   // public  String getType(String op);

    /**
     * 
     * @param op string representing the operation
     * @param a first operand
     * @return result of the given operation
     */
   // public  Double applyUnaryOperation(String op, double a);

    /**
     * 
     * @param op string representing the operation
     * @return whether the given operation is unary
     */
    //public  boolean isUnaryOperator(String op);

    /**
     * 
     * @param op string representing the operation
     * @return whether the given operation is binary
     */
    //public  boolean isBinaryOperator(String op);

    /**
     * 
     * @param mng manager of the system
     */
    //public abstract void setManager(CCManager mng);

    /**
     * 
     * @return view component of the calculator
     */
    public abstract JPanel getGUI();
}
