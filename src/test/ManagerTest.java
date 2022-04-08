package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import controller.calculators.ControllerFactoryImpl;
import controller.manager.CCEngine;
import controller.manager.CCManager;
import model.calculators.StandardCalculatorModelFactory;
import model.manager.ManagerModelInterface.Calculator;
import utils.CalcException;
import view.main.CCMainGUI;


/**
 * 
 */
public class ManagerTest {
    private final CCManager controller = new CCManager(new CCMainGUI());

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

        controller.calculate();
        assertEquals("6.7530792054E-8", controller.getCurrentState().stream().reduce("", (a, b) -> a + b));

    }

    /**
     * 
     */
    @org.junit.Test
    public void testNegativeNumbers() {
        controller.mount(Calculator.STANDARD);
        controller.readAll(List.of("9", "-", "(", "-", "3", ")"));
        controller.calculate();
        assertEquals("12", controller.getCurrentState().stream().reduce("", (a, b) -> a + b));

        controller.clear();
        controller.readAll(List.of("-", "3"));
        controller.calculate();
        assertEquals("-3", controller.getCurrentState().stream().reduce("", (a, b) -> a + b));

        controller.clear();
        controller.readAll(List.of("(", "5", ")", "-", "(", "3", ")"));
        controller.calculate();
        assertEquals("2", controller.getCurrentState().stream().reduce("", (a, b) -> a + b));

        controller.clear();
        controller.readAll(List.of("(", "5", ")", "-", "3"));
        controller.calculate();
        assertEquals("2", controller.getCurrentState().stream().reduce("", (a, b) -> a + b));
    }
    /**
     * 
     */
    @org.junit.Test
    public void testVariables() {
        final var engine = new CCEngine(new ControllerFactoryImpl().createController(StandardCalculatorModelFactory.create()));
        final List<String> in = List.of("3", "mult", "x", "sum", "2");
        try {
            final List<String> rpn = engine.parseToRPN(in);
            System.out.println(rpn);
        } catch (CalcException e) {
            e.printStackTrace();
        }
    }
}
