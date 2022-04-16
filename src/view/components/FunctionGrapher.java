package view.components;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;

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
    /**
     * 
     */
    public static final double PRECISION = 0.01;
    /**
     * 
     */
    private static double scale = 16;
    /**
     * 
     */
    public static final int LIMIT = 70;
    private boolean isOn1 = false;
    private boolean isOn2 = false;
    private List<Double> firstResults;
    private List<Double> secondResults;
    /**
     *
     */
    public FunctionGrapher() {
        this.addMouseWheelListener(m -> {
            if (m.getWheelRotation() > 0 && FunctionGrapher.scale > 16) {
                FunctionGrapher.scale--;
            } else if (m.getWheelRotation() < 0) {
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
        drawGrid(g, w, h);
        drawAxes(g, w, h);
        drawLines(g, w, h);
        drawFunction(g, w, h);
        drawFunction2(g, w, h);
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
    /**
     * 
     * @param g
     * @param w
     * @param h
     */
    private void drawFunction(final Graphics g, final int w, final int h) {
        if (this.isOn1) {
            final Graphics2D fun1 = (Graphics2D) g;
            fun1.setStroke(new BasicStroke(1));
            fun1.setColor(Color.RED);
            final Polygon p1 = new Polygon();
            double x = -LIMIT;
            for (final Double y : firstResults) {
                p1.addPoint((int) (w / 2 + x * FunctionGrapher.scale * 2), (int) (h / 2 - y.doubleValue()  * FunctionGrapher.scale * 2));
                x += PRECISION;
            }
            fun1.drawPolyline(p1.xpoints, p1.ypoints, p1.npoints);
         }
    }
    /**
     * 
     * @param g
     * @param w
     * @param h
     */
    private void drawFunction2(final Graphics g, final int w, final int h) {
        if (this.isOn2) {
            final Graphics2D fun2 = (Graphics2D) g;
            fun2.setStroke(new BasicStroke(1));
            fun2.setColor(Color.BLUE);
            final Polygon p2 = new Polygon();
            double x = -LIMIT;
            for (final Double y : secondResults) {
                p2.addPoint((int) (w / 2 + x * FunctionGrapher.scale * 2), (int) (h / 2 - y.doubleValue()  * FunctionGrapher.scale * 2));
                x += PRECISION;
            }
            fun2.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);
         }
    }

    private void drawLines(final Graphics g, final int w, final int h) {
        final Graphics2D lines = (Graphics2D) g;
        lines.setStroke(new BasicStroke(1));
        lines.setColor(Color.BLACK);
        for (int count = 0; count < LIMIT; count++) {
            lines.drawLine((int) (w / 2 + count * FunctionGrapher.scale * 2), (int) (h / 2 + 3 - 10 / FunctionGrapher.scale), (int) (w / 2 + count * FunctionGrapher.scale * 2), (int) (h / 2 - 3 + 10 / FunctionGrapher.scale));
            lines.drawLine((int) (w / 2 - count * FunctionGrapher.scale * 2), (int) (h / 2 + 3 - 10 / FunctionGrapher.scale), (int) (w / 2 - count * FunctionGrapher.scale * 2), (int) (h / 2 - 3 + 10 / FunctionGrapher.scale));
        }
        for (int count = 0; count < LIMIT; count++) {
            lines.drawLine((int) (w / 2 + 3 - 10 / FunctionGrapher.scale), (int) (h / 2 + count * FunctionGrapher.scale * 2), (int) (w / 2 - 3 + 10 / FunctionGrapher.scale), (int) (h / 2 + count * FunctionGrapher.scale * 2));
            lines.drawLine((int) (w / 2 + 3 - 10 / FunctionGrapher.scale), (int) (h / 2 - count * FunctionGrapher.scale * 2), (int) (w / 2 - 3 + 10 / FunctionGrapher.scale), (int) (h / 2 - count * FunctionGrapher.scale * 2));
        }
    }

    private void drawGrid(final Graphics g, final int w, final int h) {
        final Graphics2D grid = (Graphics2D) g;
        grid.setStroke(new BasicStroke(1));
        grid.setColor(Color.LIGHT_GRAY);
        for (int count = 0; count < LIMIT; count++) {
            grid.drawLine((int) (w / 2 + count * FunctionGrapher.scale), 0, (int) (w / 2 + count * FunctionGrapher.scale), h);
            grid.drawLine((int) (w / 2 - count * FunctionGrapher.scale), 0, (int) (w / 2 - count * FunctionGrapher.scale), h);
        }
        for (int count = 0; count < LIMIT; count++) {
            grid.drawLine(0, (int) (h / 2 + count * FunctionGrapher.scale), w, (int) (h / 2 + count * FunctionGrapher.scale));
            grid.drawLine(0, (int) (h / 2 - count * FunctionGrapher.scale), w, (int) (h / 2 - count * FunctionGrapher.scale));
        }
    }
    /**
     *@param results1
     */
    public void paintFunction1(final List<Double> results1) {
        this.isOn1 = true;
        this.firstResults = results1;
        System.out.println(firstResults);
        this.repaint();
    }
    /**
     * 
     * @param results2
     */
    public void paintFunction2(final List<Double> results2) {
        this.isOn2 = true;
        this.secondResults = results2;
        System.out.println(secondResults);
        this.repaint();
    }
    /**
     * 
     */
    public void deleteFunction1() {
        this.firstResults.clear();
        this.isOn1 = false;
        this.repaint();
    }
    /**
     * 
     */
    public void deleteFunction2() {
        this.secondResults.clear();
        this.isOn2 = false;
        this.repaint();
    }
}

