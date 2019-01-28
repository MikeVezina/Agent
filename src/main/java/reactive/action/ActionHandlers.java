package reactive.action;

import krislet.SendCommand;

import java.util.HashMap;

public class ActionHandlers {

    private static final String TURN = "turn";
    private static final String TURN_NECK = "turn_neck";
    private static final String DASH = "dash";
    private static final String KICK = "kick";
    private static final String SAY = "say";

    private HashMap<String, AbstractActionHandler> actionHandlerHashMap;

    public ActionHandlers(SendCommand sendCommand) {
        actionHandlerHashMap = new HashMap<>();

        actionHandlerHashMap.put(TURN, new DoubleActionHandler(sendCommand, TURN));
        actionHandlerHashMap.put(TURN_NECK, new DoubleActionHandler(sendCommand, TURN_NECK));
        actionHandlerHashMap.put(DASH, new DoubleActionHandler(sendCommand, DASH));
        actionHandlerHashMap.put(KICK, new DoubleActionHandler(sendCommand, KICK));

        actionHandlerHashMap.put(SAY, new StringActionHandler(sendCommand, SAY));
    }



    public AbstractActionHandler getActionHandler(String actionName)
    {
        return actionHandlerHashMap.get(actionName);
    }
}
