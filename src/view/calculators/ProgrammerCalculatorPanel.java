package view.calculators;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import controller.calculators.logics.ProgrammerLogicsImpl;
import utils.CreateButton;
import view.components.CCDisplay;
import view.components.CCNumPad;
import view.components.ConversionPanel;
import view.components.HexadecimalLettersPanel;
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
    private final transient ProgrammerLogicsImpl formatter = new ProgrammerLogicsImpl(this.display);
    {
        this.opAl = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String text = ((JButton) e.getSource()).getText();
                formatter.read(text);
                updateDisplays();
            }
        };
        this.numpad = new CCNumPad(e -> {
            //button Action Listener
            final String text = ((JButton) e.getSource()).getText();
            formatter.read(text);
            updateDisplays();
        }, e -> {
            //calculate
            try {
                formatter.updateDisplayUpperText();
                final String history = formatter.getBuffer();
                formatter.calculate();
                formatter.addResult(history);
            } catch (NumberFormatException exception) {
                display.updateText("Syntax error");
                formatter.updateDisplayUpperText();
                formatter.deleteLast();
            }
            updateDisplays();
        }, e -> {
            //delete Last
            formatter.deleteLast();
            updateDisplays();
        });
    }
    /**
     * This is ProgrammerCalculatorPanel which holds the following operators:
     * (Bitwise)
     * -Not, Nor, Nand, Or, Xor, And, RoR, RoL, ShiftL, ShiftR.
     * (Conversions)
     * -Hexadecimal, Octal, Binary.
    */
    public ProgrammerCalculatorPanel() {
        final Dimension size = this.numpad.getPreferredSize();
        final double xProportion = 1.75, yProportion = 2;
        this.numpad.setPreferredSize(new Dimension((int) (size.getWidth() / xProportion), (int) (size.getHeight() / yProportion)));
        //Disabled until IEEE745 has been implemented
        this.numpad.getButtons().entrySet().forEach((entry) -> {
            if (".".equals(entry.getKey())) {
                entry.getValue().setEnabled(false);
            }
        });
        this.setPanels();
    }
    private void setPanels() {
        this.setLayout(new BorderLayout());
        this.add(this.display, BorderLayout.NORTH);
        this.setConversionPanel();
        this.setNumpad();
    }
    private void setConversionPanel() {
        this.convPanel = new ConversionPanel(e -> {
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
            formatter.updateDisplay();
            convPanel.updateConvDisplays(0);
        });
        final var numpadDim = this.numpad.getPreferredSize();
        this.convPanel.setPreferredSize(new Dimension(numpadDim));
        this.add(this.convPanel, BorderLayout.CENTER);
    }
    /**
     * This method enables and disables the numpad buttons' below.
     * @param i .
     */
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
        this.hexaLetters = new HexadecimalLettersPanel(e -> {
            final String text = ((JButton) e.getSource()).getText();
            formatter.read(text);
            updateDisplays();
        });
        //start the programmer with 10 as default conversion
        formatter.reset(10);
        hexaLetters.disableAll();
        convPanel.changeToActive("DEC");
        numpad.add(this.hexaLetters);
        numpad.add(this.numpad);
        numpad.add(this.getRightNumpad());
        this.add(numpad, BorderLayout.SOUTH);
    }
    private JPanel getRightNumpad() {
        final int rows = 7;
        final int cols = 2;
        final JPanel operators = new JPanel();
        operators.setLayout(new GridLayout(rows, cols));
        final List<String> topOperators = List.of("roR", "roL", "shiftR", "shiftL", "nand", "nor");
        final List<String> middleOperators = List.of("not", "xor", "and", "or");
        final List<String> rightOperators = List.of("+", "-", "×", "÷");
        topOperators.forEach((str) -> {
            operators.add(this.createButton(str));
        });
        middleOperators.forEach((str) -> {
            operators.add(this.createButton(str));
        });
        rightOperators.forEach((str) -> {
            operators.add(this.createButton(str));
        });
        return operators;
    }
    private JButton createButton(final String btnName) {
        final JButton btn = CreateButton.createOpButton(btnName);
        btn.addActionListener(opAl);
        return btn;
    }
    private void updateDisplays() {
        formatter.updateDisplay();
        convPanel.updateConvDisplays(formatter.getLastValue());
    }
}
