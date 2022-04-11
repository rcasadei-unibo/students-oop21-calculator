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
import javax.swing.ToolTipManager;

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
    private static final Map<String, String> OPERATIONS = Map.of("Sequences", "sequencesNumber", "Dispositions", "factorial",
            "Subsets", "binomialCoefficient", "Derangements", "scombussolamento", "Partitions", "bellNumber",
            "Partitions(binary)", "stirlingNumber", "Fibonacci", "fibonacci", "Fibonacci(binary)", "binaryFibonacci");
    private static final List<String> OPERATIONSLIST = List.of("Sequences", "Dispositions", "Subsets",
            "Derangements", "Partitions", "Partitions(binary)", "Fibonacci", "Fibonacci(binary)");
    /**
     * 
     */
    public CombinatoricsCalculatorPanel() {
        final CombinatoricsLogics logics = new CombinatoricsLogicsImpl();
        final var display = new CCDisplay();
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);
        Calculator.COMBINATORICS.getController().setDisplay(display);
        final ActionListener btnAl = e -> {
            final var btn = (JButton) e.getSource();
            display.updateText(logics.numberAction(btn.getText()));
        };
        final ActionListener calculateAl = e -> {
            display.updateUpperText(logics.calculateAction());
            display.updateText(logics.getStream());
        };
        final ActionListener backspaceAl = e -> {
            display.updateText(logics.backspaceAction());
        };
        final var numpad = new CCNumPad(btnAl, calculateAl, backspaceAl);
        numpad.getButtons().get("(").setEnabled(false);
        numpad.getButtons().get(")").setEnabled(false);
        numpad.getButtons().get(".").setEnabled(false);
        this.add(numpad, BorderLayout.CENTER);
        final var explLabel = new JLabel();
        this.add(explLabel, BorderLayout.SOUTH);
        this.add(new OperationsPanel(logics, display, explLabel), BorderLayout.EAST);

    }
    static class OperationsPanel extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private final CombinatoricsLogics logics;
        private final CCDisplay display;
        private final JLabel explLabel;
        public static final String SEP = File.separator;
        public static final String DIRECTORY = System.getProperty("user.dir") + SEP + "src" + SEP + "utils" + SEP + "combOpExpl" + SEP;

        OperationsPanel(final CombinatoricsLogics logics, final CCDisplay display, final JLabel explLabel) {
            this.display = display;
            this.explLabel = explLabel;
            this.logics = logics;
            this.setLayout(new GridLayout(8, 2));
            OPERATIONSLIST.forEach(str -> {
                this.createOpButton(str, OPERATIONS.get(str));
                this.createExplButton(str);
            });
        }
        private void createOpButton(final String btnName, final String opName) {
            final var btn = new JButton(btnName);
            btn.addActionListener(e -> {
                display.updateText(this.logics.opAction(btnName, opName));
            });
            this.add(btn);
        }
        private void createExplButton(final String opName) {
            final var file = DIRECTORY + opName;
            final var btn = new JButton("?");
            ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
            btn.setToolTipText(this.readFromFile(file + "(TT)"));
            btn.addActionListener(e -> {
                explLabel.setText(this.readFromFile(file));
            });
            this.add(btn);
        }
        private String readFromFile(final String file) {
            String result = "";
            try (BufferedReader br = new BufferedReader(new FileReader(file + ".txt"))) {
                String str = br.readLine();
                while (str != null) {
                    result = result.concat(str + "\n");
                    str = br.readLine();
                }
            } catch (FileNotFoundException e1) {
                return "FILE NOT FOUND " + file;
            } catch (IOException e1) {
                return "I/O ERROR";
            }
            return result;
        }
    }
}
