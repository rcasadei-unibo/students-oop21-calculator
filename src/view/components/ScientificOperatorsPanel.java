package view.components;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import utils.CreateButton;
import controller.calculators.logics.InputFormatterLogics;
import controller.calculators.logics.OutputFormatterLogics;
import utils.CCColors;
/**
 * 
 *
 *
 */
public class ScientificOperatorsPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 268436559888047832L;
    private static final int COLUMNS = 2;
    private static final int LINES = 7;
    /**
     * 
     * @param inFormatter
     * @param outFormatter
     * 
     */
    public ScientificOperatorsPanel(final InputFormatterLogics inFormatter, final OutputFormatterLogics outFormatter) {
        this.setLayout(new GridLayout(LINES, COLUMNS));

        final JButton pi = new JButton("\u03C0");
        pi.setBackground(CCColors.OPERATION_BUTTON);
        pi.addActionListener(e -> {
            inFormatter.read(Double.toString(Math.PI));
            outFormatter.updateDisplay();
        });
        final JButton eul = new JButton("\u2107");
        eul.setBackground(CCColors.OPERATION_BUTTON);
        eul.addActionListener(e -> {
            inFormatter.read(Double.toString(Math.E));
            outFormatter.updateDisplay();
        });
        this.add(eul);
        this.add(pi);

        final var scientificOp = List.of("log", "ln", "root", "^", "abs", "factorial", "sin", "cos", "tan", "csc", "sec", "cot");
        scientificOp.forEach((op) -> {
            switch (op) {
               case "root":
                   final JButton btn1 = CreateButton.createOpButton("ʸ√x");
                   btn1.addActionListener(e -> {
                       inFormatter.read(op);
                       outFormatter.updateDisplay();
                    });
                    this.add(btn1);
                   break;
               case "factorial":
                   final JButton btn2 = CreateButton.createOpButton("!n");
                   btn2.addActionListener(e -> {
                       inFormatter.read(op);
                       outFormatter.updateDisplay();
                    });
                    this.add(btn2);
                   break;
               case "^":
                   final JButton btn3 = CreateButton.createOpButton("xʸ");
                   btn3.addActionListener(e -> {
                       inFormatter.read(op);
                       outFormatter.updateDisplay();
                    });
                    this.add(btn3);
                   break;
               default :
                   final JButton btn = CreateButton.createOpButton(op);
                   btn.addActionListener(e -> {
                       inFormatter.read(op);
                       outFormatter.updateDisplay();
                    });
                    this.add(btn);
                    break;
            }
        });
    }
}
