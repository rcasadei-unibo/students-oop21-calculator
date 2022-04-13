package view.components;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;

/**
 * TODO: javadoc.
 */
public class HistoryPanel extends JPanel {

    private static final long serialVersionUID = -5379164910350177067L;

    public HistoryPanel(final List<String> history) {
        this.setLayout(new GridLayout(1, 1));
        final var list = new JList<Object>(history.toArray());

        final var scroller = new JScrollPane(list);
        this.add(scroller);
    }

}
