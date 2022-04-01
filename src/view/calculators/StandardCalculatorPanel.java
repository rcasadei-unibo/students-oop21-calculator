package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.calculators.CalculatorControllerTemplate;
//TODO MISSING JAVADOC.
/**
 * 
 * MISSING JAVADOC.
 *
 */
public class StandardCalculatorPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -3801351406960094788L;
    private JPanel numbers = new JPanel();
    private JPanel operators = new JPanel();
    private JTextArea output = new JTextArea();
    private ActionListener al = new ActionListener() {

        @Override
        public void actionPerformed(final ActionEvent e) {
            //TODO add event
        }
    };
    //TODO MISSING JAVADOC.
    /**
     * MISSING JAVADOC.
     * @param calculatorTemplate 
     */
    public StandardCalculatorPanel(CalculatorControllerTemplate calculatorTemplate) {
        this.add(output, new BorderLayout().NORTH);
        this.numbers.setLayout(new GridLayout(3, 4));
        this.setNumbers();
        this.add(numbers,new BorderLayout().CENTER);
        this.operators.setLayout(new GridLayout());
       
    }
    private void setNumbers() {
        final var coma = new JButton(",");
        coma.addActionListener(al);
        this.numbers.add(coma);
        

        /*for (int i = 9; i >= 0; i--) {
            final var btn = new JButton(String.valueOf(i));
            btn.addActionListener(al);
            this.numbers.add(btn);
        }*/
    }
    private void setOperators() {
        var btn = new JButton("+");
        btn.addActionListener(al);
    }
}
