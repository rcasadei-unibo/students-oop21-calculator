package model.manager;

import java.util.List;

import javax.swing.JPanel;

import utils.AbstractCalculator;
import controller.calculators.CombinatoricsCalculatorController;
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

        STANDARD(new TempCalculator()), 
        SCIENTIFIC(new TempCalculator()), 
        PROGRAMMER(new TempCalculator()), GRAPHIC(new TempCalculator()),
        COMBINATORICS(new CombinatoricsCalculatorController()), ADVANCED(new TempCalculator());

        private final AbstractCalculator controller;
        Calculator(final AbstractCalculator controller) {
            this.controller = controller;
        }

        /**
         * 
         * @return controller of the calculator
         */
        public AbstractCalculator getController() {
            return this.controller;
        }

        /**
         * 
         * @return GUI component of the calculator
         */
        public JPanel getGUI() {
            return this.controller.getGUI();
        }
    }

    private Calculator mounted;
    private final List<String> buffer = new ArrayList<>();

    /**
     * Appends a string to the input list used for the calculation.
     * @param s String to be added
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
    public Calculator getMounted() {
        return this.mounted;
    }

    /**
     * 
     * @param calculator calculator to be mounted
     */
    public void setMounted(final Calculator calculator) {
        this.mounted = calculator;
//        System.out.println("montata " + this.mounted.name()); 
    }
}
