package utils.ast;

/**
 * @author pesic
 *
 */
public interface Operation {
	Double getNumericResult(Double val);
	
	Operation getDerivative();
}
