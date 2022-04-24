package utils;
import javax.swing.JButton;
/**
 * Utility class to create an operation button.
 */
public final class CreateButton {
    private CreateButton() {
    }
   /**
    * 
    * @param btnName the Text that the button will display
    * @return a button with an Operation's color background
    */
   public static JButton createOpButton(final String btnName) {
       final JButton btn = new JButton(btnName);
       btn.setBackground(CCColors.OPERATION_BUTTON);
       return btn;
   }
}
