package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import controller.manager.CCManager;
import model.manager.CCManagerModel.Calculator;
import utils.NumberFormatter;


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

//        controller.printCurrentState();
        controller.calculate();
//        controller.printCurrentState();
        assertEquals("6.7530792054E-8", controller.getCurrentState().get(0));
    }

    /**
     * 
     */
    @org.junit.Test
    public void testNegativeNumbers() {
        controller.mount(Calculator.STANDARD);
        controller.readAll(List.of("9", "-", "(", "-", "3", ")"));
//        controller.printCurrentState();
        controller.calculate();
//        controller.printCurrentState();
        assertEquals(12.0, Double.valueOf(controller.getCurrentState().get(0)), 0);

        controller.clear();
        controller.readAll(List.of("-", "3"));
        controller.calculate();
        assertEquals(-3.0, Double.valueOf(controller.getCurrentState().get(0)), 0);

        controller.clear();
        controller.readAll(List.of("(", "5", ")", "-", "(", "3", ")"));
        controller.calculate();
        assertEquals(2.0, Double.valueOf(controller.getCurrentState().get(0)), 0);

        controller.clear();
        controller.readAll(List.of("(", "5", ")", "-", "3"));
        controller.calculate();
        assertEquals(2.0, Double.valueOf(controller.getCurrentState().get(0)), 0);
    }
}
