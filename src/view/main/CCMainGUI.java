package view.main;

import java.util.List;

/**
 * 
 *
 */
public class CCMainGUI {

    public void log(final List<String> list) {
        System.out.println(list.stream().reduce("", (a, b) -> a + b + " "));
    }
    public void log(final String s) {
        System.out.println(s);
    }
}
