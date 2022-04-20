package view.components;

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
        this.setLayout(new GridLayout(1, 4));
        final Font font = new Font("Serif", Font.ITALIC + Font.BOLD, 20);

        final JLabel fun = new JLabel("   f(x) : ");
        fun.setFont(font);
        fun.setBorder(new LineBorder(CCColors.GRAPHIC_BORDERS, 1));

        final JTextField t = new JTextField("  ");
        t.setBorder(new LineBorder(CCColors.GRAPHIC_BORDERS, 1));

        final JButton draw = new JButton("ADD");
        draw.setBackground(CCColors.EQUAL_BUTTON);

        final JButton delete = new JButton("DELETE LAST");
        delete.setBackground(CCColors.NUMBER_BUTTON);

        draw.addActionListener(e -> {
            logic.calculate(t.getText());
            if (logic.getResults().isEmpty()) {
                t.setText(" SyntaxError");
            } else {
                f.addFunction(logic.getResults());
            }
        });

        delete.addActionListener(e -> {
            f.deleteFunction();
        });

        this.add(fun);
        this.add(t);
        this.add(draw);
        this.add(delete);
    }
}
