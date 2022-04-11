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
public class FunctionGrapher extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -6534831232343094643L;
    private static final double PRECISION = 0.1;
    private static double scale = 10;
    private static final int LIMIT = 1000;
    /**
     * 
     */
    public FunctionGrapher() {
        this.addMouseWheelListener(m -> {
            if (m.getWheelRotation() > 0 && FunctionGrapher.scale > 1) {
                FunctionGrapher.scale--;
            } else {
                 FunctionGrapher.scale++;
            }
            this.repaint();
        });
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
        double x = Math.negateExact(LIMIT);
        while (x <= LIMIT) {
           p.addPoint((int) (w / 2 + x * scale), h / 2 - (int) Math.round(Math.tan(x) * scale));
           x += PRECISION;
        }
        fun.drawPolyline(p.xpoints, p.ypoints, p.npoints);
    }
}
