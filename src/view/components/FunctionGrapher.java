package view.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import utils.FunctionCalculatorImpl;
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
    private static double scale = 16;
    /**
     * 
     */
    private List<Double> results1;
    private List<Double> results2;
    /**
     *
     */
    public FunctionGrapher() {
        results1 = new ArrayList<>();
        results2 = new ArrayList<>();
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
        final Graphics2D fun = (Graphics2D) g;
        if (!results1.isEmpty()) {
            fun.setStroke(new BasicStroke(1));
            fun.setColor(Color.RED);
            final Polygon p1 = new Polygon();
            double x1 = -FunctionCalculatorImpl.RANGE;
            for (final Double y : results1) {
                    p1.addPoint((int) (w / 2 + x1 * FunctionGrapher.scale * 2), (int) (h / 2 - y.doubleValue()  * FunctionGrapher.scale * 2));
                    x1 += FunctionCalculatorImpl.PRECISION;
                }
                fun.drawPolyline(p1.xpoints, p1.ypoints, p1.npoints);
            }
        if (!results2.isEmpty()) {
            double x2 = -FunctionCalculatorImpl.RANGE;
                fun.setStroke(new BasicStroke(1));
                fun.setColor(Color.BLUE);
                final Polygon p2 = new Polygon();
                for (final Double y : results1) {
                    p2.addPoint((int) (w / 2 + x2 * FunctionGrapher.scale * 2), (int) (h / 2 - y.doubleValue()  * FunctionGrapher.scale * 2));
                    x2 += FunctionCalculatorImpl.PRECISION;
                }
                fun.drawPolyline(p2.xpoints, p2.ypoints, p2.npoints);
            }
        System.out.println("prima" + results1);
        System.out.println("seconda" + results2);
        }

    private void drawLines(final Graphics g, final int w, final int h) {
        final Graphics2D lines = (Graphics2D) g;
        lines.setStroke(new BasicStroke(1));
        lines.setColor(Color.BLACK);
        for (int count = 0; count < FunctionCalculatorImpl.RANGE; count++) {
            lines.drawLine((int) (w / 2 + count * FunctionGrapher.scale * 2), (int) (h / 2 + 3 - 10 / FunctionGrapher.scale), (int) (w / 2 + count * FunctionGrapher.scale * 2), (int) (h / 2 - 3 + 10 / FunctionGrapher.scale));
            lines.drawLine((int) (w / 2 - count * FunctionGrapher.scale * 2), (int) (h / 2 + 3 - 10 / FunctionGrapher.scale), (int) (w / 2 - count * FunctionGrapher.scale * 2), (int) (h / 2 - 3 + 10 / FunctionGrapher.scale));
        }
        for (int count = 0; count < FunctionCalculatorImpl.RANGE; count++) {
            lines.drawLine((int) (w / 2 + 3 - 10 / FunctionGrapher.scale), (int) (h / 2 + count * FunctionGrapher.scale * 2), (int) (w / 2 - 3 + 10 / FunctionGrapher.scale), (int) (h / 2 + count * FunctionGrapher.scale * 2));
            lines.drawLine((int) (w / 2 + 3 - 10 / FunctionGrapher.scale), (int) (h / 2 - count * FunctionGrapher.scale * 2), (int) (w / 2 - 3 + 10 / FunctionGrapher.scale), (int) (h / 2 - count * FunctionGrapher.scale * 2));
        }
    }

    private void drawGrid(final Graphics g, final int w, final int h) {
        final Graphics2D grid = (Graphics2D) g;
        grid.setStroke(new BasicStroke(1));
        grid.setColor(Color.LIGHT_GRAY);
        for (int count = 0; count < FunctionCalculatorImpl.RANGE; count++) {
            grid.drawLine((int) (w / 2 + count * FunctionGrapher.scale), 0, (int) (w / 2 + count * FunctionGrapher.scale), h);
            grid.drawLine((int) (w / 2 - count * FunctionGrapher.scale), 0, (int) (w / 2 - count * FunctionGrapher.scale), h);
        }
        for (int count = 0; count < FunctionCalculatorImpl.RANGE; count++) {
            grid.drawLine(0, (int) (h / 2 + count * FunctionGrapher.scale), w, (int) (h / 2 + count * FunctionGrapher.scale));
            grid.drawLine(0, (int) (h / 2 - count * FunctionGrapher.scale), w, (int) (h / 2 - count * FunctionGrapher.scale));
        }
    }
    /**
     *@param first
     *@param results
     */
    public void paintFunction(final List<Double> results, final boolean first) {
        if (first) {
            this.results1 = results;
        } else {
            this.results2 = results;
        }
        this.repaint();
    }
    /**
     *@param first
     */
    public void deleteFunction(final boolean first) {
        if (first) {
            results1.clear();
        } else {
            results2.clear();
        }
        this.repaint();
    }
}

