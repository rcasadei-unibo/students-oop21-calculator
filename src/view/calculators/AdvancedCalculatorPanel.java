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
import model.manager.EngineModelInterface.Calculator;
import controller.calculators.CalculatorController;
import utils.CCColors;
import utils.CommandFactory;
import view.components.CCDisplay;
import view.components.CCNumPad;

    /**
     * 
    * The Advanced Calculator Panel.
     *
     */
    public class AdvancedCalculatorPanel extends JPanel {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private final CalculatorAdvancedController advancedController;
        private final OperationsPanel operationsPanel;
        /**
         *
         */
        public AdvancedCalculatorPanel() {

            this.advancedController = new CalculatorAdvancedController(Calculator.ADVANCED.getController());
            final var display = new CCDisplay();
            this.setLayout(new BorderLayout());
            this.add(display, BorderLayout.NORTH);
            this.operationsPanel = new OperationsPanel(advancedController, display);

            final ActionListener numAndOpBtn = (e) -> {
                final List<String> buttons = List.of("sin", "cos", "log", "tan", "√", "abs", "csc", "sec", "cot");
                final var btn = (JButton) e.getSource();
                final var command = CommandFactory.insert(btn.getText(), buttons, () -> "(", advancedController);
                display.updateText(command.execute());
            };

            final ActionListener deleteBtn = e -> {
                CommandFactory.deleteLast(advancedController).execute();
                display.updateText(this.advancedController.getCurrentState());
            };
            final ActionListener equalsBtn = e -> {
                final var command  = CommandFactory.calculate(advancedController, operationsPanel.getParameters());
                final var command1 = CommandFactory.previousState(advancedController);
                final var result = command.execute();
                final var expression = command1.execute();
                display.updateText(result);
                display.updateUpperText(expression + "=" + result);
                CommandFactory.addToHistory(advancedController, expression + "=" + result).execute();
            };

            final var numpad = new CCNumPad(numAndOpBtn, equalsBtn, deleteBtn);
            this.add(numpad, BorderLayout.CENTER);
            this.add(this.getOperatorsPanel(numAndOpBtn), BorderLayout.WEST);
            this.add(this.operationsPanel, BorderLayout.EAST);
        }

        private JPanel getOperatorsPanel(final ActionListener al) {
            final JPanel operators = new JPanel();
            operators.setLayout(new GridLayout(5, 3));
            final List<String> buttons = List.of("+", "-", "\u00D7", "÷", "^", "sin", "cos", "log", "tan", "√", "abs", "csc", "sec", "cot");
            buttons.forEach(s -> {
                final var btn = new JButton(s);
                btn.addActionListener(al);
                btn.setBackground(CCColors.OPERATION_BUTTON);
                operators.add(btn);
            });
            final var btn = new JButton("x");
            btn.addActionListener(al);
            btn.setBackground(CCColors.NUMBER_BUTTON);
            operators.add(btn);
            return operators;
        }
        /**
         *The panel that allows to select the operation(DERIVATE INTEGRATE , LIMIT).
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
            private final CalculatorAdvancedController advancedController;
            /**
             * @param advancedController
             * @param display
             */
            public OperationsPanel(final CalculatorAdvancedController advancedController, final CCDisplay display) {
                this.advancedController = advancedController;
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
                    if ("DERIVATE".equals(selected)) {
                        this.selectedDerivate();
                    } else if ("INTEGRATE".equals(selected)) {
                        this.selectedIntegrate();
                    } else {
                        this.selectedLimit();
                    }
                    display.updateText("");
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
                label1.setText("not needed: ");
                label2.setText("not needed: ");
                CommandFactory.selectedOperation(advancedController, TypeAlgorithm.DERIVATE).execute();
            }
            private void selectedIntegrate() {
                label1.setText("lowerBound: ");
                label2.setText("upperBound: ");
                CommandFactory.selectedOperation(advancedController, TypeAlgorithm.INTEGRATE).execute();
            }
            private void selectedLimit() {
                label1.setText("x0 \u2250 : ");
                label2.setText("not needed: ");
                CommandFactory.selectedOperation(advancedController, TypeAlgorithm.LIMIT).execute();
            }
        }
    }