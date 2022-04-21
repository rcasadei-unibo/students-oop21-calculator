package view.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import utils.CCColors;
import view.logics.FunctionCalculatorImpl;
/**
 * 
 * Given one or more lists of Doubles ( f(x)'s ) from view.logics.FunctionCalculator, draws or deletes the functions in this JPanel.
 *
 */
public class FunctionGrapher extends JPanel {
    private static final long serialVersionUID = -6534831232343094643L;
    private static double scale = 10;
    private static final int LINES_DISTANCE = 5;
    private static double bound = 3 - 10 / FunctionGrapher.scale;
    private final Random rand = new Random();
    private final List<List<Double>> buffer = new ArrayList<>();
    private final List<Color> colors = new ArrayList<>();
    /**
     * Initialize the screen size, sets the border and adds a mouse wheel listenere used to zoom in the panel.
     */
    public FunctionGrapher() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double width = screenSize.getWidth() * 0.35;
        final double height = screenSize.getHeight() / 2;
        this.setPreferredSize(new Dimension((int) width, (int) height));
        this.setBorder(new LineBorder(CCColors.GRAPHIC_BORDERS, 1));
        this.addMouseWheelListener(m -> {
            if (m.getWheelRotation() > 0 && FunctionGrapher.scale > 10) {
                FunctionGrapher.scale--;
            } else if (m.getWheelRotation() < 0) {
                FunctionGrapher.scale++;
            }
            this.repaint();
        });
    }
    /**
     * @param gr is the param that gives the possibility to draw in this JPanel
     */
    public void paintComponent(final Graphics gr) {
        final int w = this.getWidth();
        final int h = this.getHeight();
        super.paintComponent(gr);
        drawGrid(gr, w, h);
        drawAxes(gr, w, h);
        drawLines(gr, w, h);
        drawFunction(gr, w, h);
    }

    private void drawAxes(final Graphics gr, final int w, final int h) {
        final Graphics2D axes = (Graphics2D) gr;
        axes.setStroke(new BasicStroke(1));
        axes.setColor(CCColors.GRAPHIC_AXES);
        axes.drawLine(0, h / 2, w, h / 2);
        axes.drawLine(w / 2, 0, w / 2, h);
        axes.drawString("o", w / 2 - 10, h / 2 + 10);
        axes.drawString("x", w - 10, h / 2 + 10);
        axes.drawString("y", w / 2 - 10, 10 + 3);
    }

    private void drawFunction(final Graphics gr, final int w, final int h) {
        final Graphics2D fun = (Graphics2D) gr;
        fun.setStroke(new BasicStroke(1));
        int colorIterator = 0;
        for (final var f : buffer) {
            fun.setColor(colors.get(colorIterator));
            final Polygon p = getPolygon(f, w, h);
            fun.drawPolyline(p.xpoints, p.ypoints, p.npoints);
            colorIterator++;
        }
    }

    private void drawLines(final Graphics gr, final int w, final int h) {
        final Graphics2D lines = (Graphics2D) gr;
        lines.setStroke(new BasicStroke());
        lines.setColor(CCColors.GRAPHIC_AXES);
        for (int count = 0; count < FunctionCalculatorImpl.RANGE; count++) {
            lines.drawLine((int) (w / 2 + count * FunctionGrapher.scale * 2), (int) (h / 2 + bound), (int) (w / 2 + count * FunctionGrapher.scale * 2), (int) (h / 2 - 3 + 10 / FunctionGrapher.scale));
            lines.drawLine((int) (w / 2 - count * FunctionGrapher.scale * 2), (int) (h / 2 + bound), (int) (w / 2 - count * FunctionGrapher.scale * 2), (int) (h / 2 - 3 + 10 / FunctionGrapher.scale));
            if (count % FunctionGrapher.LINES_DISTANCE == 0 && count != 0) {
                lines.drawString(Integer.toString(count), (int) (w / 2 + count * FunctionGrapher.scale * 2 - 4 - 2), (int) (h / 2 - 4 - 2 + 10 / FunctionGrapher.scale));
                lines.drawString(Integer.toString(-(count)), (int) (w / 2 - count * FunctionGrapher.scale * 2 - 4 - 4), (int) (h / 2 - 4 - 2 + 10 / FunctionGrapher.scale));
            }
        }
        for (int count = 0; count < FunctionCalculatorImpl.RANGE; count++) {
            lines.drawLine((int) (w / 2 + bound), (int) (h / 2 + count * FunctionGrapher.scale * 2), (int) (w / 2 - 3 + 10 / FunctionGrapher.scale), (int) (h / 2 + count * FunctionGrapher.scale * 2));
            lines.drawLine((int) (w / 2 + bound), (int) (h / 2 - count * FunctionGrapher.scale * 2), (int) (w / 2 - 3 + 10 / FunctionGrapher.scale), (int) (h / 2 - count * FunctionGrapher.scale * 2));
            if (count % FunctionGrapher.LINES_DISTANCE == 0 && count != 0) {
                lines.drawString(Integer.toString(-(count)), (int) (w / 2 + bound + 4 + 1), (int) (h / 2 + count * FunctionGrapher.scale * 2 + 4 + 1));
                lines.drawString(Integer.toString(count), (int) (w / 2 + bound + 3), (int) (h / 2 - count * FunctionGrapher.scale * 2 + 4 + 1));
            }
        }
    }

    private void drawGrid(final Graphics gr, final int w, final int h) {
        final Graphics2D grid = (Graphics2D) gr;
        grid.setStroke(new BasicStroke(1));
        grid.setColor(CCColors.GRAPHIC_GRID);
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
     * 
     * Adds the given list to the buffer and then calls the repaint method.
     *@param result is a list which contains all the f(x) inside a certain range from a function written in the FunctionInsertionPanel and then calculated by FunctionCalculator.
     *
     */
    public void addFunction(final List<Double> result) {
        this.colors.add(new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
        final List<Double> temp = new ArrayList<>();
        temp.addAll(result);
        this.buffer.add(temp);
        this.repaint();
    }
    /**
     * Removes the last function anc color from the buffer and calls the repaint method.
     */
    public void deleteFunction() {
        if (!this.buffer.isEmpty()) {
            this.buffer.remove(buffer.size() - 1);
            this.colors.remove(colors.size() - 1);
            this.repaint();
        }
    }

    private Polygon getPolygon(final List<Double> results, final int w, final int h) {
        final Polygon polygon = new Polygon();
        double x = -FunctionCalculatorImpl.RANGE;
        for (final Double y : results) {
                polygon.addPoint((int) (w / 2 + x * FunctionGrapher.scale * 2), (int) (h / 2 - y.doubleValue()  * FunctionGrapher.scale * 2));
                x += FunctionCalculatorImpl.PRECISION;
        }
        return polygon;
    }
}

