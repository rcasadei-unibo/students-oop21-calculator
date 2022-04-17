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
        final JLabel f2 = new JLabel("G(x) : ");
        final JTextField t1 = new JTextField();
        final JTextField t2 = new JTextField();
        final JButton create1 = new JButton("CREATE");
        create1.addActionListener(e -> {
            logic.calculate(t1.getText());
            try {
                f.paintFunction1(logic.getResults());
            } catch (CalcException e1) {
                e1.printStackTrace();
            }
        });
        final JButton create2 = new JButton("CREATE");
        create2.addActionListener(e -> {
            logic.calculate(t2.getText());
            try {
                f.paintFunction2(logic.getResults());
            } catch (CalcException e1) {
                e1.printStackTrace();
            }

        });
        final JButton delete1 = new JButton("DELETE");
        delete1.addActionListener(e -> {
            f.deleteFunction1();

        });
        final JButton delete2 = new JButton("DELETE");
        delete2.addActionListener(e -> {
            f.deleteFunction2();
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
