package view.components;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controller.calculators.logics.GraphicLogics;
import controller.calculators.logics.GraphicLogicsImpl;
import utils.CCColors;
/**
 * 
 * This component gives the user a JTextField which can be used to write the function he wants and draw them on the FunctionGrapher panel or delete them using two JButtons.
 * This component communicates with both the FunctionGrapher and the FunctionCalculator in order to give the FunctionGrapher the data he needs to paint the functions.
 * 
 */
public class FunctionsInsertionPanel extends JPanel {
    private static final long serialVersionUID = -7104626977730130720L;
    /**
     *
     *@param f is the component where the function will be visualized.
     *
     */
    public FunctionsInsertionPanel(final FunctionGrapher f) {
        final GraphicLogics calc = new GraphicLogicsImpl();
        this.setLayout(new GridLayout(1, 4));
        final Font font = new Font("Serif", Font.ITALIC + Font.BOLD, 20);

        final JLabel fun = new JLabel("   f(x) : ");
        fun.setFont(font);
        fun.setBorder(new LineBorder(CCColors.GRAPHIC_BORDERS, 1));

        final JTextField t = new JTextField("");
        t.setBorder(new LineBorder(CCColors.GRAPHIC_BORDERS, 1));

        final JButton draw = new JButton("ADD");
        draw.setBackground(CCColors.EQUAL_BUTTON);

        final JButton delete = new JButton("DELETE LAST");
        delete.setBackground(CCColors.NUMBER_BUTTON);

        draw.addActionListener(e -> {
            calc.calculate(t.getText());
            if (calc.getResults().isEmpty()) {
                t.setText(" SyntaxError");
            } else {
                f.addFunction(calc.getResults());
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
