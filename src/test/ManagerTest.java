package test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import controller.calculators.ControllerFactoryImpl;
import controller.manager.CCEngine;
import controller.manager.CCManager;
import model.calculators.StandardCalculatorModelFactory;
import model.manager.EngineModelInterface.Calculator;
import utils.CalcException;
import view.main.CCMainGUI;


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

        controller.engine().mount(Calculator.STANDARD);
        final var mem = controller.memory();

        mem.readAll(List.of("2", "sum", "2"));
        assertEquals(List.of("2", "sum", "2"), controller.memory().getCurrentState());
        controller.engine().calculate();
        assertEquals(List.of("4"), controller.memory().getCurrentState());
        mem.read("2");
        assertEquals(List.of("4", "2"), controller.memory().getCurrentState());
        mem.readAll(List.of("mult", "-1"));
        controller.engine().calculate();
        assertEquals(List.of("-42"), controller.memory().getCurrentState());


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
            assertEquals(List.of("3", "x", "mult", "2", "sum"), rpn);
        } catch (CalcException e) {
            e.printStackTrace();
        }
    }
    /**
     * 
     */
    @org.junit.Test
    public void testFormatter() {
        controller.engine().mount(Calculator.STANDARD);
        controller.memory().clear();
//        System.out.println(10000000000.0);
        controller.memory().read("1000000000");
        logState();
        controller.engine().calculate();
        logState();
    }

    private void logState() {
        System.out.println(controller.memory().getCurrentState().stream().reduce("", (a, b) -> a + b));

    }
}
