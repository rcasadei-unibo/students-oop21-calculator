package view.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
/**
 * 
 * 
 *
 */
public class Graph extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -3653271200771693075L;
    /**
     * @param g
     */
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.LIGHT_GRAY);

        g.setColor(Color.BLACK);
        g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
        g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
        g.drawString("o", this.getWidth() / 2 - 10, this.getHeight() / 2 + 10);
        g.drawString("x", this.getWidth() - 10, this.getHeight() / 2 + 10);
        g.drawString("y", this.getWidth() / 2 - 10, 10);
        this.paintFunction(g);
    }
    /**
     * 
     * @param f
     * 
     */
    public void paintFunction(final Graphics f) {
        f.setColor(Color.red);
        f.drawOval(this.getWidth() / 2, this.getHeight() / 2, 0, 0);
    }

}
