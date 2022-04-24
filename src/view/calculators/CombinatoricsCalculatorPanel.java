package view.calculators;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;

import controller.calculators.logics.CombinatoricsLogics;
import controller.calculators.logics.CombinatoricsLogicsImpl;
import utils.CCColors;
import utils.CustomJToolTip;
import utils.FileResourcesUtils;
import view.components.CCDisplay;
import view.components.CCNumPad;

/**
 * 
 * A BorderLayout JPanel: the display is North, the numpad is in the center,
 * a new JPanel containing all the buttons for the Combinatorics Operations is East and the label with examples for the operations is South.
 *
 */
public class CombinatoricsCalculatorPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * It creates the Combinatorics Calculator GUI.
     */
    public CombinatoricsCalculatorPanel() {
        final CombinatoricsLogics logics = new CombinatoricsLogicsImpl();
        final var display = new CCDisplay();
        this.setLayout(new BorderLayout());
        this.add(display, BorderLayout.NORTH);
        final var explLabel = new JLabel();

        final ActionListener btnAl = e -> {
            final var btn = (JButton) e.getSource();
            display.updateText(logics.numberAction(btn.getText()));
        };

        final ActionListener calculateAl = e -> {
            display.updateUpperText(logics.calculateAction());
            display.updateText(logics.getBufferToString());
            explLabel.setText("");
        };

        final ActionListener backspaceAl = e -> {
            display.updateText(logics.backspaceAction());
        };

        final var numpad = new CCNumPad(btnAl, calculateAl, backspaceAl);
        numpad.getButtons().get("(").setEnabled(false);
        numpad.getButtons().get(")").setEnabled(false);
        numpad.getButtons().get(".").setEnabled(false);
        this.add(numpad, BorderLayout.CENTER);
        this.add(explLabel, BorderLayout.SOUTH);
        this.add(new OperationsPanel(logics, display, explLabel), BorderLayout.EAST);
    }

    /**
     * 
     * JPanel containing the buttons of the operations and the explanation buttons.
     *
     */
    static class OperationsPanel extends JPanel {

        private static final long serialVersionUID = 1L;
        private final String sep = File.separator;
        private final String directory = "resources" + this.sep;

        OperationsPanel(final CombinatoricsLogics logics, final CCDisplay display, final JLabel explLabel) {
            this.setLayout(new GridLayout(8, 2));
            for (final CombinatoricsLogics.Operations op : CombinatoricsLogics.Operations.values()) {
                this.createOpButton(op.getOpBtnName(), op.getOpModelName(), logics, display);
                this.createExplButton(op.getOpBtnName(), explLabel);
            }
        }

        private void createOpButton(final String btnName, final String opName, final CombinatoricsLogics logics, final CCDisplay display) {
            final var btn = new JButton(btnName);
            btn.addActionListener(e -> {
                display.updateText(logics.operationAction(btnName, opName));
            });
            btn.setBackground(CCColors.OPERATION_BUTTON);
            this.add(btn);
        }

        private void createExplButton(final String opName, final JLabel explLabel) {
            final var file = this.directory + opName;
            final var btn = new JButton("?") {
                private static final long serialVersionUID = 1L;
                @Override
                public JToolTip createToolTip() {
                    return new CustomJToolTip(this);
                }
            };
            ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
            btn.setToolTipText(FileResourcesUtils.readFromFile(file + "(TT).txt"));
            final String labelText = FileResourcesUtils.readFromFile(file + ".txt");
            btn.addActionListener(e -> {
                explLabel.setText(explLabel.getText().equals(labelText) ? "" : labelText);
            });
            btn.setBackground(CCColors.EXPLANATION_BUTTON);
            this.add(btn);
        }
    }
}
