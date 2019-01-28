package reactive.action;

import krislet.SendCommand;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractActionHandler<T> {
    private SendCommand sendCommand;
    private int numArguments;
    private Method actionMethod;
    private String actionName;

    public AbstractActionHandler(SendCommand sendCommand, String action) {
        this.sendCommand = sendCommand;
        this.actionName = action;

        try {
            this.actionMethod = FindSendCommandMethod(action);
            this.numArguments = actionMethod.getParameterCount();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new NullPointerException("Failed to find the corresponding action method");
        }
    }

    public void handleAction(String[] args) {
        if (args.length != numArguments) {
            System.out.println("Invalid number of arguments. Expected: " + numArguments + " - Actual: " + args.length);
            return;
        }

        try {
            T[] parsedArgs = this.parseArguments(args);
            this.actionMethod.invoke(sendCommand, (Object[]) parsedArgs);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private static Method FindSendCommandMethod(String action) throws NoSuchMethodException {
        for (Method m : SendCommand.class.getDeclaredMethods()) {
            if (m.getName().equals(action))
                return m;
        }

        throw new NoSuchMethodException();
    }

    protected abstract T[] parseArguments(String[] args);

    public String getActionName() {
        return actionName;
    }
}
