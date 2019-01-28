package reactive.action;

import krislet.Memory;
import object.ObjectInfo;
import org.json.JSONArray;
import org.json.JSONObject;

public class ActionRunner {

    private AbstractActionHandler actionHandler;
    private String[] args;
    private Memory memory;

    public ActionRunner(ActionHandlers handlers, Memory memory, String actionName, String[] args) {
        this.actionHandler = handlers.getActionHandler(actionName);
        this.args = args;
        this.memory = memory;
    }

    private String resolveArg(String arg) {
        if (!arg.contains(".") || actionHandler instanceof StringActionHandler)
            return arg;

        String[] argSplit = arg.split("\\.");
        String objName = argSplit[0];
        String property = argSplit[1];

        ObjectInfo objectInfo = memory.getObject(objName);
        try {
            return String.valueOf(objectInfo.getClass().getField(property).getDouble(objectInfo));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Failed to resolve argument: " + arg);
            e.printStackTrace();
        }

        return arg;
    }

    public void runAction() {
        String[] resolvedArgs = new String[args.length];

        for (int i = 0; i < args.length; i++)
            resolvedArgs[i] = resolveArg(args[i]);

        actionHandler.handleAction(resolvedArgs);
    }

    public static ActionRunner ParseFromJsonObject(ActionHandlers handlers, Memory memory, JSONObject jsonObject) {
        String actionName = jsonObject.getString("action");
        JSONArray actionArgs = jsonObject.getJSONArray("args");

        String[] args = new String[actionArgs.length()];

        for (int i = 0; i < actionArgs.length(); i++) {
            args[i] = actionArgs.getString(i);
        }

        return new ActionRunner(handlers, memory, actionName, args);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(actionHandler.getActionName());

        if (args.length > 0) {
            stringBuilder.append(" => ");
            for (String arg : args) {
                stringBuilder.append(arg).append(" ");
            }
        }

        return stringBuilder.toString();
    }
}
