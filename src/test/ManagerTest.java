package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import controller.manager.CCEngine;
import controller.manager.CCManager;
import model.manager.EngineModelInterface.Calculator;
import utils.CalcException;


/**
 * TODO: javadoc.
 */
public class ManagerTest {
    private final CCManager controller = new CCManager();

    /**
     * Testing Manager reading and calculating. 
     */
    @org.junit.Test
    public void testController() {

        controller.engine().mount(Calculator.STANDARD);
        final var mem = controller.memory();

        mem.readAll(List.of("2", "+", "2"));
        assertEquals(List.of("2", "+", "2"), controller.memory().getCurrentState());
        controller.engine().calculate();
        assertEquals(List.of("4"), controller.memory().getCurrentState());
        mem.read("2");
        assertEquals(List.of("4", "2"), controller.memory().getCurrentState());
        mem.readAll(List.of("×", "(", "-", "1", ")"));
        controller.engine().calculate();
        assertEquals(List.of("-42"), controller.memory().getCurrentState());

    }

    /**
     * Testing the correct behavior of the engine with symbolic expressions.
     */
    @org.junit.Test
    public void testVariables() {
        controller.engine().mount(Calculator.STANDARD);

        final var engine = new CCEngine(Calculator.STANDARD.getController());
        final List<String> in = List.of("3", "×", "x", "+", "2");
        try {
            final List<String> rpn = engine.parseToRPN(in);
            assertEquals(List.of("3", "x", "×", "2", "+"), rpn);
        } catch (CalcException e) {
            fail("Parsing error");
        }
    }
}
