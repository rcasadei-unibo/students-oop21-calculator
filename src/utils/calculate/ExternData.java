package utils.calculate;

import java.util.Collections;
import java.util.Set;

/**
 *
 *
 */
public class ExternData {
	
	private Set<String> constants = Set.of("pi", "e");
	private String variable = "x";
	
	public Set<String> getConstants() {
		return Collections.unmodifiableSet(this.constants);
	}
	
	public String getVariable() {
		return this.variable;
	}
	
}
