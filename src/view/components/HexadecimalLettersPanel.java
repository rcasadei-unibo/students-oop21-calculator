package view.components;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
//TODO javadoc.
/**
 * MISSING JAVADOC.
 *
 */
public class HexadecimalLettersPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -2613278018688810576L;

    private final List<JButton> hexadecimalLetters = new ArrayList<>();
    //TODO javadoc.
    /**
     * 
     * @param al
     */
    public HexadecimalLettersPanel(final ActionListener al) {
        final int rows = 6;
        final int cols = 1;
        this.setLayout(new GridLayout(rows, cols));
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
