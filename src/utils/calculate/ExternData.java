package utils.calculate;

import java.util.Collections;
import java.util.Map;

/**
 *Used for variables and constants.
 *
 */
public class ExternData {
	
	private final Map<String, Double> constants = Map.of(
	        "pi", Math.PI,
	        "e", Math.E,
	        "infinity", 1E+12,
	        "Infinity", 1E+12,
	        "inf", 1E+12,
	        "NaN", Double.NaN);
	
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
