package test;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import view.components.Graph;

/**
 * 
 * This class is for testing the calculators panels.
 *
 */
public class PanelTest extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = -3467875712655003970L;
    /**
     * 
     */
    public PanelTest() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screen.getWidth() / 1.75), (int) (screen.getHeight() / 1.75));
        this.setLocationByPlatform(true);
        this.add(new Graph());
        this.setVisible(true);
    }
    /**
     * @param args
     */
    public static void main(final String[] args) {
        new PanelTest();
    }

}
