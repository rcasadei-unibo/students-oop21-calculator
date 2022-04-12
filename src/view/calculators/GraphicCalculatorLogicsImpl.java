package view.calculators;

import java.util.ArrayList;
import java.util.List;

import controller.calculators.CalculatorController;

public class GraphicCalculatorLogicsImpl implements GraphicCalculatorLogics {
    private List<String> eq = new ArrayList<>();
    private final CalculatorController controller;
    
    public GraphicCalculatorLogicsImpl(final CalculatorController controller) {
        this.controller = controller;
    }
    
    public void getEquation(final List<String> eq) {
        this.eq = eq;
    }
    
    public double getResult() {
        this.controller.getManager().engine().calculate();
        return Double.valueOf(this.controller.getManager().memory().getCurrentState().get(0));
    }
    
    public List<String> setXValue(final double value){
        List<String> temp = this.eq;
        temp.stream().filter(e -> e == "x").map(e -> e = Double.toString(value));
        return temp;
    }
}
