package reactive.action;

import krislet.SendCommand;


public class StringActionHandler extends AbstractActionHandler<String> {
    public StringActionHandler(SendCommand sendCommand, String action) {
        super(sendCommand, action);
    }

    protected String[] parseArguments(String[] args) {
        return args;
    }

}
