package view.main;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.Toolkit;

import java.awt.Dimension;

import java.awt.BorderLayout;

import controller.manager.CCManager;
import model.manager.ManagerModelInterface.Calculator;

/**
 * TODO: min size of frame, menu, numpad.
 * TODO: update font size when tall enough
 * TODO: display size changes on resize
 *
 */
public class CCMainGUI extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -4510924334938545109L;
    private final CCManager controller = new CCManager();
    private final JPanel outer = new JPanel();
    private JPanel mountedCalc;
    /**
     * 
     */
    public CCMainGUI() {

        this.setTitle("Calculator Collection");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double width = screenSize.getWidth() / 3;
        final double height = screenSize.getHeight() * 2 / 3;
        this.setSize(new Dimension((int) width, (int) height));

        final JMenuBar menuBar = new JMenuBar();
        final JMenu menu = new JMenu("Select Calculator");
        menuBar.add(menu);

        menu.add(this.createMenuItem("Standard Calculator", Calculator.STANDARD));
        menu.add(this.createMenuItem("Scientific Calculator", Calculator.SCIENTIFIC));
        menu.add(this.createMenuItem("Graphic Calculator", Calculator.GRAPHIC));
        menu.add(this.createMenuItem("Programmer Calculator", Calculator.PROGRAMMER));
        menu.add(this.createMenuItem("Combinatorics Calculator", Calculator.COMBINATORICS));
        menu.add(this.createMenuItem("Advanced Calculator", Calculator.ADVANCED));
        this.setJMenuBar(menuBar);




        outer.setLayout(new BorderLayout());
        this.getContentPane().add(outer);

        this.mount(Calculator.STANDARD);
        this.setVisible(true);
    }

    private void mount(final Calculator calc) {
        controller.mount(calc);

        if (mountedCalc != null) {
            outer.remove(this.mountedCalc);
        }

        outer.add(controller.getMounted().getGUI(), BorderLayout.CENTER);

        this.mountedCalc = controller.getMounted().getGUI();
        outer.revalidate();
        outer.repaint();
    }

    private JMenuItem createMenuItem(final String text, final Calculator calcName) {
        final JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(e -> this.mount(calcName));
        return menuItem;
    }

}
