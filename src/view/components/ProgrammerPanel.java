package view.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
//TODO missing javadoc
/**
 * 
 *
 */
public class ProgrammerPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 2469993034979788995L;
    private final List<String> topOperators = List.of("roR", "roL", "shiftR", "shiftL", "nand");
    private final List<String> middleOperators = List.of("and", "or", "xor");
    private final List<String> rightOperators = List.of("not", "nor", "+", "-", "×", "÷");
    
    
    
    /**
     * 
     * @param opAl
     */
    public ProgrammerPanel(final ActionListener opAl) {
        
        this.setLayout(new GridLayout(2, 3));
        
        final JPanel topPanel = new JPanel();
        final JPanel middlePanel = new JPanel();
        final JPanel bottomPanel = new JPanel();

        this.add(new CCDisplay());
        
        this.add(new ConversionPanel(opAl));
        
        final JPanel binaryOp = new JPanel();
        topOperators.forEach((entry) -> {
          final JButton btn = new JButton(entry);
          btn.addActionListener(opAl);
          binaryOp.add(btn);
        });
        this.add(binaryOp);
        
        
        
        this.add(topPanel);
        this.add(middlePanel);
        this.add(bottomPanel);
        
        //topPanel.add(new CCDisplay());
//        middlePanel.setLayout(new GridLayout(2,1));
//        middlePanel.add(new ConversionPanel(opAl));
//        final JPanel binaryOp = new JPanel();
//        topOperators.forEach((entry) -> {
//            final JButton btn = new JButton(entry);
//            btn.addActionListener(opAl);
//            binaryOp.add(btn);
//        });
//        middlePanel.add(binaryOp);
//        
//        
//        bottomPanel.setLayout(new BorderLayout());
//        bottomPanel.add(new HexadecimalLettersPanel(opAl), BorderLayout.WEST);
//        bottomPanel.add(new CCNumPad(opAl, opAl, opAl), BorderLayout.SOUTH);
//        bottomPanel.add(binaryOp, BorderLayout.NORTH);
//        
//        final JPanel logicPanel = new JPanel();
//        logicPanel.setLayout(new GridLayout(3, 1));
//        this.middleOperators.forEach((entry) -> {
//                final JButton btn = new JButton(entry);
//                btn.addActionListener(opAl);
//                logicPanel.add(btn);
//            }
//        );
//        bottomPanel.add(logicPanel, BorderLayout.CENTER);
//        
//        final JPanel rightOperands = new JPanel();
//        rightOperands.setLayout(new GridLayout(6, 1));
//        this.rightOperators.forEach((entry) -> {
//                final JButton btn = new JButton(entry);
//                btn.addActionListener(opAl);
//                rightOperands.add(btn);
//        });
//        
//        bottomPanel.add(rightOperands, BorderLayout.EAST);
        
        
        

    }
}
