package view.main;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.util.Map;
import java.util.Optional;
import java.awt.Dimension;

import model.manager.EngineModelInterface.Calculator;
import utils.CCColors;
import view.calculators.AdvancedCalculatorPanel;
import view.calculators.CombinatoricsCalculatorPanel;
import view.calculators.GraphicCalculatorPanel;
import view.calculators.ProgrammerCalculatorPanel;
import view.calculators.ScientificCalculatorPanel;
import view.calculators.StandardCalculatorPanel;
import view.components.HistoryPanel;

/**
 * Main JFrame of the application.
 * It contains references to the JPanel of each calculator and displays the right panel on request.
 * It consists of a main panel and a menu to select the calculator to show.
 */
public class CCMainGUI extends JFrame implements View {

    private static final long serialVersionUID = -4510924334938545109L;
    private final transient ViewLogics logics = new ViewLogicsImpl(this);

    private transient Optional<JPanel> mountedPanel = Optional.empty();
    private transient Optional<Calculator> current = Optional.empty();

    private final JLabel title = new JLabel("");

    private final Map<Calculator, JPanel> views = Map.of(
            Calculator.STANDARD, new StandardCalculatorPanel(Calculator.STANDARD.getController()),
            Calculator.SCIENTIFIC, new ScientificCalculatorPanel(Calculator.SCIENTIFIC.getController()),
            Calculator.PROGRAMMER, new ProgrammerCalculatorPanel(Calculator.PROGRAMMER.getController()),
            Calculator.GRAPHIC, new GraphicCalculatorPanel(Calculator.GRAPHIC.getController()),
            Calculator.ADVANCED, new AdvancedCalculatorPanel(),
            Calculator.COMBINATORICS, new CombinatoricsCalculatorPanel()
            );
    /**
     * Creates the JFrame of the application and sets it visible.
     */
    public CCMainGUI() {

        this.setTitle("Calculator Collection");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JMenuBar menuBar = new JMenuBar();
        final JMenu menu = new JMenu("Select Calculator");
        menuBar.add(menu);
        menuBar.add(title);
        menuBar.add(Box.createHorizontalGlue());
        final var historyBtn = new JButton("History  ");
        historyBtn.setBackground(CCColors.OPERATION_BUTTON);
        historyBtn.setMnemonic('h');
        historyBtn.addActionListener((e) -> this.toggleHistory());
        menuBar.add(historyBtn);

        menu.add(this.createMenuItem("Standard Calculator", Calculator.STANDARD));
        menu.add(this.createMenuItem("Scientific Calculator", Calculator.SCIENTIFIC));
        menu.add(this.createMenuItem("Graphic Calculator", Calculator.GRAPHIC));
        menu.add(this.createMenuItem("Programmer Calculator", Calculator.PROGRAMMER));
        menu.add(this.createMenuItem("Combinatorics Calculator", Calculator.COMBINATORICS));
        menu.add(this.createMenuItem("Advanced Calculator", Calculator.ADVANCED));
        this.setJMenuBar(menuBar);

        this.logics.mount(Calculator.STANDARD);
        this.setLocationByPlatform(true);
        this.setVisible(true);
    }

    private void toggleHistory() {

        this.mountedPanel.ifPresent((mounted) -> {
            if ("HistoryPanel".equals(mounted.getClass().getSimpleName())) {
                this.logics.mount(this.current.get());
            } else {
                this.getContentPane().remove(mounted);
                final var panel = new HistoryPanel(this.logics.getHistory());
                this.getContentPane().add(panel);

                this.mountedPanel = Optional.of(panel);

                this.revalidate();
                this.repaint();
            }
        });

    }

    private JMenuItem createMenuItem(final String text, final Calculator calcName) {
        final JMenuItem menuItem = new JMenuItem(text);
        menuItem.addActionListener(e -> this.logics.mount(calcName));
        return menuItem;
    }

    @Override
    public void show(final Calculator calc) {
        title.setText(calc.name());

        this.mountedPanel.ifPresent((mounted) -> this.getContentPane().remove(mounted));
        final JPanel panel = this.views.get(calc);
        this.getContentPane().add(panel);

        this.setMinimumSize(new Dimension(0, 0));
        this.pack();
        this.setMinimumSize(this.getSize());

        this.current = Optional.of(calc);
        this.mountedPanel = Optional.of(panel);

        this.revalidate();
        this.repaint();
    }

}
