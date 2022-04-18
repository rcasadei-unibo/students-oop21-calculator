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

    private final Map<String, CCDisplay> map = new HashMap<>();
    /**
     * This class contains 4 Buttons and 4 Displays that are linked.
     * Each display shows its buttons conversion base.
     * 
     * @param conv ActionListener for when to change display.
     */
    public ConversionPanel(final ActionListener conv) {
        this.setLayout(new GridLayout(4, 2));

        final JButton hex = new JButton("HEX");
        hex.addActionListener(conv);
        hex.setBackground(CCColors.CONVERSION_BUTTON);
        this.add(hex);
        final CCDisplay hexDisplay = new CCDisplay();
        this.add(hexDisplay);
        this.map.put(hex.getText(), hexDisplay);

        final JButton dec = new JButton("DEC");
        dec.addActionListener(conv);
        dec.setBackground(CCColors.CONVERSION_BUTTON);
        this.add(dec);
        final CCDisplay decDisplay = new CCDisplay();
        this.add(decDisplay);
        this.map.put(dec.getText(), decDisplay);

        final JButton oct = new JButton("OCT");
        oct.addActionListener(conv);
        oct.setBackground(CCColors.CONVERSION_BUTTON);
        this.add(oct);
        final CCDisplay octDisplay = new CCDisplay();
        this.add(octDisplay);
        this.map.put(oct.getText(), octDisplay);

        final JButton bin = new JButton("BIN");
        bin.addActionListener(conv);
        bin.setBackground(CCColors.CONVERSION_BUTTON);
        this.add(bin);
        final CCDisplay binDisplay = new CCDisplay();
        this.add(binDisplay);
        this.map.put(bin.getText(), binDisplay);

    }
    /**
     * @param l the number that the displays will show
     */
    public void updateConvDisplays(final long l) {
        this.map.entrySet().stream().forEach((entry) -> entry.getValue().updateText(textToBase(entry.getKey(), l)));
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
}
