package view.main;

import model.manager.EngineModelInterface.Calculator;

/**
 *
 */
public class ViewLogicsImpl implements ViewLogics {

    private final View frame;
    /**
     * @param frame
     */
    public ViewLogicsImpl(final View frame) {
        this.frame = frame;
    }

    @Override
    public void mount(final Calculator calc) {
        this.frame.show(calc);
    }

}
