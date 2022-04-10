package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.util.List;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.calculators.CalculatorAdvancedController;
import controller.calculators.CalculatorAdvancedController.TypeAlgorithm;
import controller.calculators.CalculatorController;
import utils.CalcException;
import view.components.CCDisplay;
import view.components.CCNumPad;

/**
 * @author pesic
 *
 */
public class AdvancedCalculatorPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private CalculatorAdvancedController advancedController;
    private OperationsPanel operationsPanel;
    /**
     * @param controller
     */
    public AdvancedCalculatorPanel(final CalculatorController controller) {
        this.advancedController = new CalculatorAdvancedController(controller);
        final var display = new CCDisplay();
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);
        this.operationsPanel = new OperationsPanel(advancedController);
        
        final ActionListener numAndOpBtn = e -> {
            final var btn = (JButton) e.getSource();
            advancedController.read(btn.getText());
            display.updateText(this.advancedController.getCurrentState());
        };
        
        final ActionListener deleteBtn = e -> {
            this.advancedController.deleteLast();
            display.updateText(this.advancedController.getCurrentState());
        };
        // it should output an error in the screen but when you press any button should you cancel it
        final ActionListener equalsBtn = e -> {
            this.advancedController.setParameters(this.operationsPanel.getParameters());
            String result = "Syntax Error";
            boolean isError = false;
            try {
                result = this.advancedController.calculate();
            } catch (Exception error) { //modify your code so it throws always calcExceptions or something else
                this.advancedController.reset();
                isError = true;
            }
            if (!isError) {
            advancedController.read(result);
            display.updateText(this.advancedController.getCurrentState());
            } else {
                display.updateText(result);
            }
        };

        final var numpad = new CCNumPad(numAndOpBtn, equalsBtn, deleteBtn);
        this.add(numpad, BorderLayout.CENTER);
        this.add(this.getOperatorsPanel(numAndOpBtn), BorderLayout.WEST);
        this.add(this.operationsPanel, BorderLayout.EAST);
    }
    
    private JPanel getOperatorsPanel(final ActionListener al) {
        final JPanel operators = new JPanel();
        operators.setLayout(new GridLayout(5, 3));
        final List<String> buttons = List.of("+", "-", "\u00D7", "÷", "^", "sin", "cos", "log", "tan", "sqrt", "abs", "csc", "sec", "cot", "x");
        buttons.forEach(s -> {
            final var btn = new JButton(s);
            btn.addActionListener(al);
            operators.add(btn);
        });
        return operators;
    }
    /**
     * @author pesic
     *
     */
    public class OperationsPanel extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private final GridBagConstraints c = new GridBagConstraints();
        private final String[] choices = {"DERIVATE", "INTEGRATE", "LIMIT"};
        private final JComboBox<String> combo = new JComboBox<>(choices);
        private final JFormattedTextField param1 = new JFormattedTextField();
        private final JLabel label1 = new JLabel("Param1: ");
        private final JFormattedTextField param2 = new JFormattedTextField();
        private final JLabel label2 = new JLabel("Param2: ");
        /**
         * @param advancedController
         */
        public OperationsPanel(final CalculatorAdvancedController advancedController) {
            this.setLayout(new GridBagLayout());
            c.weightx = 1;
            c.weighty = 1;
            c.anchor = GridBagConstraints.NORTHWEST;
            this.add(combo);
            this.param1.setColumns(10);
            this.param2.setColumns(10);
            c.gridx = 0;
            c.gridy = 1;
            this.add(label1, c);
            c.gridx = 1;
            c.gridy = 1;
            this.add(param1, c);
            c.gridx = 0;
            c.gridy = 2;
            this.add(label2, c);
            c.gridx = 1;
            c.gridy = 2;
            this.add(param2, c);

            final ActionListener selectChoice = e -> {
                final var selected = combo.getSelectedItem().toString();
                if (selected.equals("DERIVATE")) {
                    this.selectedDerivate();
                } else if (selected.equals("INTEGRATE")) {
                    this.selectedIntegrate();
                } else {
                    this.selectedLimit();
                }
            };
            
            combo.addActionListener(selectChoice);
            this.selectedDerivate();
        }
        /**
         * @return c
         */
        public List<String> getParameters() {
            return List.of(param1.getText(), param2.getText());
        }
        private void selectedDerivate() {
            advancedController.setOperation(TypeAlgorithm.DERIVATE);
            System.out.println("DERIVATE");
        }
        private void selectedIntegrate() {
            advancedController.setOperation(TypeAlgorithm.INTEGRATE);
            System.out.println("INTEGRATION");
        }
        private void selectedLimit() {
            advancedController.setOperation(TypeAlgorithm.LIMIT);
            System.out.println("LIMIT");
        }
    }
}
