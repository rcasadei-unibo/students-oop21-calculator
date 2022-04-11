package view.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * 
 * 
 *
 */
public class Graph extends JPanel implements MouseListener {
    /**
     * 
     */
    private static final long serialVersionUID = -6534831232343094643L;
    private static final double PRECISION = 0.1;
    private static final double SCALE = 20;
    private static final int COUNTER = 1000;
    /**
     * 
     */
    public Graph() {
        addMouseListener(this);
    }
    /**
     * @param g
     */
    public void paintComponent(final Graphics g) {
        final int w = this.getWidth();
        final int h = this.getHeight();
        super.paintComponent(g);
        drawAxes(g, w, h);
        drawFunction(g, w, h);
    }

    private void drawAxes(final Graphics g, final int w, final int h) {
        final Graphics2D axes = (Graphics2D) g;
        axes.setStroke(new BasicStroke(1));
        axes.setColor(Color.BLACK);
        axes.drawLine(0, h / 2, w, h / 2);
        axes.drawLine(w / 2, 0, w / 2, h);
        axes.drawString("o", w / 2 - 10, h / 2 + 10);
        axes.drawString("x", w - 10, h / 2 + 10);
        axes.drawString("y", w / 2 - 10, 10);
    }

    private void drawFunction(final Graphics g, final int w, final int h) {
        final Graphics2D fun = (Graphics2D) g;
        fun.setStroke(new BasicStroke(1));
        fun.setColor(Color.RED);
        final Polygon p = new Polygon();
        double x = -COUNTER;
        while (x <= COUNTER) {
           p.addPoint((int) (w / 2 + x * SCALE), h / 2 - (int) Math.round(Math.abs(x) * SCALE));
           x += PRECISION;
        }
        fun.drawPolyline(p.xpoints, p.ypoints, p.npoints);
    }
    @Override
    public void mouseClicked(final MouseEvent e) {
        this.setToolTipText("x: " + (e.getPoint().x - this.getWidth() / 2) / SCALE + "y: " + (this.getHeight() / 2 - e.getPoint().y) / SCALE);
    }
    @Override
    public void mousePressed(final MouseEvent e) {
    }
    @Override
    public void mouseReleased(final MouseEvent e) {
    }
    @Override
    public void mouseEntered(final MouseEvent e) {
    }
    @Override
    public void mouseExited(final MouseEvent e) {
    }
}
