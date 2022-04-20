package view.calculators;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import utils.CCColors;
import utils.CalcException;
import view.components.CCDisplay;
import view.components.CCNumPad;
import view.components.ConversionPanel;
import view.components.HexadecimalLettersPanel;
import view.logics.ProgrammerInputFormatterImpl;
/**
 * This is ProgrammerCalculatorPanel which holds the following operators:
 * (Bitwise)
 * -Not, Nor, Nand, Or, Xor, And, RoR, RoL, ShiftL, ShiftR.
 * (Conversions)
 * -Hexadecimal, Octal, Binary.
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
    private final transient ProgrammerInputFormatterImpl formatter;
    private final List<String> topOperators = List.of("roR", "roL", "shiftR", "shiftL", "nand", "nor");
    private final List<String> middleOperators = List.of("not", "xor", "and", "or");
    private final List<String> rightOperators = List.of("+", "-", "×", "÷");
    {
        final ActionListener btnAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String text = ((JButton) e.getSource()).getText();
                formatter.read(text);
                updateDisplays();
            }
        };
        final ActionListener calcAl = new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                try {
                    display.updateUpperText(formatter.getOutput() + " =");
                    formatter.calculate();
                    formatter.updateHistory();
                } catch (Exception exception) {
                    System.out.println("excpetion");
                    display.updateText("Syntax error");
                    display.updateUpperText(formatter.getOutput() + " =");
                    formatter.deleteLast();
                }
                updateDisplays();
            }
        };
        final ActionListener backspaceAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                formatter.deleteLast();
                updateDisplays();
            }
        };
        this.numpad = new CCNumPad(btnAl, calcAl, backspaceAl);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.numpad.setPreferredSize(new Dimension((int) screenSize.getWidth() / 6, (int) screenSize.getHeight() / 4));
        this.numpad.getButtons().entrySet().forEach((entry) -> {
            if (".".equals(entry.getKey())) {
                entry.getValue().setEnabled(false);
            }
        });

        this.opAl = new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                final String text = ((JButton) e.getSource()).getText();
                formatter.read(text);
                updateDisplays();
            }
        };
    }
    /**
     * This is ProgrammerCalculatorPanel which holds the following operators:
     * (Bitwise)
     * -Not, Nor, Nand, Or, Xor, And, RoR, RoL, ShiftL, ShiftR.
     * (Conversions)
     * -Hexadecimal, Octal, Binary.
    */
    public ProgrammerCalculatorPanel() {
        this.formatter = new ProgrammerInputFormatterImpl();
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
                    break;
                case "DEC":
                    formatter.reset(10);
                    hexaLetters.disableAll();
                    enableButtons(10);
                    break;
                case "OCT":
                    formatter.reset(8);
                    hexaLetters.disableAll();
                    enableButtons(8);
                    break;
                case "BIN":
                    formatter.reset(2);
                    hexaLetters.disableAll();
                    enableButtons(2);
                    break;
                default:
                    break;
                }
                convPanel.changeToActive(btn.getText());
                display.updateText("0");
                convPanel.updateConvDisplays(0);
            }
        };
        this.convPanel = new ConversionPanel(conv);
        this.convPanel.setPreferredSize(new Dimension(100, 150));
        this.add(this.convPanel, BorderLayout.CENTER);
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
    private void setNumpad() {
        final JPanel numpad = new JPanel();
        numpad.setLayout(new GridLayout(1, 3));
        final ActionListener letterActionListener = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String text = ((JButton) e.getSource()).getText();
                formatter.read(text);
                updateDisplays();
            }
        };
        this.hexaLetters = new HexadecimalLettersPanel(letterActionListener);
        //start the programmer with 10 as default conversion
        formatter.reset(10);
        hexaLetters.disableAll();
        convPanel.changeToActive("DEC");
        numpad.add(this.hexaLetters);
        numpad.add(this.numpad);
        numpad.add(this.getRightNumpad());
        final JPanel numpadAndOperators = new JPanel();
        numpadAndOperators.setLayout(new BorderLayout());
        numpadAndOperators.add(numpad, BorderLayout.CENTER);
        final JPanel oper = new JPanel();
        oper.setLayout(new GridLayout(1, 5));
        final JPanel mid = new JPanel();
        mid.setLayout(new BorderLayout());
        mid.add(oper, BorderLayout.NORTH);
        mid.add(numpadAndOperators, BorderLayout.CENTER);
        this.add(mid, BorderLayout.SOUTH);
    }
    private JPanel getRightNumpad() {
        final int rows = 7;
        final int cols = 2;
        final JPanel operators = new JPanel();
        operators.setLayout(new GridLayout(rows, cols));
        this.topOperators.forEach((str) -> {
            final JButton btn = new JButton(str);
            btn.addActionListener(opAl);
            btn.setBackground(CCColors.OPERATION_BUTTON);
            operators.add(btn);
        });
        this.middleOperators.forEach((str) -> {
            final JButton btn = new JButton(str);
            btn.addActionListener(opAl);
            btn.setBackground(CCColors.OPERATION_BUTTON);
            operators.add(btn);
        });
        this.rightOperators.forEach((op) -> {
            final JButton btn = new JButton(op);
            btn.addActionListener(opAl);
            btn.setBackground(CCColors.OPERATION_BUTTON);
            operators.add(btn);
        });
        return operators;
    }
    private void updateDisplays() {
        display.updateText(formatter.getOutput());
        convPanel.updateConvDisplays(formatter.getLastValue());
    }
}
