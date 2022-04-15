package view.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import utils.CCColors;

/**
 * Display component to show the user what has been given in input, the result of a calculation and eventual errors.
 */
public class CCDisplay extends JPanel {

    private static final long serialVersionUID = -4685599997020932786L;
    private final JLabel mainBox;
    private final JLabel upperBox;

    /**
     * Constructs a new display component.
     */
    public CCDisplay() {
        this.setLayout(new BorderLayout());

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double width = screenSize.getWidth() / 3;
        final double height = screenSize.getHeight() / (8);

        this.upperBox = new JLabel("", SwingConstants.RIGHT);
        this.mainBox = new JLabel("0", SwingConstants.RIGHT);
        this.mainBox.setPreferredSize(new Dimension((int) width, (int) height));
        this.mainBox.setFont(new Font(mainBox.getFont().getName(), Font.PLAIN, 32));
        this.mainBox.setBorder(new EmptyBorder(0, 10, 0, 10));
        this.mainBox.setAlignmentX(1.0f);
        this.add(mainBox, BorderLayout.CENTER);

        this.upperBox.setBorder(new EmptyBorder(10, 10, 0, 10));
        this.add(upperBox, BorderLayout.NORTH);

        this.setBackground(CCColors.DISPLAY);

    }

    /**
     * Shows a given string on the main JLabel.
     * @param s String to display.
     */
    public void updateText(final String s) {
        this.mainBox.setText(s);
    }
    /**
     * Shows a given string on the upper JLabel.
     * @param s String to display.
     */
    public void updateUpperText(final String s) {
        this.upperBox.setText(s);
    }


}
