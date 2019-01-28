package strategy;

import reactive.Parser;
import reactive.action.ActionRunner;
import environment.EnvironmentState;
import krislet.SendCommand;


public class ReactiveBrain extends Brain {

    private Parser parser;
    private ActionRunner defaultActionRunner;

    public ReactiveBrain(SendCommand krislet, String team, char side, int number, String playMode) {
        super(krislet, team, side, number, playMode);
        parser = new Parser(m_memory, m_krislet);
        this.defaultActionRunner = parser.getDefaultActionRunner();
        super.start();
    }

    @Override
    protected void receivedState(EnvironmentState environmentState) {
        if (parser == null)
            return;

        parser.getActionRunner(environmentState);

        ActionRunner actionRunner = parser.getActionRunner(environmentState);

        if (actionRunner != null)
            System.out.println("Matched [" + environmentState + "]: " + actionRunner);
        else {
            System.out.println("Not Matched [" + environmentState + "]: defaultAction -> " + defaultActionRunner);
            actionRunner = defaultActionRunner;
        }

        actionRunner.runAction();
    }

}
