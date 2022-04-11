package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.calculators.CalculatorController;
import model.calculators.ProgrammerCalculatorModelFactory;
import utils.InputFormatter;
import view.components.CCDisplay;
import view.components.CCNumPad;
import view.components.ConversionPanel;
import view.components.HexadecimalLettersPanel;

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
    private final CCDisplay display = new CCDisplay();
    private HexadecimalLettersPanel hexaLetters;
    private ConversionPanel convPanel;
    private final CCNumPad numpad;
    private transient ActionListener opAl;
    private final transient InputFormatter formatter;
    {
        final ActionListener btnAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String text = ((JButton) e.getSource()).getText();
                formatter.read(text);
                display.updateText(formatter.getOutput());
                convPanel.updateConvDisplays(formatter.getLastValue());
            }
        };
        final ActionListener calcAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                formatter.calculate();
                display.updateText(formatter.getOutput());
                convPanel.updateConvDisplays(formatter.getLastValue());
            }
        };
        final ActionListener backspaceAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                formatter.deleteLast();
                display.updateText(formatter.getOutput());
                convPanel.updateConvDisplays(formatter.getLastValue());
            }
        };
        this.numpad = new CCNumPad(btnAl, calcAl, backspaceAl);
        this.numpad.getButtons().entrySet().forEach((entry) -> {
            if (entry.getKey().equals(".")) {
                entry.getValue().setEnabled(false);
            }
        });

        this.opAl = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                final String text = ((JButton) e.getSource()).getText();
                formatter.read(text);

                display.updateText(formatter.getOutput());
            }
        };
    }

    /**
     * @param controller
     */
    public ProgrammerCalculatorPanel(final CalculatorController controller) {
        this.formatter = new InputFormatter(controller);
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
                    formatter.reset(16);
                    hexaLetters.enableAll();
                    enableButtons(10);
                    formatter.reset(16);
                    break;
                case "DEC":
                    formatter.reset(10);
                    hexaLetters.disableAll();
                    enableButtons(10);
                    formatter.reset(10);
                    break;
                case "OCT":
                    formatter.reset(8);
                    hexaLetters.disableAll();
                    enableButtons(8);
                    formatter.reset(8);
                    break;
                case "BIN":
                    formatter.reset(2);
                    hexaLetters.disableAll();
                    enableButtons(2);
                    formatter.reset(2);
                    break;
                default:
                    break;
                }
                display.updateText("0");
                convPanel.updateConvDisplays(0);
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
        numbers.entrySet().stream().filter((entry) -> Integer.parseInt(entry.getKey()) < i)
                .forEach((entry) -> entry.getValue().setEnabled(true));
        numbers.entrySet().stream().filter((entry) -> Integer.parseInt(entry.getKey()) >= i)
                .forEach((entry) -> entry.getValue().setEnabled(false));
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
     * @return true if key == ror , rol , shiftL, shiftR, nand
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
        final ActionListener letterActionListener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String text = ((JButton) e.getSource()).getText();
                formatter.read(text);
                display.updateText(formatter.getOutput());
                convPanel.updateConvDisplays(formatter.getLastValue());
            }
        };

        this.hexaLetters = new HexadecimalLettersPanel(letterActionListener);
        //this sets the standard initial base to 10, disabling hexadecimal Letters
        formatter.reset(10);
        hexaLetters.disableAll();
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
}
