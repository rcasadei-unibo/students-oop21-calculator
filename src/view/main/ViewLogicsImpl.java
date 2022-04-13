package view.main;

import model.manager.EngineModelInterface.Calculator;

import java.util.List;

import controller.manager.CCManager;
import controller.manager.ManagerInterface;

/**
 * Logics for the main View component of the application.
 * When a calculator is selected, it communicates to the manager to mount the calculator and displays it on the main GUI.
 */
public class ViewLogicsImpl implements ViewLogics {

    private final View frame;
    private final ManagerInterface mng = new CCManager();

    /**
     * Constructs the logics for the main View. 
     * @param frame Main View component of the application.
     */
    public ViewLogicsImpl(final View frame) {
        this.frame = frame;
    }

    @Override
    public void mount(final Calculator calc) {
        this.frame.show(calc);
        this.mng.engine().mount(calc);
    }

    @Override
    public List<String> getHistory() {
        return this.mng.memory().getHistory();
    }

}
