package view.components;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;

/**
 * JPanel that displays the history of the calculations in a list. 
 */
public class HistoryPanel extends JPanel {

    private static final long serialVersionUID = -5379164910350177067L;

    /**
     * Constructs a new JPanel that displays the given list of strings. 
     * If the list is empty, a message will be shown.
     * @param history List of strings to display.
     */
    public HistoryPanel(final List<String> history) {
        this.setLayout(new GridLayout(1, 1));
        final var list = new JList<Object>(history.isEmpty() ? List.of("Your calculations will be shown here!").toArray() : history.toArray());

        final var scroller = new JScrollPane(list);
        this.add(scroller);
    }

}
