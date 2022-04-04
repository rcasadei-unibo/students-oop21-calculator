package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import controller.calculators.CalculatorController;
import model.calculators.ProgrammerCalculatorModelFactory;
import view.components.CCDisplay;
import view.components.CCNumPad;

//TODO MISSING JAVADOC.
/**
 * 
 * MISSING JAVADOC.
 *
 */
public class ProgrammerCalculatorPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -8342823219976507443L;
    private final CalculatorController controller;
    private final CCDisplay display = new CCDisplay();
    private final Map<String, CCDisplay> convDisplays = new HashMap<>();
    private final List<JButton> hexadecimalLetters = new ArrayList<>();
    private final CCNumPad numpad;
    private ActionListener opAl;
    {
        final ActionListener btnAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.getManager().read(((JButton) e.getSource()).getText());
                display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
            }
        };
        final ActionListener calcAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.getManager().calculate();
                display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
            }
        };
        final ActionListener backspaceAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.getManager().deleteLast();
                display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));

            }
        };
        this.numpad = new CCNumPad(btnAl, calcAl, backspaceAl);
    }
    
    /**
     * @param controller
     */
    public ProgrammerCalculatorPanel(final CalculatorController controller) {
        this.controller = controller;
        this.setPanels();
    }
    private void setPanels() {
        this.setLayout(new BorderLayout());
        this.add(this.display, BorderLayout.NORTH);
        this.setConversionPanel();
        this.setNumpad();
    }
    private void setConversionPanel() {
        final ActionListener conv = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                //TODO fix
                final JButton btn = (JButton) e.getSource();
                convDisplays.get(btn.getText()).updateText(btn.getText());
            }
        };
        final JPanel conversionButtons = new JPanel();
        conversionButtons.setLayout(new GridLayout(4, 2));
        final JButton hex = new JButton("HEX");
        hex.addActionListener(conv);
        conversionButtons.add(hex);
        final CCDisplay hexDisplay = new CCDisplay();
        conversionButtons.add(hexDisplay);
        convDisplays.put("HEX",hexDisplay);
        final JButton dec = new JButton("DEC");
        dec.addActionListener(conv);
        conversionButtons.add(dec);
        final CCDisplay decDisplay = new CCDisplay();
        conversionButtons.add(decDisplay);
        convDisplays.put("HEX",decDisplay);
        final JButton oct = new JButton("OCT");
        oct.addActionListener(conv);
        conversionButtons.add(oct);
        final CCDisplay octDisplay = new CCDisplay();
        conversionButtons.add(octDisplay);
        convDisplays.put("HEX",octDisplay);
        final JButton bin = new JButton("BIN");
        bin.addActionListener(conv);
        conversionButtons.add(bin);
        final CCDisplay binDisplay = new CCDisplay();
        conversionButtons.add(binDisplay);
        convDisplays.put("HEX",binDisplay);
        
        this.add(conversionButtons,BorderLayout.CENTER);
    }
    private JPanel getBitwiseOperators() {
        final JPanel operators = new JPanel();
        
        for (final var entry : ProgrammerCalculatorModelFactory.create().getBinaryOpMap().entrySet()) {
            if (this.equalsAny(entry.getKey())) {
                final JButton btn = new JButton(entry.getKey());
                btn.addActionListener(this.opAl);
                btn.add(operators);
            }
        }
        
        
        
        return operators;
        
    }
    
    private boolean equalsAny(final String key) {
        switch (key) {
        case "ror":
            return true;
        case "rol":
            return true;
        case "shiftL":
            return true;
        case "shiftR":
            return true;
        case "nand":
            return true;
        default:
            return false;
        }
    }
    private void setNumpad() {
        
        final JPanel numpad = new JPanel();
        numpad.setLayout(new GridLayout(1, 3)); //TODO USELESS
        
        numpad.add(this.getHexadecimalLettersPanel());
        
        
        numpad.add(this.getMiddleNumpad());
        
        numpad.add(this.getRightNumpad());
        
        
        
        
        final JPanel numpadAndOperators = new JPanel();
        numpadAndOperators.setLayout(new BorderLayout());
        numpadAndOperators.add(this.getBitwiseOperators(),BorderLayout.NORTH);
        numpadAndOperators.add(numpad,BorderLayout.CENTER);
        
        this.add(numpadAndOperators,BorderLayout.SOUTH);
        
    }
    private JPanel getRightNumpad() {
        final JPanel operators = new JPanel();
        operators.setLayout(new GridLayout(6, 1));
        
        final JButton not = new JButton("not");
        not.addActionListener(opAl);
        final JButton nor = new JButton("nor");
        nor.addActionListener(opAl);
        
        operators.add(not);
        operators.add(nor);
        
        for ( final var entry : ProgrammerCalculatorModelFactory.getBasicOperators().entrySet()) {
                final JButton btn = new JButton(entry.getKey());
                btn.addActionListener(opAl);
                operators.add(btn);
        }
        
        
        return operators;
    }
    private JPanel getMiddleNumpad() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(this.numpad, BorderLayout.CENTER);
        final JPanel topMiddleNumpad = new JPanel();
        topMiddleNumpad.setLayout(new GridLayout(1, 3));
        final JButton and = new JButton("and");
        and.addActionListener(opAl);
        final JButton or = new JButton("or");
        or.addActionListener(opAl);
        final JButton xor = new JButton("xor");
        xor.addActionListener(opAl);
        topMiddleNumpad.add(and);
        topMiddleNumpad.add(or);
        topMiddleNumpad.add(xor);
        panel.add(topMiddleNumpad,BorderLayout.NORTH);
        return panel;
    }
    private JPanel getHexadecimalLettersPanel() {
        final ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                // TODO Auto-generated method stub
            }
        };
        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        final JButton a = new JButton("A");
        a.addActionListener(al);
        this.hexadecimalLetters.add(a);
        panel.add(a);
        final JButton b = new JButton("B");
        b.addActionListener(al);
        this.hexadecimalLetters.add(b);
        panel.add(b);
        final JButton c = new JButton("C");
        c.addActionListener(al);
        this.hexadecimalLetters.add(c);
        panel.add(c);
        final JButton d = new JButton("D");
        d.addActionListener(al);
        this.hexadecimalLetters.add(d);
        panel.add(d);
        final JButton e = new JButton("E");
        e.addActionListener(al);
        this.hexadecimalLetters.add(e);
        panel.add(e);
        final JButton f = new JButton("F");
        f.addActionListener(al);
        this.hexadecimalLetters.add(f);
        panel.add(f);
        
        return panel;
    }
}
