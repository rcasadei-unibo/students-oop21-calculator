package view.calculators;

import java.util.List;
/**
 * 
 * 
 *
 */
public interface GraphicCalculatorLogics {
    /**
     * 
     * @param eq
     */
    void setEquation(List<String> eq);
    /**
     * 
     * @return aaaa
     */
    Double getResult();
    /**
     * 
     * @param value
     * @return aaa
     */
    List<String> replaceXValue(double value);
}
