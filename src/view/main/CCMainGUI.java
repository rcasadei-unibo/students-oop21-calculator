package view.main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.util.Map;
import java.util.Optional;
import java.awt.Dimension;

import java.awt.BorderLayout;

import controller.manager.CCManager;
import controller.manager.ManagerInterface;
import model.manager.ManagerModelInterface.Calculator;
import view.calculators.CombinatoricsCalculatorPanel;
import view.calculators.ProgrammerCalculatorPanel;
import view.calculators.ScientificCalculatorPanel;
import view.calculators.StandardCalculatorPanel;

/**
 * TODO: min size of frame, menu, numpad.
 * TODO: update font size when tall enough
 * TODO: display size changes on resize
 *
 */
public class CCMainGUI extends JFrame implements View {

    /**
     * 
     */
    private static final long serialVersionUID = -4510924334938545109L;
    private final transient ManagerInterface controller = new CCManager(this);
    private final JPanel outer = new JPanel();
    private transient Optional<JPanel> mountedPanel = Optional.empty();
    private final JLabel title = new JLabel("");

    private final Map<Calculator, JPanel> views = Map.of(
            Calculator.STANDARD, new StandardCalculatorPanel(Calculator.STANDARD.getController()),
            Calculator.SCIENTIFIC, new ScientificCalculatorPanel(Calculator.SCIENTIFIC.getController()),
            Calculator.PROGRAMMER, new ProgrammerCalculatorPanel(Calculator.PROGRAMMER.getController()),
            Calculator.GRAPHIC, new StandardCalculatorPanel(Calculator.STANDARD.getController()),
            Calculator.ADVANCED, new StandardCalculatorPanel(Calculator.STANDARD.getController()),
            Calculator.COMBINATORICS, new CombinatoricsCalculatorPanel(Calculator.COMBINATORICS.getController())
            );
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
        menuBar.add(title);

        menu.add(this.createMenuItem("Standard Calculator", Calculator.STANDARD));
        menu.add(this.createMenuItem("Scientific Calculator", Calculator.SCIENTIFIC));
        menu.add(this.createMenuItem("Graphic Calculator", Calculator.GRAPHIC));
        menu.add(this.createMenuItem("Programmer Calculator", Calculator.PROGRAMMER));
        menu.add(this.createMenuItem("Combinatorics Calculator", Calculator.COMBINATORICS));
        menu.add(this.createMenuItem("Advanced Calculator", Calculator.ADVANCED));
        this.setJMenuBar(menuBar);

        outer.setLayout(new BorderLayout());
        this.getContentPane().add(outer);

        this.controller.mount(Calculator.STANDARD);
        this.setVisible(true);
    }

    private JMenuItem createMenuItem(final String text, final Calculator calcName) {
        final JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(e -> this.controller.mount(calcName));
        return menuItem;
    }

    @Override
    public void show(final Calculator calc) {
        title.setText(calc.name());

        this.mountedPanel.ifPresent((mounted) -> outer.remove(mounted));
        final JPanel panel = this.views.get(calc);
        outer.add(panel, BorderLayout.CENTER);
        this.mountedPanel = Optional.of(panel);

        outer.revalidate();
        outer.repaint();
    }

}
