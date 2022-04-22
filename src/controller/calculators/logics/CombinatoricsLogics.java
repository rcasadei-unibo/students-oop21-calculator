package controller.calculators.logics;

/**
 * 
 *
 * Interface of the Controller which CombinatoricsCalculatorPanel interacts to handle input.
 *
 */
public interface CombinatoricsLogics {

    /**
     * 
     * Enumeration used in CombinatoricsCalculatorPanel to get all the information about the Combinatorics Operations.
     * From each value(operation) you can get two strings: the name of the button of the operation and the name used in CombinatoricsCalculatorModelFactory
     *
     */
    enum Operations {
        SEQUENCES("Sequences", "sequencesNumber"),
        DISPOSITIONS("Subsets", "binomialCoefficient"),
        SUBSETS("Dispositions", "factorial"),
        DERANGEMENTS("Derangements", "derangement"),
        PARTITIONS("Partitions", "bellNumber"),
        PARTITIONSBIN("Partitions(binary)", "stirlingNumber"),
        FIBONACCI("Fibonacci", "fibonacci"),
        FIBONACCIBIN("Fibonacci(binary)", "binaryFibonacci");

        private final String opBtnName;
        private final String opModelName;

        Operations(final String opBtnName, final String opModelName) {
            this.opBtnName = opBtnName;
            this.opModelName = opModelName;
        }

        public String getOpBtnName() {
            return opBtnName;
        }

        public String getOpModelName() {
            return opModelName;
        }
    }

    /**
     * 
     * @param btnText the value of the button that has been clicked on
     * @return the String to be displayed after clicking on a number
     */
    String numberAction(String btnText);

    /**
     * 
     * @param btnName the name of the button of the operation
     * @param opName the name that the same operation uses in Model
     * @return the String to be displayed after clicking on an operation
     */
    String operationAction(String btnName, String opName);

    /**
     * 
     * @return the String to be displayed after clicking on the backspace button
     */
    String backspaceAction();

    /**
     * 
     * @return the current manager buffer made into a String
     */
    String getBufferToString();

    /**
     * 
     * @return the String to be displayed(in the upper text)after clicking on the '=' button
     */
    String calculateAction();
}
