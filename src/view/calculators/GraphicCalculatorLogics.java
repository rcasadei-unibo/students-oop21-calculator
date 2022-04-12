package view.calculators;

import java.util.List;

public interface GraphicCalculatorLogics {
    
    void getEquation(List<String> eq);
    
    double getResult();
    
    List<String> setXValue(double value);
}
