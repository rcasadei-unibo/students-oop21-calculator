package utils.calculate;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import model.calculators.ScientificCalculatorModelFactory;

/**
 * It is used to get all the allowed functions.
 *
 */
public class Function {	
	private String name;
	private int numArgs;
	
	
	/**
	 * @return All functions
	 */
	public static Set<String> getFunctions() {
	   var set = new HashSet<>(ScientificCalculatorModelFactory.create()
	           .getUnaryOpMap().keySet());
	           set.addAll(Set.of("abs", "acos", "asin", "atan", "cos",
	                   "exp", "log", "negate", "pow", "sin", "√", "tan", "csc", "cot", "sec"));
	   return set;
	}
	
	/**
	 * 
	 */
	public static final Map<String, Function> DICTFUNCTIONS = getFunctions().stream()
			.collect(Collectors.toMap(s -> s, s -> "pow".equals(s) ? new Function(s, 2) : new Function(s)));
	
	/**
	 * @param name
	 * @param numArgs
	 */
	public Function(final String name, final int numArgs) {
		if (name.length() == 0 || numArgs < 0 || !getFunctions().contains(name)) {
			throw new IllegalArgumentException();
		}

		this.name = name;
		this.numArgs = numArgs;
	}
	
	/**
	 * @param name
	 */
	public Function(final String name) {
		this(name, 1);
	}
	
	/**
	 * @return the name of he function
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return number of arguments required
	 */
	public int getNumArgs() {
		return this.numArgs;
	}
	
	/**
	 * @param name
	 * @return if he function exists
	 */
	public static boolean isFunction(final String name) {
		return getFunctions().contains(name);
	}
}
