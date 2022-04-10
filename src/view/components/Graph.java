package view.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
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
    private static final long serialVersionUID = -6534831232343094643L;

    /**
     * @param g
     */
    public void paintComponent(final Graphics g) {
        final int width = this.getWidth();
        final int height = this.getHeight();
        super.paintComponent(g);

        final Graphics2D axes = (Graphics2D) g;
        axes.setStroke(new BasicStroke(1));
        axes.setColor(Color.BLACK);
        axes.drawLine(0, height / 2, width, height / 2);
        axes.drawLine(width / 2, 0, width / 2, height);
        axes.drawString("o", width / 2 - 10, height / 2 + 10);
        axes.drawString("x", width - 10, height / 2 + 10);
        axes.drawString("y", width / 2 - 10, 10);
        drawFunction(g, width, height);
    }

    private void drawFunction(final Graphics g, final int width, final int height) {
        final Graphics2D fun = (Graphics2D) g;
        fun.setStroke(new BasicStroke(1));
        fun.setColor(Color.RED);
        final Polygon p = new Polygon();
        final int scale = 10;
        for (int x = 0; x <= 1000; x++) {
           p.addPoint(width / 2 + x, height / 2 - (int) Math.round(scale * Math.sin(x)));
        }
        fun.drawPolyline(p.xpoints, p.ypoints, p.npoints);
    }
}
