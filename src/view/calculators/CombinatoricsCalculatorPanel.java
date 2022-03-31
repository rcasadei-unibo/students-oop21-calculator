package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.calculators.CalculatorTemplate;
import view.components.CCDisplay;
import view.components.CCNumPad;
/**
 * 
 * MISSING JAVADOC.
 *
 */
public class CombinatoricsCalculatorPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static String opFormat = "";
    private static String opString = "";
    private static final Map<String, String> OPERATIONS = Map.of("Sequences", "sequencesNumber", "Factorial", "factorial",
            "Binomial Coefficient", "binomialCoefficient", "Scombussolamento", "scombussolamento", "Partitions", "bellNumber",
            "Partitions(binary)", "stirlingNumber", "Fibonacci", "fibonacci", "Fibonacci(binary)", "binaryFibonacci");
    /**
     * 
     * @param controller
     */
    public CombinatoricsCalculatorPanel(final CalculatorTemplate controller) {
        final var display = new CCDisplay();
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);
        controller.setDisplay(display);
        final ActionListener btnAl = e -> {
            final var btn = (JButton) e.getSource();
            controller.getManager().read(btn.getText());
            display.updateText(this.getDisplayText(controller));
        };
        final ActionListener calculateAl = e -> {
            final String adder = controller.isBinaryOperator(opString) ? controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b).split(opString)[1] : "";
            display.updateUpperText(opFormat + adder + ") =");
            controller.getManager().calculate();
            display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
            this.clearStrings();
        };
        final ActionListener backspaceAl = e -> {
            if (controller.getManager().getCurrentState().get(controller.getManager().getCurrentState().size() - 1).length() > 1) {
                this.clearStrings();
            }
            controller.getManager().deleteLast();
            display.updateText(this.getDisplayText(controller));
        };
        final var numpad = new CCNumPad(btnAl, calculateAl, backspaceAl);
        numpad.getButtons().get("(").setEnabled(false);
        numpad.getButtons().get(")").setEnabled(false);
        numpad.getButtons().get(".").setEnabled(false);
        this.add(numpad, BorderLayout.WEST);
        this.add(new OperationsPanel(controller, display), BorderLayout.CENTER);
        this.add(new ExplainationPanel(), BorderLayout.EAST);
    }
    private String getDisplayText(final CalculatorTemplate controller) {
        if (!opFormat.isBlank()) {
            if (!opString.isBlank() && controller.isBinaryOperator(opString)) {
                return opFormat + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b).split(opString)[1];
            } else {
                return opFormat + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b);
            }
        } else {
            return controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b);
        }
    }
    private void clearStrings() {
        CombinatoricsCalculatorPanel.opFormat = "";
        CombinatoricsCalculatorPanel.opString = "";
    }
    static class OperationsPanel extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private final CalculatorTemplate controller;
        private final CCDisplay display;

        OperationsPanel(final CalculatorTemplate controller, final CCDisplay display) {
            this.display = display;
            this.controller = controller;
            this.setLayout(new GridLayout(8, 1));
            OPERATIONS.forEach((str1, str2) -> this.createButton(str1, str2));
        }
        private void createButton(final String btnName, final String opName) {
            final var btn = new JButton(btnName);
            btn.addActionListener(e -> {
                final String closer = controller.isBinaryOperator(opName) ? ", " : "";
                opFormat = btnName + "(" + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b) + closer;
                display.updateText(opFormat);
                controller.getManager().read(opName);
                opString = opName;
            });
            this.add(btn);
        }
    }
    static class ExplainationPanel extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        ExplainationPanel() {
            this.setLayout(new GridLayout(8, 1));
            OPERATIONS.forEach((str1, str2) -> this.createButton(str1));
        }
        private void createButton(final String opName) {
            final var btn = new JButton("?");
            btn.setToolTipText(opName);
            this.add(btn);
        }
    }
}
