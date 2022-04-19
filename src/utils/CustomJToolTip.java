package utils;

import javax.swing.JComponent;
import javax.swing.JToolTip;

/**
 * 
 * A JToolTip with a custom background color.
 * Taken from a StackOverflow answer (https://stackoverflow.com/questions/26906039/how-to-set-the-background-of-tooltip-in-swing)
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
