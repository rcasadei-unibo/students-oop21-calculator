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
import utils.ConversionAlgorithms;
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
    private HexadecimalLettersPanel hexaLetters;
    private ConversionPanel convPanel;
    private String numberBuffer = "";
    private final CCNumPad numpad;
    private ActionListener opAl;
    {
        final ActionListener btnAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String text = ((JButton) e.getSource()).getText();
                numberBuffer = numberBuffer.concat(text);
                controller.getManager().read(text);
                display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
                convPanel.updateConvDisplays(numberBuffer);
            }
        };
        final ActionListener calcAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.getManager().calculate();
                numberBuffer = controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b);
                display.updateText(numberBuffer);
            }
        };
        final ActionListener backspaceAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.getManager().deleteLast();
                numberBuffer = controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b);
                display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));

            }
        };
        this.numpad = new CCNumPad(btnAl, calcAl, backspaceAl);
        
        this.opAl = new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String text = ((JButton) e.getSource()).getText();
                
                final var temp = ProgrammerCalculatorModelFactory.create().getBinaryOpMap();
                if (temp.get(text) == null) { //unary operator
                    
                    display.updateText(((JButton) e.getSource()).getText() + "(" +controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b) + ")");
                    controller.getManager().read(((JButton) e.getSource()).getText());
                    numberBuffer = "";
                }
                else {
                    controller.getManager().read(((JButton) e.getSource()).getText());
                    display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
                    numberBuffer = "";
                }
                
            }
        };
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
                final JButton btn = (JButton) e.getSource();
                switch (btn.getText()) {
                case "HEX":
                    
                    //hexaLetters.enableAll();
                    //enableButtons(10);
                    break;
                case "DEC":
                    //hexaLetters.disableAll();
                    //enableButtons(10);
                    break;
                case "OCT":
                    //hexaLetters.disableAll();
                    //enableButtons(8);
                    break;
                case "BIN":
                    //hexaLetters.disableAll();
                    //enableButtons(2);
                    break;
                default:
                    break;
                }
            }
        };
        this.convPanel = new ConversionPanel(conv);
        this.add(this.convPanel, BorderLayout.CENTER);
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
    private void enableButtons(final int i) {
        final var numbers = this.getNumbers();
        numbers.entrySet().stream().filter((entry) -> 
            Integer.parseInt(entry.getKey()) < i).forEach((entry) -> entry.getValue().setEnabled(true));
        numbers.entrySet().stream().filter((entry) -> 
            Integer.parseInt(entry.getKey()) >= i).forEach((entry) -> entry.getValue().setEnabled(false));
    }
    private Map<String, JButton> getNumbers() {
        final Map<String, JButton> map = new HashMap<>();
        this.numpad.getButtons().entrySet().stream().filter((entry) -> { 
            try {
                Integer.parseInt(entry.getKey());
                return true;
            } catch (final NumberFormatException e) {
                return false;
            }
        }).forEach((entry) -> map.put(entry.getKey(), entry.getValue()));
        return map;
    }
    /**
     * 
     * @param key
     * @return true if key == 
     * ror , rol , shiftL, shiftR, nand
     */
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
        numpad.setLayout(new GridLayout(1, 3));
        // TODO add ActionListener for hexadecimal letters
        this.hexaLetters = new HexadecimalLettersPanel(null);
        this.hexaLetters.disableAll();
        numpad.add(this.hexaLetters);
        numpad.add(this.getMiddleNumpad());
        numpad.add(this.getRightNumpad());

        final JPanel numpadAndOperators = new JPanel();
        numpadAndOperators.setLayout(new BorderLayout());
        numpadAndOperators.add(this.getBitwiseOperators(), BorderLayout.NORTH);
        numpadAndOperators.add(numpad, BorderLayout.CENTER);

        this.add(numpadAndOperators, BorderLayout.SOUTH);

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

        for (final var entry : ProgrammerCalculatorModelFactory.getBasicOperators().entrySet()) {
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
        panel.add(topMiddleNumpad, BorderLayout.NORTH);
        return panel;
    }
    
    private class ConversionPanel extends JPanel{
        /**
         * 
         */
        private static final long serialVersionUID = -9080067811293897721L;
        
        private final Map<String, CCDisplay> map = new HashMap<>();
        
        
        
        private final CCDisplay decDisplay;
        ConversionPanel(final ActionListener conv) {
          //TODO fix
            this.setLayout(new GridLayout(4, 2));
            
            
            final JButton hex = new JButton("HEX");
            hex.addActionListener(conv);
            this.add(hex);
            final CCDisplay hexDisplay = new CCDisplay();
            this.add(hexDisplay);
            this.map.put(hex.getText(), hexDisplay);
            
            final JButton dec = new JButton("DEC");
            dec.addActionListener(conv);
            this.add(dec);
            this.decDisplay = new CCDisplay();
            this.add(decDisplay);
            this.map.put(dec.getText(), decDisplay);
            //not needed since it's linked to the main display
            
            final JButton oct = new JButton("OCT");
            oct.addActionListener(conv);
            this.add(oct);
            final CCDisplay octDisplay = new CCDisplay();
            this.add(octDisplay);
            this.map.put(oct.getText(), octDisplay);
            
            final JButton bin = new JButton("BIN");
            bin.addActionListener(conv);
            this.add(bin);
            final CCDisplay binDisplay = new CCDisplay();
            this.add(binDisplay);
            this.map.put(bin.getText(), binDisplay);
        }
        /**
         * @param value for base2 base8 and base16.
         */
        void updateConvDisplays(final String input) {
            this.map.entrySet().stream().forEach((entry) -> entry.getValue().updateText(textToBase(entry.getKey())));
        }
        /**
         * 
         * @param text string to show for base10 display.
         */
        private String textToBase(final String text) {
            switch (text) {
            case "HEX":
                return ConversionAlgorithms.conversionToStringBase(16, Integer.parseInt(numberBuffer));//TODO add  ConversionAlgorithms.conversionToStringBase(16, controller.getManager().getCurrentState().lastInput());
            case "DEC":
                return numberBuffer; //TODO controller.getManager().getCurrentState().lastInput();
            case "OCT":
                return ConversionAlgorithms.conversionToStringBase(8, 69);
            case "BIN":
                return ConversionAlgorithms.conversionToStringBase(2, 69);
            default:
                 return null;
            }
        }
    }
    private class HexadecimalLettersPanel extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = -2613278018688810576L;

        private final List<JButton> hexadecimalLetters = new ArrayList<>();
        /**
         * 
         * @param al
         */
        HexadecimalLettersPanel(final ActionListener al) {
            this.setLayout(new GridLayout(6, 1));
            final JButton a = new JButton("A");
            a.addActionListener(al);
            this.hexadecimalLetters.add(a);
            this.add(a);
            final JButton b = new JButton("B");
            b.addActionListener(al);
            this.hexadecimalLetters.add(b);
            this.add(b);
            final JButton c = new JButton("C");
            c.addActionListener(al);
            this.hexadecimalLetters.add(c);
            this.add(c);
            final JButton d = new JButton("D");
            d.addActionListener(al);
            this.hexadecimalLetters.add(d);
            this.add(d);
            final JButton e = new JButton("E");
            e.addActionListener(al);
            this.hexadecimalLetters.add(e);
            this.add(e);
            final JButton f = new JButton("F");
            f.addActionListener(al);
            this.hexadecimalLetters.add(f);
            this.add(f);
        }
        /**
         * This method disables all hexadecimal letter buttons.
         */
        void disableAll() {
            this.hexadecimalLetters.forEach((btn) -> btn.setEnabled(false));
        }
        /**
         * This method enables all hexadecimal letter buttons.
         */
        void enableAll() {
            this.hexadecimalLetters.forEach((btn) -> btn.setEnabled(true));
        }
    }

}
