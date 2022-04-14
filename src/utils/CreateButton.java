package utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import controller.calculators.CalculatorController;
import model.calculators.StandardCalculatorModelFactory;
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
    private static final List<String> TOKENS = new ArrayList<>();
    private static final Map<String,String> KEYMAP = new HashMap<>();
    {
        StandardCalculatorModelFactory.create().getBinaryOpMap().entrySet().stream().forEach((entry) -> TOKENS.add(entry.getKey()));
        StandardCalculatorModelFactory.create().getUnaryOpMap().entrySet().stream().forEach((entry) -> TOKENS.add(entry.getKey()));
    }

    private CreateButton() {
    }
   /**
    * 
    * @param btnName
    * @param opName
    * @param appearance
    * @param controller
    * @param display
    * @return aaaa
    */

   public static JButton createOpButton(final String btnName, final String opName, final String appearance, final CalculatorController controller, final CCDisplay display) {
       KEYMAP.put(btnName, opName);
       final JButton btn = new JButton(btnName);
       btn.addActionListener(e -> {
           final String text = ((JButton) e.getSource()).getText();
           final var isUnary = controller.isUnaryOperator(KEYMAP.get(text));
           System.out.println(text + " isUnary: " + isUnary);
           final boolean isLastInputNumber = isLastInputANumber(controller);
           if (isUnary && isLastInputNumber) {
               //controller.getManager().memory().clear();
               controller.getManager().memory().read("Syntax error");
           } else {
               controller.getManager().memory().read(text);
               if (!AVOID.contains(text)) {
                   controller.getManager().memory().read("(");
               }
           }
           display.updateText(controller.getManager().memory().getCurrentState().stream().map((x) -> {
               if (x.contains("1/x")) {
                   return "1/";
               }
               return x;
           }).reduce("", (a, b) -> a + b));
       });
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


}
