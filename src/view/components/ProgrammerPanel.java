package view.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import utils.ConversionAlgorithms;
//TODO missing javadoc
/**
 * 
 *
 */
public class ProgrammerPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 2469993034979788995L;
    private final List<String> topOperators = List.of("roR", "roL", "shiftR", "shiftL", "nand");
    private final List<String> middleOperators = List.of("and", "or", "xor");
    private final List<String> rightOperators = List.of("not", "nor", "+", "-", "×", "÷");
    
    
    
    /**
     * 
     * @param opAl
     */
    public ProgrammerPanel(final ActionListener opAl) {
        
        this.setLayout(new BorderLayout());
        this.add(new CCDisplay(), BorderLayout.NORTH);
        
        this.add(new ConversionPanel2(null), BorderLayout.CENTER);
        
        final JPanel numpad = new JPanel();
        numpad.setLayout(new GridLayout(1, 3));
        
        numpad.add(new HexadecimalLettersPanel(opAl));
        numpad.add(this.getMiddleNumpad());
        numpad.add(this.getRightNumpad());

        final JPanel numpadAndOperators = new JPanel();
        numpadAndOperators.setLayout(new BorderLayout());
        //numpadAndOperators.add(this.getBitwiseOperators(), BorderLayout.NORTH);
        numpadAndOperators.add(numpad, BorderLayout.CENTER);
        
        final JPanel oper = new JPanel();
        oper.setLayout(new GridLayout(1, 5));
        topOperators.forEach((str) -> {
            oper.add(new JButton(str));
        });
        
        final JPanel mid = new JPanel();
        mid.setLayout(new BorderLayout());
        
        mid.add(oper, BorderLayout.NORTH);
        mid.add(numpadAndOperators, BorderLayout.CENTER);
        
        this.add(mid, BorderLayout.SOUTH);
        
        
        
    }
    private JPanel getRightNumpad() {
        final int rows = 6;
        final int cols = 1;
        final JPanel operators = new JPanel();
        operators.setLayout(new GridLayout(rows, cols));
        
        this.rightOperators.forEach((op) -> {
            final JButton btn = new JButton(op);
            btn.addActionListener(null); 
            operators.add(btn);
        });

        return operators;
    }

    private JPanel getMiddleNumpad() {
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new CCNumPad(null, null, null), BorderLayout.CENTER);
        final JPanel topMiddleNumpad = new JPanel();
        topMiddleNumpad.setLayout(new GridLayout(1, 3));
        
        this.middleOperators.forEach((str) -> {
            final var btn = new JButton(str);
            btn.addActionListener(null);
            topMiddleNumpad.add(btn);
        });
        
        panel.add(topMiddleNumpad, BorderLayout.NORTH);
        return panel;
    }
    
    
    private class ConversionPanel2 extends JPanel {
        /**
         * 
         */
        private static final long serialVersionUID = -9080067811293897721L;

        private final Map<String, CCDisplay> map = new HashMap<>();
        //TODO add javadoc.
        /**
         * MISSING JAVADOC.
         * @param conv
         */
        ConversionPanel2(final ActionListener conv) {
            this.setLayout(new BorderLayout());
            
            this.add(this.setTop(), BorderLayout.CENTER);
            
            
        }
        
        

        private JPanel setTop() {
            final JPanel x = new JPanel();
            x.setLayout(new GridLayout(4, 2));
            final JButton hex = new JButton("HEX");
            hex.addActionListener(null);
            x.add(hex);
            final CCDisplay hexDisplay = new CCDisplay();
            x.add(hexDisplay);
            this.map.put(hex.getText(), hexDisplay);

            final JButton dec = new JButton("DEC");
            dec.addActionListener(null);
            x.add(dec);
            final CCDisplay decDisplay = new CCDisplay();
            x.add(decDisplay);
            this.map.put(dec.getText(), decDisplay);

            final JButton oct = new JButton("OCT");
            oct.addActionListener(null);
            x.add(oct);
            final CCDisplay octDisplay = new CCDisplay();
            x.add(octDisplay);
            this.map.put(oct.getText(), octDisplay);

            final JButton bin = new JButton("BIN");
            bin.addActionListener(null);
            x.add(bin);
            final CCDisplay binDisplay = new CCDisplay();
            x.add(binDisplay);
            this.map.put(bin.getText(), binDisplay);
            
            return x;
        }



        /**
         * @param l the number that the displays will show
         */
        public void updateConvDisplays(final long l) {
                this.map.entrySet().stream().forEach((entry) -> entry.getValue().updateText(textToBase(entry.getKey(), l)));
        }

        private String textToBase(final String text, final long l) {
            switch (text) {
            case "HEX":
                return ConversionAlgorithms.conversionToStringBase(16, l);
            case "DEC":
                return String.valueOf(l);
            case "OCT":
                return ConversionAlgorithms.conversionToStringBase(8, l);
            case "BIN":
                return ConversionAlgorithms.conversionToStringBase(2, l);
            default:
                return null;
            }
        }
    }
    
    
}
