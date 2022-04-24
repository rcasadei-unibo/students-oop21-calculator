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
import controller.calculators.logics.InputFormatterLogics;
import controller.calculators.logics.InputFormatterLogicsImpl;
import controller.calculators.logics.OutputFormatterLogics;
import controller.calculators.logics.OutputFormatterLogicsImpl;
import model.manager.EngineModelInterface.Calculator;
import utils.CCColors;
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
    private static final int COLUMNS = 2;
    private static final int LINES = 7;
    private final CCDisplay display = new CCDisplay();
    private final transient CalculatorController controller = Calculator.SCIENTIFIC.getController();
    private final transient InputFormatterLogics inFormatter;
    private final transient OutputFormatterLogics outFormatter;
    /**
     * Initialize and add all the components.
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
            final JButton btn = CreateButton.createOpButton(op);
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
        scientificOperator.setLayout(new GridLayout(LINES, COLUMNS));
        this.addConstants(scientificOperator);
        final var scientificOp = List.of("log", "ln", "root", "^", "abs", "factorial", "sin", "cos", "tan", "csc", "sec", "cot");
        scientificOp.forEach((op) -> {
            switch (op) {
               case "root":
                   final JButton btn1 = CreateButton.createOpButton("ʸ√x");
                   btn1.addActionListener(e -> {
                       this.inFormatter.read(op);
                       this.outFormatter.updateDisplay();
                    });
                    scientificOperator.add(btn1);
                   break;
               case "factorial":
                   final JButton btn2 = CreateButton.createOpButton("!n");
                   btn2.addActionListener(e -> {
                       this.inFormatter.read(op);
                       this.outFormatter.updateDisplay();
                    });
                    scientificOperator.add(btn2);
                   break;
               case "^":
                   final JButton btn3 = CreateButton.createOpButton("xʸ");
                   btn3.addActionListener(e -> {
                       this.inFormatter.read(op);
                       this.outFormatter.updateDisplay();
                    });
                    scientificOperator.add(btn3);
                   break;
               default :
                   final JButton btn = CreateButton.createOpButton(op);
                   btn.addActionListener(e -> {
                       this.inFormatter.read(op);
                       this.outFormatter.updateDisplay();
                    });
                    scientificOperator.add(btn);
                    break;
            }
        });
        this.add(scientificOperator, BorderLayout.WEST);
    }

    private void addConstants(final JPanel panel) {
        final JButton pi = new JButton("\u03C0");
        pi.setBackground(CCColors.OPERATION_BUTTON);
        pi.addActionListener(e -> {
            inFormatter.read(Double.toString(Math.PI));
            outFormatter.updateDisplay();
        });
        final JButton eul = new JButton("\u2107");
        eul.setBackground(CCColors.OPERATION_BUTTON);
        eul.addActionListener(e -> {
            inFormatter.read(Double.toString(Math.E));
            outFormatter.updateDisplay();
        });
        panel.add(eul);
        panel.add(pi);
    }
}

