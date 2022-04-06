package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.calculators.CalculatorController;
import utils.CalcException;
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
    private static final Map<String, String> OPERATIONS = Map.of("Sequences", "sequencesNumber", "Dispositions", "factorial",
            "Subsets", "binomialCoefficient", "Derangements", "scombussolamento", "Partitions", "bellNumber",
            "Partitions(binary)", "stirlingNumber", "Fibonacci", "fibonacci", "Fibonacci(binary)", "binaryFibonacci");
    private static final List<String> OPERATIONSLIST = List.of("Sequences", "Dispositions", "Subsets",
            "Derangements", "Partitions", "Partitions(binary)", "Fibonacci", "Fibonacci(binary)");
    /**
     * 
     * @param controller
     */
    public CombinatoricsCalculatorPanel(final CalculatorController controller) {
        final var display = new CCDisplay();
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);
        controller.setDisplay(display);
        final ActionListener btnAl = e -> {
            final var btn = (JButton) e.getSource();
            controller.getManager().read(btn.getText());
            try {
                display.updateText(this.getDisplayText(controller));
            } catch (CalcException e1) {
                this.clearStrings();
                controller.getManager().clear();
                display.updateText("Invalid Operation");
            }
        };
        final ActionListener calculateAl = e -> {
            String adder = "";
            try {
                adder = controller.isBinaryOperator(opString) ? controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b).split(opString)[1] : "";
            } catch (ArrayIndexOutOfBoundsException e2) {
                this.clearStrings();
                controller.getManager().clear();
            }
            adder += opString.isBlank() ? "" : ") =";
            display.updateUpperText(opFormat + adder);
            controller.getManager().calculate();
            display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
            this.clearStrings();
        };
        final ActionListener backspaceAl = e -> {
            if (controller.getManager().getCurrentState().get(controller.getManager().getCurrentState().size() - 1).length() > 1) {
                this.clearStrings();
            }
            controller.getManager().deleteLast();
            try {
                display.updateText(this.getDisplayText(controller));
            } catch (CalcException e1) {
                this.clearStrings();
                controller.getManager().clear();
                display.updateText(" ");
            }
        };
        final var numpad = new CCNumPad(btnAl, calculateAl, backspaceAl);
        numpad.getButtons().get("(").setEnabled(false);
        numpad.getButtons().get(")").setEnabled(false);
        numpad.getButtons().get(".").setEnabled(false);
        this.add(numpad, BorderLayout.CENTER);
        final var explLabel = new JLabel();
        this.add(explLabel, BorderLayout.SOUTH);
        this.add(new OperationsPanel(controller, display, explLabel), BorderLayout.EAST);

    }
    private String getDisplayText(final CalculatorController controller) throws CalcException {
        if (!opFormat.isBlank()) {
            if (!opString.isBlank() && controller.isBinaryOperator(opString)) {
                try {
                    return opFormat + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b).split(opString)[1];
                } catch (ArrayIndexOutOfBoundsException e3) {
                    return opFormat;
                }
            } else {
                throw new CalcException("Invalid operation");
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
        private final CalculatorController controller;
        private final CCDisplay display;
        private final JLabel explLabel;
        private String labelText = "";
        public static final String SEP = File.separator;
        public static final String DIRECTORY = System.getProperty("user.dir") + SEP + "src" + SEP + "utils" + SEP + "combOpExpl" + SEP;

        OperationsPanel(final CalculatorController controller, final CCDisplay display, final JLabel explLabel) {
            this.display = display;
            this.explLabel = explLabel;
            this.controller = controller;
            this.setLayout(new GridLayout(8, 2));
            OPERATIONSLIST.forEach(str -> {
                this.createOpButton(str, OPERATIONS.get(str));
                this.createExplButton(str);
            });
        }
        private void createOpButton(final String btnName, final String opName) {
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
        private void createExplButton(final String opName) {
            final var btn = new JButton("?");
            btn.setToolTipText(opName);
            btn.addActionListener(e -> {
                final var file = DIRECTORY + opName + ".txt";
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String str = br.readLine();
                    while (str != null) {
                        this.labelText += str;
                        str = br.readLine();
                    }
                } catch (FileNotFoundException e1) {
                    this.labelText = "FILE NOT FOUND" + file;
                } catch (IOException e1) {
                    this.labelText = "I/O ERROR";
                }
                explLabel.setText(this.labelText);
                this.labelText = "";
            });
            this.add(btn);
        }
    }
}
