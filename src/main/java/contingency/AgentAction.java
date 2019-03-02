package contingency;

import aima.core.agent.impl.DynamicAction;
import krislet.SendCommand;
import object.BallInfo;

/**
 * An Action class that is compatible with R & N AndOrSearch
 */
public class AgentAction extends DynamicAction {

    private ActionEnum actionEnum;

    public AgentAction(ActionEnum actionEnum) {
        super(actionEnum.name());
        this.actionEnum = actionEnum;
    }

    public ActionEnum getActionEnum() {
        return actionEnum;
    }

    public void run(SendCommand krislet, BallInfo ballInfo) {
        actionEnum.execute(krislet, ballInfo);
    }

    @Override
    public String toString() {
        return "Agent Action: " + super.getName();
    }
}
