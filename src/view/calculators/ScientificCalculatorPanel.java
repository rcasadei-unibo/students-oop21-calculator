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
import view.components.ScientificOperatorsPanel;
import controller.calculators.CalculatorController;
import controller.calculators.logics.InputFormatterLogics;
import controller.calculators.logics.InputFormatterLogicsImpl;
import controller.calculators.logics.OutputFormatterLogics;
import controller.calculators.logics.OutputFormatterLogicsImpl;
import model.manager.EngineModelInterface.Calculator;
import utils.CreateButton;

/**
 * 
 */
public class ScientificCalculatorPanel extends JPanel {
    /**
     * This is ScientificCalculatorPanel which holds the basic operators of the StandardCalculatorPanel and the scientific operators, as well as the PI and E constants:
     * 
     * -log₁₀ and ln.
     * -nthRoot.
     * -xʸ .
     * -absolute value .
     * -factorial.
     * -Sin, Cos and Tan operators.
     * -Csc, Sec and Cot operators.
     * -PI and Euler's constants.
     * 
     */
    private static final long serialVersionUID = -3801351406960094788L;
    private final CCDisplay display = new CCDisplay();
    private final transient CalculatorController controller = Calculator.SCIENTIFIC.getController();
    private final transient InputFormatterLogics inFormatter;
    private final transient OutputFormatterLogics outFormatter;
    /**
     * Initialize and add all the components.
     */
    public ScientificCalculatorPanel() {
        this.setLayout(new BorderLayout());
        this.inFormatter = new InputFormatterLogicsImpl(this.controller);
        this.outFormatter = new OutputFormatterLogicsImpl(this.controller, this.display);
        final ScientificOperatorsPanel scientificOp = new ScientificOperatorsPanel(this.inFormatter, this.outFormatter);
        this.add(scientificOp, BorderLayout.WEST);
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
            final JButton btn = CreateButton.createOpButton(op);
            btn.addActionListener(e -> {
               this.inFormatter.read(op);
               this.outFormatter.updateDisplay();
            });
            operator.add(btn);
        });

        this.add(operator, BorderLayout.EAST);
    }
}

