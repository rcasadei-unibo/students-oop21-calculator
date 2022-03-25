package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.manager.CCManager;
import utils.AbstractCalculator;
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
    /**
     * 
     * @param controller
     */
    public CombinatoricsCalculatorPanel(final AbstractCalculator controller) {
        this.setLayout(new BorderLayout());
        final var display = new CCDisplay();
        this.add(display, BorderLayout.NORTH);
        controller.setDisplay(display);
        final ActionListener btnAl = e -> {
            final var btn = (JButton) e.getSource();
            controller.getManager().read(btn.getText());
            display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
        };
        final ActionListener calculateAl = e -> {
            display.updateUpperText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + " " + b) + " =");
            controller.getManager().calculate();
            display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
        };
        final ActionListener backspaceAl = e -> {
            controller.getManager().deleteLast();
            display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
        };
        final var numpad = new CCNumPad(btnAl, calculateAl, backspaceAl);
        numpad.getButtons().get("(").setEnabled(false);
        numpad.getButtons().get(")").setEnabled(false);
        numpad.getButtons().get(".").setEnabled(false);
        this.add(numpad, BorderLayout.CENTER);
        this.add(new OperationsPanel(controller, display), BorderLayout.EAST);
    }
    static class OperationsPanel extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        OperationsPanel(final AbstractCalculator controller, final CCDisplay display) {
            this.setLayout(new GridLayout(8, 1));
            final var part = new JButton("Partizioni");
            part.addActionListener(e -> {
                controller.getManager().read("Bell number");
                display.updateText("Bell(" + controller.getManager().getCurrentState().toString());
            });
            this.add(part);
            
            final var fact = new JButton("Factorial");
            fact.addActionListener(e -> {
                display.updateText("Fact(" + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + " " + b) + ", ");
                controller.getManager().read("factorial");
            });
            this.add(fact);
            
            final var binomCoeff = new JButton("Binomial Coefficient");
            binomCoeff.addActionListener(e -> {
                display.updateText("BinCoeff" + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + " " + b) + ", ");
                controller.getManager().read("binomial coefficient");
            });
            this.add(binomCoeff);
            
            final var seq = new JButton("Sequences");
            seq.addActionListener(e -> {
                display.updateText("Sequences(" + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + " " + b) + ", ");
                controller.getManager().read("sequencesNumber");
            });
            this.add(seq);
            
            final var binFib = new JButton("Fibonacci(binary)");
            binFib.addActionListener(e -> {
                display.updateText("Fibonacci(" + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + " " + b) + ", ");
                controller.getManager().read("binaryFibonacci");
            });
            this.add(binFib);
            
            final var fib = new JButton("Fibonacci");
            fib.addActionListener(e -> {
                display.updateText("Fibonacci(" + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + " " + b));
                controller.getManager().read("fibonacci");
            });
            this.add(fib);
            
            final var stirling = new JButton("Stirling number");
            stirling.addActionListener(e -> {
                display.updateText("Stirling(" + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + " " + b) + ", ");
                controller.getManager().read("Stirling number");
            });
            this.add(stirling);
            
            final var scomb = new JButton("Scombussolamento");
            scomb.addActionListener(e -> {
                display.updateText("Scombussolamenti di" + controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + " " + b));
                controller.getManager().read("scombussolamento");
            });
            this.add(scomb);
        }
    }
}
