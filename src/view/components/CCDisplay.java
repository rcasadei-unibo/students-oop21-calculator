package view.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * 
 */
public class CCDisplay extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -4685599997020932786L;
    private final JLabel mainBox;
    private final JLabel upperBox;
    /**
     * Display component.
     */
    public CCDisplay() {
        this.setLayout(new BorderLayout());

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double width = screenSize.getWidth() / 3 / 4;
        final double height = screenSize.getHeight() * 2 / 3 / 4;

        this.upperBox = new JLabel("", SwingConstants.RIGHT);
        this.mainBox = new JLabel("0", SwingConstants.RIGHT);
        this.mainBox.setPreferredSize(new Dimension((int) width, (int) height));
        this.mainBox.setFont(new Font(mainBox.getFont().getName(), Font.PLAIN, 32));
        this.mainBox.setBorder(new EmptyBorder(0, 10, 0, 10));
        this.mainBox.setAlignmentX(1.0f);
        this.add(mainBox, BorderLayout.CENTER);

        this.upperBox.setBorder(new EmptyBorder(10, 10, 0, 10));
        this.add(upperBox, BorderLayout.NORTH);

    }
    /**
     * 
     * @param s String to display
     */
    public void updateText(final String s) {
        this.mainBox.setText(s);
    }
    /**
     * 
     * @param s
     */
    public void updateUpperText(final String s) {
        this.upperBox.setText(s);
    }


}
