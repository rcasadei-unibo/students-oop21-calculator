package controller.manager;

import java.util.List;

import utils.CalcException;

/**
 * 
 */
public interface EngineInterface {

    /**
     * 
     * @param input
     * @return result
     */
    double calculate(List<String> input) throws CalcException;

}
