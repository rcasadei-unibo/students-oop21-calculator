package view.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import utils.CCColors;
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
        f1.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        final JLabel f2 = new JLabel("  g(x) : ");
        f2.setFont(font);
        f2.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));

        final JTextField t1 = new JTextField("  ");
        t1.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        final JTextField t2 = new JTextField("  ");
        t2.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));

        final JButton create1 = new JButton("DRAW");
        create1.setBackground(CCColors.EQUAL_BUTTON);
        final JButton create2 = new JButton("DRAW");
        create2.setBackground(CCColors.EQUAL_BUTTON);

        final JButton delete1 = new JButton("DELETE");
        delete1.setBackground(CCColors.NUMBER_BUTTON);
        final JButton delete2 = new JButton("DELETE");
        delete2.setBackground(CCColors.NUMBER_BUTTON);

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
