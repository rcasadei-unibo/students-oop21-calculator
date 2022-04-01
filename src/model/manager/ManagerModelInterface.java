package model.manager;

import java.util.List;

import javax.swing.JPanel;

import controller.calculators.CalculatorControllerTemplate;
import controller.calculators.CalculatorControllerBuilder;
import controller.calculators.CombinatoricsCalculatorControllerBuilder;

/**
 * 
 *
 */
public interface ManagerModelInterface {

    /**
     * Enumeration of all types of calculators available in the system.
     */
    enum Calculator {

        /**
         * Standard calculator. Contains a reference to the standard calculator controller.
         */
        STANDARD(new CombinatoricsCalculatorControllerBuilder()), 
        /**
         * Scientific calculator. Contains a reference to the scientific calculator controller.
         */
        SCIENTIFIC(new CombinatoricsCalculatorControllerBuilder()), 
        /**
         * Programmer calculator. Contains a reference to the programmer calculator controller.
         */
        PROGRAMMER(new CombinatoricsCalculatorControllerBuilder()),
        /**
         * Graphic calculator. Contains a reference to the graphic calculator controller.
         */
        GRAPHIC(new CombinatoricsCalculatorControllerBuilder()),
        /**
         * Combinatorics calculator. Contains a reference to the combinatorics calculator controller.
         */
        COMBINATORICS(new CombinatoricsCalculatorControllerBuilder()),
        /**
         * Advanced calculator. Contains a reference to the scientific calculator controller.
         */
        ADVANCED(new CombinatoricsCalculatorControllerBuilder());

        private final CalculatorControllerTemplate controller;
        Calculator(final CalculatorControllerBuilder controller) {
            this.controller = controller.getController();
        }

        /**
         * Returns the AbstractCalculator controller of the calculator.
         * @return controller of the calculator
         */
        public CalculatorControllerTemplate getController() {
            return this.controller;
        }

        /**
         * Return the view component of the calculator.
         * @return GUI component of the calculator
         */
        public JPanel getGUI() {
            return this.controller.getGUI();
        }
    }

    /**
     * Appends a string to the input list used for the calculation.
     * @param s String to be added
     */
    void addInput(String s);

    /**
     * Returns the current state of the input list.
     * @return Unmodifiable list containing the strings given in input 
     */
    List<String> getCurrentState();

    /**
     * Sets the input list to have only s.
     * @param s String to set the input list to
     */
    void setCurrentState(String s);

    /**
     * Removes all elements from input list.
     */
    void clearBuffer(); 

    /**
     * Sets the calculator to be used. 
     * @param calculator calculator to be mounted
     */
    void setMounted(Calculator calculator);

    /**
     * Returns the calculator currently in use.
     * @return currently mounted calculator
     */
    Calculator getMounted();
}
