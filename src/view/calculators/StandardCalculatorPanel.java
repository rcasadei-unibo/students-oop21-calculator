package view.calculators;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import view.components.CCDisplay;
import view.components.CCNumPad;
import controller.calculators.CalculatorController;
import model.calculators.StandardCalculatorModelFactory;
import model.manager.EngineModelInterface.Calculator;
import view.logics.CreateButton;
/**
 * This is StandardCalculatorPanel which holds the basic operators:
 * -plus.
 * -subtraction.
 * -multiplication.
 * -division.
 * -square.
 * -inverse.
 * -modulo.
 */
public class StandardCalculatorPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -3801351406960094788L;
    private final CCDisplay display = new CCDisplay();
    private final CalculatorController controller;
    /**
      * This is StandardCalculatorPanel which holds the basic operators:
      * -plus.
      * -subtraction.
      * -multiplication.
      * -division.
      * -square.
      * -inverse.
      * -modulo.
     */
    public StandardCalculatorPanel() {
        this.controller = Calculator.STANDARD.getController();
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);
        this.setNumbers();
        this.setOperators();
    }
    private void setNumbers() {
        final ActionListener btnAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.getManager().memory().read(((JButton) e.getSource()).getText());
                CreateButton.updateDisplay(controller, display);
            }
        };
        final ActionListener calcAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (!controller.getManager().memory().getCurrentState().isEmpty()) {
                    final String history = controller.getManager().memory().getCurrentState().stream().reduce("", (a, b) -> a + b).replace("Syntax error", "");
                    controller.getManager().engine().calculate();
                    display.updateUpperText(history.concat(" ="));
                    if (!controller.getManager().memory().getCurrentState().contains("Syntax error")) {
                        controller.getManager().memory().addResult(history.concat(" = ").concat(controller.getManager().memory().getCurrentState().stream().reduce("", (a, b) -> a + b)));
                    }
                }
                CreateButton.updateDisplay(controller, display);
            }
        };
        final ActionListener backspaceAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.getManager().memory().deleteLast();
                CreateButton.updateDisplay(controller, display);
            }
        };
        final JPanel numbers = new CCNumPad(btnAl, calcAl, backspaceAl);
        this.add(numbers, BorderLayout.CENTER);
    }
    private void setOperators() {
        final JPanel operator = new JPanel();
        operator.setLayout(new GridLayout(4, 2));
        for (final var entry : StandardCalculatorModelFactory.create().getBinaryOpMap().entrySet()) {
            operator.add(CreateButton.createOpButton(entry.getKey(), entry.getKey(), entry.getKey(), controller, display));
        }
        for (final var entry : StandardCalculatorModelFactory.create().getUnaryOpMap().entrySet()) {
            if ("1/x".equals(entry.getKey())) {
                operator.add(CreateButton.createOpButton(entry.getKey(), entry.getKey(), "1/", controller, display));
            } else if ("square".equals(entry.getKey())) {
                operator.add(CreateButton.createOpButton(entry.getKey(), entry.getKey(), "square", controller, display));
            } else {
                operator.add(CreateButton.createOpButton(entry.getKey(), entry.getKey(), entry.getKey(), controller, display));
            }
        }
        this.add(operator, BorderLayout.EAST);
    }
    /**
     * Returns the StandardCalculatorPanel's display.
     * @return display 
     */
    public CCDisplay getDisplay() {
        return this.display;
    }
}
