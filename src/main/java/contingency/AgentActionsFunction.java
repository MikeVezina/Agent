package contingency;

import aima.core.search.framework.problem.ActionsFunction;
import contingency.environment.BallState;
import contingency.environment.EnvironmentState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class AgentActionsFunction implements ActionsFunction {

    private HashMap<BallState, Set<AgentAction>> actionMap;

    public static final AgentAction ACTION_TURN_CLOCKWISE = new AgentAction(ActionEnum.TURN_CLOCKWISE);
    public static final AgentAction ACTION_DASH = new AgentAction(ActionEnum.DASH);
    public static final AgentAction ACTION_TURN_TO_BALL = new AgentAction(ActionEnum.TURN_TO_BALL);


    public AgentActionsFunction() {
        actionMap = new HashMap<>();

        addAction(BallState.NOT_PRESENT, ACTION_TURN_CLOCKWISE);
        addAction(BallState.PRESENT, ACTION_TURN_TO_BALL, ACTION_DASH);
        addAction(BallState.NEAR, ACTION_TURN_TO_BALL);
        addAction(BallState.FACING, ACTION_DASH);
    }

    private void addAction(BallState ballState, AgentAction... agentActions) {
        if (ballState == null || agentActions == null) {
            throw new NullPointerException("Invalid arguments");
        }

        Set<AgentAction> actionSet = new HashSet<>(Arrays.asList(agentActions));
        actionMap.put(ballState, actionSet);
    }

    @Override
    public Set actions(Object o) {

        if (!(o instanceof EnvironmentState))
            throw new RuntimeException("The current state is not an instance of EnvironmentState");

        EnvironmentState state = (EnvironmentState) o;

        Set<AgentAction> actionSet = actionMap.get(state.getBallState());


        return actionSet;
    }
}
