package utils.calculate;

import java.util.Set;

/**
 * @author pesic
 *
 */
public class Operator {
	
	private enum Precedence{
		SUM(1),
		SUB(1),
		MUL(2),
		DIV(2),
		UNARYMINUS(3),
		UNARYPLUS(3),
		POW(4);
		
		private int value;
		Precedence(int v) {
			this.value = v;
		}
		
		private int getPrecedence() {
			return this.value;
		}
	}
	public static Set<String>  availableCharacters = Set.of("+", "-", "*", "/", "^", "÷", "\u00D7");
	
	 private  int numOperands;
	 private  boolean leftAssociative;
	 private  String symbol;
	 private  int precedence;
	 
	    /*
	     */
	    public Operator(String symbol, int numberOfOperands, boolean leftAssociative) {
	    	if(!isAllowedOperator(symbol)) {
	    		throw new IllegalArgumentException();
	    	}
	        this.numOperands = numberOfOperands;
	        this.leftAssociative = leftAssociative;
	        this.symbol = symbol;
	        this.precedence = Operator.getPrecedences(symbol, numberOfOperands);
	    }

	    /**
	    *
	     */
	    public static boolean isAllowedOperator(String s) {
	       return availableCharacters.contains(s);
	    }

	    /**
	     *
	     */
	    public boolean isLeftAssociative() {
	        return leftAssociative;
	    }

	    /**
	     */
	    public int getPrecedence() {
	        return precedence;
	    }

	    /**
	    *
	     */
	    public String getSymbol() {
	        return symbol;
	    }

	    /**
		*
	     */
	    public int getNumOperands() {
	        return numOperands;
	    }
	    
	    public static Integer getPrecedences(final String symbol, final int numArguments) {
	    	switch(symbol) {
	    	case "+":
	    		if(numArguments > 1) {
	    			return Precedence.SUM.getPrecedence();
	    		}
	    		return Precedence.UNARYPLUS.getPrecedence();
	    	
	    	case "-":
	    		if(numArguments > 1) {
	    			return Precedence.SUB.getPrecedence();
	    		}
	    		return Precedence.UNARYMINUS.getPrecedence();
	    	case "\u00D7":
	    	case "*":
	    		return Precedence.MUL.getPrecedence();
	    	case "÷":
	    	case "/":
	    		return Precedence.DIV.getPrecedence();
	    	case "^":
	    		return Precedence.POW.getPrecedence();
	    	default:
	    		return null;
	    	}
	    }
	    
	    public static Operator getOperatorBySymbolAndArgs(final String symbol, final int numArguments) {
	    	switch(symbol) {
	    	case "+":
	    		if(numArguments > 1) {
	    			return new Operator(symbol, numArguments, true);
	    		}
	    		return new Operator(symbol, numArguments, false);
	    	
	    	case "-":
	    		if(numArguments > 1) {
	    			return new Operator(symbol, numArguments, true);
	    		}
	    		return new Operator(symbol, numArguments, false);
	    	case "*":
	    	case "\u00D7":
	    		return new Operator(symbol, numArguments, true);
	    	case "÷":
	    	case "/":
	    		return new Operator(symbol, numArguments, true);
	    	case "^":
	    		return new Operator(symbol, numArguments, false);
	    	default:
	    		return null;
	    	}
	    }
	    
}
