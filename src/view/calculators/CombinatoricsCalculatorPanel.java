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
import model.manager.EngineModelInterface.Calculator;
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
    /**
     * 
     */
    public CombinatoricsCalculatorPanel() {
        final CombinatoricsLogics logics = new CombinatoricsLogicsImpl();
        final var display = new CCDisplay();
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);
        final var explLabel = new JLabel();
        Calculator.COMBINATORICS.getController().setDisplay(display);
        final ActionListener btnAl = e -> {
            final var btn = (JButton) e.getSource();
            display.updateText(logics.numberAction(btn.getText()));
        };
        final ActionListener calculateAl = e -> {
            display.updateUpperText(logics.calculateAction());
            display.updateText(logics.getStream());
            explLabel.setText("");
        };
        final ActionListener backspaceAl = e -> {
            display.updateText(logics.backspaceAction());
        };
        final var numpad = new CCNumPad(btnAl, calculateAl, backspaceAl);
        numpad.getButtons().get("(").setEnabled(false);
        numpad.getButtons().get(")").setEnabled(false);
        numpad.getButtons().get(".").setEnabled(false);
        this.add(numpad, BorderLayout.CENTER);
        this.add(explLabel, BorderLayout.SOUTH);
        this.add(new OperationsPanel(logics, display, explLabel), BorderLayout.EAST);

    }
    /**
     * 
     * MISSING JAVADOC.
     *
     */
    static class OperationsPanel extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private final String sep = File.separator;
        private final String directory = System.getProperty("user.dir") + this.sep + "src" + this.sep + "utils" + this.sep + "combOpExpl" + this.sep;
        private final Map<String, String> opMap = Map.of("Sequences", "sequencesNumber", "Dispositions", "factorial",
                "Subsets", "binomialCoefficient", "Derangements", "derangement", "Partitions", "bellNumber",
                "Partitions(binary)", "stirlingNumber", "Fibonacci", "fibonacci", "Fibonacci(binary)", "binaryFibonacci");
        private final List<String> opList = List.of("Sequences", "Dispositions", "Subsets",
                "Derangements", "Partitions", "Partitions(binary)", "Fibonacci", "Fibonacci(binary)");

        OperationsPanel(final CombinatoricsLogics logics, final CCDisplay display, final JLabel explLabel) {
            this.setLayout(new GridLayout(8, 2));
            this.opList.forEach(str -> {
                this.createOpButton(str, this.opMap.get(str), logics, display);
                this.createExplButton(str, explLabel);
            });
        }
        private void createOpButton(final String btnName, final String opName, final CombinatoricsLogics logics, final CCDisplay display) {
            final var btn = new JButton(btnName);
            btn.addActionListener(e -> {
                display.updateText(logics.opAction(btnName, opName));
            });
            this.add(btn);
        }
        private void createExplButton(final String opName, final JLabel explLabel) {
            final var file = this.directory + opName;
            final var btn = new JButton("?");
            ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
            btn.setToolTipText(this.readFromFile(file + "(TT)"));
            final String labelText = this.readFromFile(file);
            btn.addActionListener(e -> {
                explLabel.setText(explLabel.getText().equals(labelText) ? "" : labelText);
            });
            this.add(btn);
        }
        private String readFromFile(final String file) {
            String result = "<html><p width=\"500\">";
            try (BufferedReader br = new BufferedReader(new FileReader(file + ".txt"))) {
                String str = br.readLine();
                while (str != null) {
                    result = result.concat(str + "<br>");
                    str = br.readLine();
                }
            } catch (FileNotFoundException e1) {
                return "FILE NOT FOUND " + file;
            } catch (IOException e1) {
                return "I/O ERROR";
            }
            return result + "</p></html>";
        }
    }
}
