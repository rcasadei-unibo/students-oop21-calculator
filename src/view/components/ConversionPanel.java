package view.components;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import utils.CCColors;
import utils.ConversionAlgorithms;

/**
 *  This class handles conversion and displaying those conversions.
 */
public class ConversionPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -9080067811293897721L;

    private final Map<String, CCDisplay> displayMap = new HashMap<>();
    private final Map<String, JButton> buttonsMap = new HashMap<>();
    /**
     * This class contains 4 Buttons and 4 Displays that are linked.
     * Each display shows its buttons conversion base.
     * 
     * @param conv ActionListener for when to change display.
     */
    public ConversionPanel(final ActionListener conv) {
        this.setLayout(new GridLayout(4, 2));
        this.createButton("HEX", conv);
        this.createButton("DEC", conv);
        this.createButton("OCT", conv);
        this.createButton("BIN", conv);
    }
    /**
     * @param l the number that the displays will show
     */
    public void updateConvDisplays(final long l) {
        this.displayMap.entrySet().stream().forEach((entry) -> entry.getValue().updateText(textToBase(entry.getKey(), l)));
    }
    /**
     * Updated the buttons' color to indicate it is active.
     * @param text the button's text.
     */
    public void changeToActive(final String text) {
        this.buttonsMap.forEach((str, btn) -> {
            if (text.equals(str)) {
                btn.setBackground(CCColors.ACTIVE_CONVERSION_BUTTON);
            } else {
                btn.setBackground(CCColors.CONVERSION_BUTTON);
            }
        });
    }
    private String textToBase(final String text, final long l) {
        switch (text) {
        case "HEX":
            return ConversionAlgorithms.conversionToStringBase(16, l);
        case "DEC":
            return l >= 0 ? "+".concat(String.valueOf(l)) : String.valueOf(l);
        case "OCT":
            return ConversionAlgorithms.conversionToStringBase(8, l);
        case "BIN":
            return ConversionAlgorithms.conversionToStringBase(2, l);
        default:
            return null;
        }
    }
    private void createButton(final String text, final ActionListener conv) {
        final JButton btn = new JButton(text);
        btn.addActionListener(conv);
        btn.setBackground(CCColors.CONVERSION_BUTTON);
        this.add(btn);
        final CCDisplay display = new CCDisplay();
        this.add(display);
        this.displayMap.put(btn.getText(), display);
        this.buttonsMap.put(text, btn);
    }
}
