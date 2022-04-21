package view.components;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import utils.CCColors;
/**
 * This panel contains the Hexadeciamal letters going from A to F.
 */
public class HexadecimalLettersPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -2613278018688810576L;

    private final List<JButton> hexadecimalLetters = new ArrayList<>();
    /**
     * @param al This links each letter to the ActionListener
     */
    public HexadecimalLettersPanel(final ActionListener al) {
        final int rows = 6;
        final int cols = 1;
        this.setLayout(new GridLayout(rows, cols));
        this.createButton("A", al);
        this.createButton("B", al);
        this.createButton("C", al);
        this.createButton("D", al);
        this.createButton("E", al);
        this.createButton("F", al);
    }
    private void createButton(final String text, final ActionListener al) {
        final JButton btn = new JButton(text);
        btn.addActionListener(al);
        btn.setBackground(CCColors.NUMBER_BUTTON);
        this.hexadecimalLetters.add(btn);
        this.add(btn);
    }
    /**
     * This method disables all hexadecimal letter buttons.
     */
    public void disableAll() {
        this.hexadecimalLetters.forEach((btn) -> btn.setEnabled(false));
    }
    /**
     * This method enables all hexadecimal letter buttons.
     */
    public void enableAll() {
        this.hexadecimalLetters.forEach((btn) -> btn.setEnabled(true));
    }
}
