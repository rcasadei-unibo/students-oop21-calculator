package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.calculators.logics.CreateButton;
import controller.calculators.logics.ScientificInputFormatter;
import controller.calculators.logics.ScientificOutputFormatter;
import model.manager.EngineModelInterface.Calculator;
import view.components.CCDisplay;
import view.components.CCNumPad;
/**
 * 
 * MISSING JAVADOC.
 *
 */

public class ScientificCalculatorPanel extends JPanel{
    /**
     * 
     */
    private static final long serialVersionUID = 1795172134074823312L;
    
    private final ScientificInputFormatter inFormatter = new ScientificInputFormatter();
    private final CCDisplay display = new CCDisplay();
    private final ScientificOutputFormatter outFormatter = new ScientificOutputFormatter(this.display);
    private final CCNumPad numpad;
    private final transient ActionListener opAl;
    {
        opAl = new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent e) {
                inFormatter.read(((JButton) e.getSource()).getText());
                outFormatter.updateDisplay();
            }
        };
        
        this.numpad = new CCNumPad(
        opAl, calcAl -> {
            final String history = outFormatter.format();
            outFormatter.updateDisplayUpperText();
            inFormatter.calculate();
            outFormatter.updateDisplay();
            Calculator.SCIENTIFIC.getController().getManager().memory().addResult(history.concat("=").concat(history).concat(
                    Calculator.SCIENTIFIC.getController().getManager().memory().getCurrentState().stream().reduce("", (a, b) -> a + b)));
        }, deleteAl -> {
            inFormatter.deleteLast();
            outFormatter.updateDisplay();
        });
    }
    /**
     * 
     */
    public ScientificCalculatorPanel() {
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);
        this.add(this.getStandardPanel(), BorderLayout.CENTER);
        this.add(this.getScientificOperators(), BorderLayout.WEST);
    }

    private JPanel getStandardPanel() {
        final JPanel standard = new JPanel();
        standard.setLayout(new BorderLayout());
        
        standard.add(this.numpad, BorderLayout.CENTER);
        final JPanel standardOp = new JPanel();
        standardOp.setLayout(new GridLayout(3, 2));
        final var basicOp = List.of("+", "-", "×", "÷", "%", "1/x");
        basicOp.stream().forEach((str) -> {
            standardOp.add(this.setActionListener(str));
        });
        standard.add(standardOp,BorderLayout.EAST);
        return standard;
    }

    private JPanel getScientificOperators() {
       final JPanel scientificOperators = new JPanel();
       scientificOperators.setLayout(new GridLayout(4, 3));
       final var scientificOp = List.of("log","ln");
       final var scientificOp2 = List.of("^","nthRoot","abs","!","cos", "sin", "tan" ,"cot", "csc", "sec");
       scientificOp.stream().forEach((str) -> {
           scientificOperators.add(this.setActionListener(str));
       });
       scientificOp2.stream().forEach((str) -> {
           scientificOperators.add(this.setActionListener(str));
       });
       return scientificOperators;
    }
    private JButton setActionListener(final String text) {
        final var btn = CreateButton.createOpButtonFR(text);
        btn.addActionListener(e -> {
            if ("!".equals(((JButton)e.getSource()).getText())) {
                this.inFormatter.read("factorial");
            } else {
                this.inFormatter.read(((JButton)e.getSource()).getText());
            }
            this.outFormatter.updateDisplay();
        });
        return btn;
    }

}
