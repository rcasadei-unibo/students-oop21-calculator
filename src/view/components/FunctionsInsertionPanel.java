package view.components;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.logics.FunctionCalculator;
/**
 * 
 * 
 *
 */
public class FunctionsInsertionPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -7104626977730130720L;
    /**
     *@param logic
     *@param f
     */
    public FunctionsInsertionPanel(final FunctionCalculator logic, final FunctionGrapher f) {
        this.setLayout(new GridLayout(2, 4));
        final Font font = new Font("Serif", Font.ITALIC + Font.BOLD, 20);
        final JLabel f1 = new JLabel("   f(x) : ");
        f1.setFont(font);
        final JLabel f2 = new JLabel("  g(x) : ");
        f2.setFont(font);
        final JTextField t1 = new JTextField();
        final JTextField t2 = new JTextField();
        final JButton create1 = new JButton("DRAW");
        final JButton create2 = new JButton("DRAW");
        final JButton delete1 = new JButton("DELETE");
        final JButton delete2 = new JButton("DELETE");

        create1.addActionListener(e -> {
            logic.calculate(t1.getText());
            f.paintFunction(logic.getResults(), true);
        });

        delete1.addActionListener(e -> {
            f.deleteFunction(true);

        });

        create2.addActionListener(e -> {
            logic.calculate(t2.getText());
            f.paintFunction(logic.getResults(), false);
        });

        delete2.addActionListener(e -> {
            f.deleteFunction(false);
        });

        this.add(f1);
        this.add(t1);
        this.add(create1);
        this.add(delete1);
        this.add(f2);
        this.add(t2);
        this.add(create2);
        this.add(delete2);
    }
}
