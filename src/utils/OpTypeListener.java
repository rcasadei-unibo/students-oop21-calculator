package utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.calculators.CalculatorController;
import view.components.CCDisplay;
/**
 * 
 * 
 *
 */
public final class OpTypeListener {
    /**
     * 
     */
    private OpTypeListener() {
    }

    /**
     * 
     * @param display
     * @param controller
     * @return to do
     */
    public static ActionListener getUnaryListener(final CCDisplay display, final CalculatorController controller) {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JButton btn = (JButton) e.getSource();
                display.updateText(btn.getText() + controller.getManager().getCurrentState().stream().reduce("(", (a, b) -> a + b) + ")");
                controller.getManager().read(btn.getText());
            }
        };
    }

    /**
     * 
     * @param display
     * @param controller
     * @return to do
     */
   public static ActionListener getBinaryListener(final CCDisplay display, final CalculatorController controller) {
        return new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JButton btn = (JButton) e.getSource();
                controller.getManager().read(btn.getText());
                display.updateText(controller.getManager().getCurrentState().stream().reduce("", (a, b) -> a + b));
            }
        };
    }


}
