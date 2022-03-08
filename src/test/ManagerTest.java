package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import controller.manager.CCManager;

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
        assertEquals(value, Double.valueOf(controller.getCurrentState().get(0)), 0.000001 );
    }
}
