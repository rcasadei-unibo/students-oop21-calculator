package view.calculators;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import view.components.CCDisplay;
import view.components.CCNumPad;
import controller.calculators.CalculatorController;
import model.manager.EngineModelInterface.Calculator;
import view.logics.CreateButton;
import view.logics.InputFormatterLogics;
import view.logics.InputFormatterLogicsImpl;
import view.logics.OutputFormatterLogics;
import view.logics.OutputFormatterLogicsImpl;
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
public class ScientificCalculatorPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -3801351406960094788L;
    private final CCDisplay display = new CCDisplay();
    private final CalculatorController controller = Calculator.SCIENTIFIC.getController();
    private final InputFormatterLogics inFormatter;
    private final OutputFormatterLogics outFormatter;
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
    public ScientificCalculatorPanel() {
        this.inFormatter = new InputFormatterLogicsImpl(this.controller);
        this.outFormatter = new OutputFormatterLogicsImpl(this.controller, this.display);
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);
        this.setNumbers();
        this.setOperators();
        this.setScientificOperators();
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
                    outFormatter.addResult(history);
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
        final var standardOp = List.of("+", "-", "×", "÷", "%", "1/x", "√", "x²");
        standardOp.forEach((op) -> {
            final JButton btn = CreateButton.createOpButtonFR(op);
            btn.addActionListener(e -> {
               this.inFormatter.read(op);
               this.outFormatter.updateDisplay();
            });
            operator.add(btn);
        });

        this.add(operator, BorderLayout.EAST);
    }

    private void setScientificOperators() {
        final JPanel scientificOperator = new JPanel();
        scientificOperator.setLayout(new GridLayout(4 + 2, 2));
        final var scientificOp = List.of("log", "ln", "nthRoot", "^", "abs", "factorial", "sin", "cos", "tan", "csc", "sec", "cot");
        scientificOp.forEach((op) -> {
            final JButton btn = CreateButton.createOpButtonFR(op);
            btn.addActionListener(e -> {
               this.inFormatter.read(op);
               this.outFormatter.updateDisplay();
            });
            scientificOperator.add(btn);
        });

        this.add(scientificOperator, BorderLayout.WEST);
    }
    /**
     * Returns the StandardCalculatorPanel's display.
     * @return display 
     */
    public CCDisplay getDisplay() {
        return this.display;
    }
}

