package controller.calculators.logics;
import javax.swing.JButton;
import utils.CCColors;
/**
 * 
 * 
 *
 */
public final class CreateButton {
    private CreateButton() {
    }
   /**
    * 
    * @param btnName is the name to be displayed on the button
    * @return a new JButton
    */
   public static JButton createOpButton(final String btnName) {
       final JButton btn = new JButton(btnName);
       btn.setBackground(CCColors.OPERATION_BUTTON);
       return btn;
   }
}
