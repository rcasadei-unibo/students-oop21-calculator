package utils.calculate;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author pesic
 *
 */
public class Function {
	
	private String name;
	private int numArgs;
	
	public static Set<String> Functions = Set.of("abs", "acos", "asin", "atan", "cos",
			"exp", "log", "negate", "pow", "sin", "sqrt", "tan", "csc", "cot", "sec");
	
	public static Map<String, Function> dictFunctions = Functions.stream()
			.collect(Collectors.toMap(s->s, s->s.equals("pow")?new Function(s, 2):new Function(s)));
	
	public Function(String name, int numArgs) {
		if(name.length() == 0 || numArgs < 0 || !Functions.contains(name)) {
			throw new IllegalArgumentException();
		}
		
		this.name = name;
		this.numArgs = numArgs;
	}
	
	public Function(String name) {
		this(name, 1);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getNumArgs() {
		return this.numArgs;
	}
	
	public static boolean isFunction(String name) {
		return Functions.contains(name);
	}
}
