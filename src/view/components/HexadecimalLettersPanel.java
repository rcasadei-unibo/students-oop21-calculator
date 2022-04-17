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
        final JButton a = new JButton("A");
        a.addActionListener(al);
        a.setBackground(CCColors.NUMBER_BUTTON);
        this.hexadecimalLetters.add(a);
        this.add(a);
        final JButton b = new JButton("B");
        b.addActionListener(al);
        b.setBackground(CCColors.NUMBER_BUTTON);
        this.hexadecimalLetters.add(b);
        this.add(b);
        final JButton c = new JButton("C");
        c.addActionListener(al);
        c.setBackground(CCColors.NUMBER_BUTTON);
        this.hexadecimalLetters.add(c);
        this.add(c);
        final JButton d = new JButton("D");
        d.addActionListener(al);
        d.setBackground(CCColors.NUMBER_BUTTON);
        this.hexadecimalLetters.add(d);
        this.add(d);
        final JButton e = new JButton("E");
        e.addActionListener(al);
        e.setBackground(CCColors.NUMBER_BUTTON);
        this.hexadecimalLetters.add(e);
        this.add(e);
        final JButton f = new JButton("F");
        f.addActionListener(al);
        f.setBackground(CCColors.NUMBER_BUTTON);
        this.hexadecimalLetters.add(f);
        this.add(f);
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
