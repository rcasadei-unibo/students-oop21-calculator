package utils.calculate;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 *Used for variables and constants.
 *
 */
public class ExternData {
	
	private final Map<String, Double> constants = Map.of("pi", Math.PI, "e", Math.E);
	private static final String VARIABLE = "x";
	
	/**
	 * @return constants symbol
	 */
	public Map<String, Double> getConstants() {
		return Collections.unmodifiableMap(this.constants);
	}
	
	/**
	 * @return variable symbol
	 */
	public String getVariable() {
		return VARIABLE;
	}
	
}
