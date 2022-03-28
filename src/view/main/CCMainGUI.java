package view.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.border.TitledBorder;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import java.awt.BorderLayout;





import controller.manager.CCManager;
import model.manager.CCManagerModel.Calculator;
import view.components.CCNumPad;

/**
 * 
 *
 */
public class CCMainGUI extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -4510924334938545109L;
    private final CCManager controller = new CCManager();
    private final JPanel outer = new JPanel();
    private JPanel mountedCalc;
    private final Dimension menuDims = new Dimension(800, 600);

    /**
     * 
     */
    public CCMainGUI() {

        this.setTitle("Calculator Collection");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(menuDims);

        outer.setLayout(new BorderLayout());
        this.getContentPane().add(outer);

        final JPanel pEastInternal = new JPanel(new GridBagLayout());
        pEastInternal.setPreferredSize(new Dimension(180, menuDims.height));
        pEastInternal.setBorder(new TitledBorder("SELECT CALCULATOR"));
        final GridBagConstraints cnst = new GridBagConstraints();
        cnst.gridy = 0;
        cnst.insets = new Insets(3, 3, 3, 3); 
        cnst.fill = GridBagConstraints.HORIZONTAL; 
        pEastInternal.add(this.createMenuButton("Standard", Calculator.STANDARD), cnst);
        cnst.gridy++; 
        pEastInternal.add(this.createMenuButton("Scientific", Calculator.SCIENTIFIC), cnst);
        cnst.gridy++;
        pEastInternal.add(this.createMenuButton("Programmer", Calculator.PROGRAMMER), cnst);
        cnst.gridy++;
        pEastInternal.add(this.createMenuButton("Graphic", Calculator.GRAPHIC), cnst);
        cnst.gridy++;
        pEastInternal.add(this.createMenuButton("Combinatorics", Calculator.COMBINATORICS), cnst);
        cnst.gridy++;
        pEastInternal.add(this.createMenuButton("Advanced", Calculator.ADVANCED), cnst);
        outer.add(pEastInternal, BorderLayout.WEST);

        
        this.setVisible(true);
    }

    private void mount(final Calculator calc) {
        controller.mount(calc);

        if (mountedCalc != null) {
            outer.remove(this.mountedCalc);
        }

        outer.add(controller.getMounted().getGUI(), BorderLayout.CENTER);

        this.mountedCalc = controller.getMounted().getGUI();
        outer.revalidate();
        outer.repaint();
    }

    private JButton createMenuButton(final String text, final Calculator calcName) {
        final var btn = new JButton(text);
        btn.addActionListener((e) -> this.mount(calcName));
        return btn;
    }

}
