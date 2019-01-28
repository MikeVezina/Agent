package reactive.action;

import krislet.SendCommand;


public class DoubleActionHandler extends AbstractActionHandler<Double> {

    public DoubleActionHandler(SendCommand sendCommand, String action) {
        super(sendCommand, action);
    }

    protected Double[] parseArguments(String[] args) {
        Double[] doubleArgs = new Double[args.length];

        for (int i = 0, argsLength = args.length; i < argsLength; i++) {
            String arg = args[i];
            doubleArgs[i] = Double.parseDouble(arg);
        }

        return doubleArgs;
    }
}
