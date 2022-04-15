package utils.calculate;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * It is used to get all the allowed functions.
 *
 */
public class Function {	
	private String name;
	private int numArgs;
	
	/**
	 * 
	 */
	public static final Set<String> FUNCTIONS = Set.of("abs", "acos", "asin", "atan", "cos",
			"exp", "log", "negate", "pow", "sin", "√", "tan", "csc", "cot", "sec");
	
	/**
	 * 
	 */
	public static final Map<String, Function> DICTFUNCTIONS = FUNCTIONS.stream()
			.collect(Collectors.toMap(s -> s, s -> "pow".equals(s) ? new Function(s, 2) : new Function(s)));
	
	/**
	 * @param name
	 * @param numArgs
	 */
	public Function(final String name, final int numArgs) {
		if (name.length() == 0 || numArgs < 0 || !FUNCTIONS.contains(name)) {
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
		return FUNCTIONS.contains(name);
	}
}
