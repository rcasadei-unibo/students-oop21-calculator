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
    void setEquation(String eq);
    /**
     * 
     * @param value
     * @return aaaa
     */
    List<Double> getResult(double value);
}
