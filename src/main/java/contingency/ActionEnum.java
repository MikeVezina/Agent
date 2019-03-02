package contingency;

import krislet.SendCommand;
import object.BallInfo;

public enum ActionEnum {
    TURN_CLOCKWISE((k, b) -> k.turn(45)),
    TURN_TO_BALL((k, b) -> k.turn(b.m_direction)),
    DASH((k, b) -> k.dash(100));

    private ActionRunner actionRunner;

    ActionEnum(ActionRunner actionRunner)
    {
        this.actionRunner = actionRunner;
    }
    public void execute(SendCommand sendCommand, BallInfo ballInfo)
    {
        this.actionRunner.run(sendCommand, ballInfo);
    }
}
