package view.components;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utils.CalcException;
import utils.FunctionCalculator;
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
        final JLabel f1 = new JLabel("F(x) : ");
        final JTextField t1 = new JTextField();
        final JButton create1 = new JButton("CREATE");
        final JLabel f2 = new JLabel("G(x) : ");
        final JTextField t2 = new JTextField();
        final JButton create2 = new JButton("CREATE");
        final JButton delete1 = new JButton("DELETE");
        final JButton delete2 = new JButton("DELETE");

        create1.addActionListener(e -> {
            logic.calculate(t1.getText());
            try {
                f.paintFunction(logic.getResults(), true);
            } catch (CalcException e1) {
                e1.printStackTrace();
            }
        });

        delete1.addActionListener(e -> {
            f.deleteFunction(true);

        });

        create2.addActionListener(e -> {
            logic.calculate(t2.getText());
            try {
                f.paintFunction(logic.getResults(), false);
            } catch (CalcException e1) {
                e1.printStackTrace();
            }
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
