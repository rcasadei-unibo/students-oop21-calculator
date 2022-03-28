package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.calculators.StandardCalculatorModel;
import view.components.CCDisplay;
import view.components.CCNumPad;
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
    
    private ActionListener al = new ActionListener() {

        @Override
        public void actionPerformed(final ActionEvent e) {
            //TODO add event
        }
    };
    private JPanel display = new CCDisplay();
    //TODO MISSING JAVADOC.
    /**
     * MISSING JAVADOC.
     */
    public StandardCalculatorPanel() {
        this.setLayout(new BorderLayout());
        this.add(display,BorderLayout.NORTH);
        
        this.setNumbers();
        this.setOperators();
       
        this.repaint();
    }
    private void setNumbers() {
        
        ActionListener btnAl = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                
            }
        };
        ActionListener calcAl = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }
        };
        ActionListener backspaceAl = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }
        };
        final JPanel numbers = new CCNumPad(btnAl, calcAl, backspaceAl);

        this.add(numbers,BorderLayout.CENTER);
    }
    private void setOperators() {
        JPanel operator = new JPanel();
        operator.setLayout(new GridLayout(4,2));
        for(var entry : new StandardCalculatorModel().getUnaryOpMap().entrySet()) {
            var btn = new JButton(entry.getKey());
            btn.addActionListener(al);
            operator.add(btn);
        }
        for(var entry : new StandardCalculatorModel().getBinaryOpMap().entrySet()) {
            var btn = new JButton(entry.getKey());
            btn.addActionListener(al);
            operator.add(btn);
        }
        this.add(operator,BorderLayout.EAST);
    }
}
