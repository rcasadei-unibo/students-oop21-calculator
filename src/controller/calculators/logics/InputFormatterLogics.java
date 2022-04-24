package controller.calculators.logics; 
/**
 * This inferface acts as and intermediate between the panel and the engine.
 * It shares the engine' methods.
 */
public interface InputFormatterLogics {
    /**
     * This method will call the memory's read().
     * @param input the value to be read.
     */
    void read(String input);

    /**
     * This method will call the memory's deleteLast().
     */
    void deleteLast();

    /**
     * This method will call the engine's calculate().
     */
    void calculate();
}
