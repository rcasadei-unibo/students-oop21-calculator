package utils;

import javax.swing.JComponent;
import javax.swing.JToolTip;

/**
 * 
 * A JToolTip with a custom background color.
 *
 */
public class CustomJToolTip extends JToolTip {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 
     * @param component the button whose JToolTip will be changed
     */
    public CustomJToolTip(final JComponent component) {
        super();
        setComponent(component);
        setBackground(CCColors.DISPLAY);
    }
}
