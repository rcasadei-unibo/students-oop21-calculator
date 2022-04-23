package model.manager;

import java.util.Optional;

import controller.calculators.CalculatorController;
import controller.calculators.ControllerFactoryImpl;
import model.calculators.CalculatorModel;
import model.calculators.CombinatoricsCalculatorModelFactory;
import model.calculators.ProgrammerCalculatorModelFactory;
import model.calculators.ScientificCalculatorModelFactory;
import model.calculators.StandardCalculatorModelFactory;

/**
 * Interface for the model of the engine manager.
 */
public interface EngineModelInterface {

    /**
     * Enumeration of all types of calculators available in the system.
     */
    enum Calculator {

        /**
         * Standard calculator. Contains a reference to the standard calculator controller.
         */
        STANDARD(StandardCalculatorModelFactory.create()), 
        /**
         * Scientific calculator. Contains a reference to the scientific calculator controller.
         */
        SCIENTIFIC(ScientificCalculatorModelFactory.create()), 
        /**
         * Programmer calculator. Contains a reference to the programmer calculator controller.
         */
        PROGRAMMER(ProgrammerCalculatorModelFactory.create()),
        /**
         * Graphic calculator. Contains a reference to the graphic calculator controller.
         */
        GRAPHIC(ScientificCalculatorModelFactory.create()),
        /**
         * Combinatorics calculator. Contains a reference to the combinatorics calculator controller.
         */
        COMBINATORICS(CombinatoricsCalculatorModelFactory.create()),
        /**
         * Advanced calculator. Contains a reference to the advanced calculator controller.
         */
        ADVANCED(ScientificCalculatorModelFactory.create());

        private final CalculatorController controller;
        Calculator(final CalculatorModel model) {
            this.controller = new ControllerFactoryImpl().createController(model);
        }

        /**
         * Returns the controller of the calculator.
         * @return controller of the calculator
         */
        public CalculatorController getController() {
            return this.controller;
        }

    }

    /**
     * Sets the calculator to be used in the calculations. 
     * @param calculator Calculator to be mounted
     */
    void setMounted(Calculator calculator);

    /**
     * Returns the calculator currently in use.
     * @return Optional of the currently mounted calculator
     */
    Optional<Calculator> getMounted();

}
