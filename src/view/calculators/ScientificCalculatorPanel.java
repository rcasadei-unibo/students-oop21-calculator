package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.calculators.ScientificCalculatorModelFactory;
import model.calculators.StandardCalculatorModelFactory;
import model.manager.EngineModelInterface.Calculator;
import view.components.CCDisplay;
import view.components.CCNumPad;
import view.logics.CreateButton;
import view.logics.ScientificInputFormatter;
import view.logics.ScientificOutputFormatter;
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
            public void actionPerformed(ActionEvent e) {
                inFormatter.read(((JButton)e.getSource()).getText());
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
                    Calculator.SCIENTIFIC.getController().getManager().memory().getCurrentState().stream().reduce("", (a,b) -> a + b)));
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
        this.add(this.getStandardPanel(), BorderLayout.CENTER);
        this.add(this.getScientificOperators(), BorderLayout.WEST);
    }

    private JPanel getStandardPanel() {
        final JPanel standard = new JPanel();
        standard.setLayout(new BorderLayout());
        
        standard.add(this.numpad, BorderLayout.CENTER);
        final JPanel standardOp = new JPanel();
        standardOp.setLayout(new GridLayout(4, 2));
        StandardCalculatorModelFactory.create().getBinaryOpMap().entrySet().forEach((entry) -> {
            standardOp.add(this.setActionListener(entry.getKey()));
        });
        StandardCalculatorModelFactory.create().getUnaryOpMap().entrySet().forEach((entry) -> {
            standardOp.add(this.setActionListener(entry.getKey()));
        });
        
        
        return standard;
    }

    private JPanel getScientificOperators() {
       final JPanel scientificOperators = new JPanel();
       scientificOperators.setLayout(new GridLayout(4, 3));
       ScientificCalculatorModelFactory.create().getBinaryOpMap().entrySet().forEach((entry) -> {
           scientificOperators.add(this.setActionListener(entry.getKey()));
       });
       ScientificCalculatorModelFactory.create().getUnaryOpMap().entrySet().forEach((entry) -> {
           scientificOperators.add(this.setActionListener(entry.getKey()));
       });
       return scientificOperators;
    }
    private JButton setActionListener(final String text) {
        final var btn = CreateButton.createOpButtonFR(text);
        btn.addActionListener(e -> {
            this.inFormatter.read(((JButton)e.getSource()).getText());
            this.outFormatter.updateDisplay();
        });
        return btn;
    }

}
