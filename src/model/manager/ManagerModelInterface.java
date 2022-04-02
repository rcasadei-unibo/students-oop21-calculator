package model.manager;

import java.util.List;

import javax.swing.JPanel;

import controller.calculators.CalculatorController;
import controller.calculators.ControllerFactoryImpl;
import model.calculators.CalculatorModel;
import model.calculators.CombinatoricsCalculatorModelFactory;

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
        STANDARD(CombinatoricsCalculatorModelFactory.create()), 
        /**
         * Scientific calculator. Contains a reference to the scientific calculator controller.
         */
        SCIENTIFIC(CombinatoricsCalculatorModelFactory.create()), 
        /**
         * Programmer calculator. Contains a reference to the programmer calculator controller.
         */
        PROGRAMMER(CombinatoricsCalculatorModelFactory.create()),
        /**
         * Graphic calculator. Contains a reference to the graphic calculator controller.
         */
        GRAPHIC(CombinatoricsCalculatorModelFactory.create()),
        /**
         * Combinatorics calculator. Contains a reference to the combinatorics calculator controller.
         */
        COMBINATORICS(CombinatoricsCalculatorModelFactory.create()),
        /**
         * Advanced calculator. Contains a reference to the scientific calculator controller.
         */
        ADVANCED(CombinatoricsCalculatorModelFactory.create());

        private final CalculatorController controller;
        Calculator(final CalculatorModel model) {
            this.controller = new ControllerFactoryImpl().createController(model, this);
        }

        /**
         * Returns the AbstractCalculator controller of the calculator.
         * @return controller of the calculator
         */
        public CalculatorController getController() {
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
