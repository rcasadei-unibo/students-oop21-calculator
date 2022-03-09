package model.manager;

import java.util.List;

import controller.temp.AbstractCalculator;
import controller.temp.TempCalculator;


import java.util.ArrayList;

/**
 * 
 */
public class CCManagerModel {

    /**
     * 
     */
    public enum Calculator {
        
        STANDARD(new TempCalculator()), SCIENTIFIC(new TempCalculator()), COMBINATORICS(new TempCalculator()),
        GRAPHIC(new TempCalculator()), ADVANCED(new TempCalculator()), PROGRAMMER(new TempCalculator());

        private final AbstractCalculator calc;
        Calculator(final AbstractCalculator calc) {
            this.calc = calc;
        }
        public AbstractCalculator getCalc() {
            return calc;
        }
    }

    private Calculator mounted = null;
    private final List<String> buffer = new ArrayList<>();

    /**
     * @param s String to be added to the input list
     */
    public void addInput(final String s) {
        this.buffer.add(s);
    }

    /**
     * @return Unmodifiable List containing the strings given in input 
     */
    public List<String> getCurrentState() {
        return List.copyOf(this.buffer);
    }

    /**
     * Sets the input list to have only s.
     * @param s String to set the input list to
     */
    public void setCurrentState(final String s) {
        this.buffer.clear();
        this.buffer.add(s);
    }

    /**
     * Removes all elements from input list.
     */
    public void clearBuffer() {
        this.buffer.clear();
    }
    /**
     * 
     * @return currently mounted calculator
     */
    public AbstractCalculator getMounted() {
        return this.mounted.getCalc();
    }

    /**
     * 
     * @param calcName calculator to be mounted
     */
    public void setMounted(final Calculator calcName) {
        this.mounted = calcName;
    }
}
