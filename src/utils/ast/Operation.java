package utils.ast;

/**
 * Operation inteface used for calculating derivateves and general results.
 *
 */
// https://github.com/Ivan-Capponi/Tesi_di_Laurea/tree/master/Derivate/src/ast
public interface Operation {
    /**
     * @param val
     * @return the numerical result of the expression given the value
     */
    Double getNumericResult(Double val);

    /**
     * @return he derivative
     */
    Operation getDerivative();
}
