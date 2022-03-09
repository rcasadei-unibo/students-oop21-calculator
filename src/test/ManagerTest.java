package test;

import static org.junit.Assert.assertEquals;

import controller.manager.CCManager;
import model.manager.CCManagerModel.Calculator;


/**
 * 
 */
public class ManagerTest {
    private final CCManager controller = new CCManager();

    /**
     * test javadoc.
     */
    @org.junit.Test
    public void initialize() {

    }
    /**
     * test javadoc.
     */
    @org.junit.Test
    public void testController() {

        //cos ( 3 + 4 * 2 ) / ( 1 - 5 ) ^ 2 ^ 3 

        controller.mount(Calculator.STANDARD);

        controller.read("cos");
        controller.read("(");
        controller.read("3");
        controller.read("+");
        controller.read("4");
        controller.read("*");
        controller.read("2");
        controller.read(")");
        controller.read("/");
        controller.read("(");
        controller.read("1");
        controller.read("-");
        controller.read("5");
        controller.read(")");
        controller.read("^");
        controller.read("2");
        controller.read("^");
        controller.read("3");

        controller.printCurrentState();
        controller.calculate();
        controller.printCurrentState();
        double value = 6.753079205E-8;
        assertEquals(value, Double.valueOf(controller.getCurrentState().get(0)), 1E-16 );
    }
}
