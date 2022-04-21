package view.logics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.CalcException;
import javax.swing.JButton;

import controller.calculators.CalculatorController;
import utils.CCColors;
import view.components.CCDisplay;
/**
 * 
 * 
 *
 */
public final class CreateButton {
    /**
     * 
     */
    private static final List<String> AVOID = List.of("+", "-", "×", "÷", "%");
    private static final Map<String, String> KEYMAP = new HashMap<>();
    private static final Map<String, String> APPEARANCEMAP = new HashMap<>();
    private CreateButton() {
    }
   /**
    * @param btnName the button's name
    * @param opName the mapped's operator name
    * @param appearance the appearance it will have
    * @param controller the current controller
    * @param display the current display
    * @return a button linked to its controller and display
    */

   public static JButton createOpButton(final String btnName, final String opName, final String appearance, final CalculatorController controller, final CCDisplay display) {
       KEYMAP.put(btnName, opName);
       APPEARANCEMAP.put(opName, appearance);
       final JButton btn = new JButton(btnName);
       btn.addActionListener(e -> {
           final String text = ((JButton) e.getSource()).getText();
           final String op = KEYMAP.get(text);
           final var isUnary = controller.isUnaryOperator(op);
           final boolean isLastInputNumber = isLastInputANumber(controller);

               if (isUnary && isLastInputNumber) {
                   controller.getManager().memory().clear();
                   display.updateText("Syntax error");
               } else {
                   controller.getManager().memory().read(op);
                   if (!AVOID.contains(text)) {
                       controller.getManager().memory().read("(");
                   }
                   updateDisplay(controller, display);
               }
       });
       btn.setBackground(CCColors.OPERATION_BUTTON);
       return btn;
   }
   public static JButton createOpButtonFR(final String btnName) {
       final JButton btn = new JButton(btnName);
       btn.setBackground(CCColors.OPERATION_BUTTON);
       return btn;
   }
   private static boolean isLastInputANumber(final CalculatorController controller) {
       final var buffer = controller.getManager().memory().getCurrentState();
       if (!buffer.isEmpty()) {
           try {
               Double.parseDouble(buffer.get(buffer.size() - 1));
               return true;
           } catch (final NumberFormatException e) {
               return false;
           }
       }
       return false;
   }
   /**
    * Updates the display replacing the operators with their appearance.
    * @param controller the current controller
    * @param display the current display
    */
   public static void updateDisplay(final CalculatorController controller, final CCDisplay display) {
       display.updateText(controller.getManager().memory().getCurrentState().stream().map((x) -> {
           if (APPEARANCEMAP.containsKey(x)) {
               return APPEARANCEMAP.get(x);
           }
           return x;
       }).reduce("", (a, b) -> a + b));
   }

}
