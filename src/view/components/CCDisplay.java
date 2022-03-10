package view.components;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 */
public class CCDisplay extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -4685599997020932786L;
    private final JLabel textbox;
    /**
     * Display component.
     */
    public CCDisplay() {
        textbox = new JLabel("0");
        textbox.setPreferredSize(new Dimension(200, 100));
        textbox.setFont(new Font(textbox.getFont().getName(), Font.PLAIN, 32));
        textbox.setBorder(new EmptyBorder(0, 10, 0, 0));
        this.add(textbox);
    }
    /**
     * 
     * @param s String to display
     */
    public void updateText(final String s) {
        this.textbox.setText(s);
    }


}
