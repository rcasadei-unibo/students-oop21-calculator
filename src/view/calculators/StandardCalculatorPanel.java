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
import view.logics.StandardInputFormatter;
import view.logics.StandardOutputFormatter;
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
    private final StandardInputFormatter inFormatter = new StandardInputFormatter();
    private final StandardOutputFormatter outFormatter = new StandardOutputFormatter(this.display);
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
                inFormatter.read(((JButton) e.getSource()).getText());
                outFormatter.updateDisplay();
            }
        };
        final ActionListener calcAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (!controller.getManager().memory().getCurrentState().isEmpty()) {
                    final String history = outFormatter.format();
                    outFormatter.updateDisplayUpperText();
                    inFormatter.calculate();
                    if (!controller.getManager().memory().getCurrentState().contains("Syntax error")) {
                        controller.getManager().memory().addResult(history.concat(" = ").concat(controller.getManager().memory().getCurrentState().stream().reduce("", (a, b) -> a + b)));
                    }
                }
                outFormatter.updateDisplay();
            }
        };
        final ActionListener backspaceAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                inFormatter.deleteLast();
                outFormatter.updateDisplay();
            }
        };
        final JPanel numbers = new CCNumPad(btnAl, calcAl, backspaceAl);
        this.add(numbers, BorderLayout.CENTER);
    }
    private void setOperators() {
        final JPanel operator = new JPanel();
        operator.setLayout(new GridLayout(4, 2));
        for (final var entry : StandardCalculatorModelFactory.create().getBinaryOpMap().entrySet()) {
            final JButton op = CreateButton.createOpButtonFR(entry.getKey());
            op.addActionListener(e -> {
                inFormatter.read(entry.getKey());
                outFormatter.updateDisplay();
            });
            operator.add(op);
        }
        for (final var entry : StandardCalculatorModelFactory.create().getUnaryOpMap().entrySet()) {
            final JButton op = CreateButton.createOpButtonFR(entry.getKey());
            op.addActionListener(e -> {
                inFormatter.read(entry.getKey());
                outFormatter.updateDisplay();
            });
            operator.add(op);
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
