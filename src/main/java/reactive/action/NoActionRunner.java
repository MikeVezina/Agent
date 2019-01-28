package reactive.action;

import krislet.Memory;

public class NoActionRunner extends ActionRunner {
    public NoActionRunner(ActionHandlers handlers, Memory memory) {
        super(handlers, memory, "None", null);
    }

    @Override
    public void runAction() {
        // Do nothing
    }

    @Override
    public String toString() {
        return "[No Action]";
    }
}
