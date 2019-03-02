package contingency;

import aima.core.agent.Action;
import aima.core.search.nondeterministic.ResultsFunction;
import contingency.environment.BallState;
import contingency.environment.EnvironmentState;

import java.util.HashSet;
import java.util.Set;

public class AgentResultsFunction implements ResultsFunction {


    /**
     * This function provides the resulting "AND" states from performing a given action a in state s.
     * <p>
     * Please NOTE: this function does not state any cyclic dependencies, even though they exist.
     * Cyclic behaviour will be handled outside the search functionality.
     *
     * @param s The state being examined
     * @param a The action being examined
     * @return (AND branch) A set of all possible states from the given state and action in an OR branch
     */
    @Override
    public Set results(Object s, Action a) {

        Set<EnvironmentState> results = new HashSet<>();
        BallState state = ((EnvironmentState) s).getBallState();
        AgentAction action = (AgentAction) a;

        /* This case can also result in cycle behaviour */
        // If we don't see the ball and we turn clockwise, we either see the ball (PRESENT) or we don't (NOT_PRESENT, CYCLIC)
        if (state == BallState.NOT_PRESENT && action == AgentActionsFunction.ACTION_TURN_CLOCKWISE) {
            results.add(new EnvironmentState(BallState.PRESENT));
            results.add(new EnvironmentState(BallState.FACING));
            return results;
        }


        // If we see the ball and we turn to the ball, we will be facing the ball (but not near it)
        if (state == BallState.PRESENT && action == AgentActionsFunction.ACTION_TURN_TO_BALL) {
            results.add(new EnvironmentState(BallState.FACING));
            return results;
        }

        // If we see the ball and we dash towards it, we will be near the ball (but not facing it directly) OR it will not be near us (PRESENT, cyclic)
        if (state == BallState.PRESENT && action == AgentActionsFunction.ACTION_DASH) {
            results.add(new EnvironmentState(BallState.NEAR));
            return results;
        }


        // If we are near the ball and we rotate towards it, we will obtain the ball (GOAL)
        if (state == BallState.NEAR && action == AgentActionsFunction.ACTION_TURN_TO_BALL) {
            results.add(new EnvironmentState(BallState.OBTAINED));
            return results;
        }

        // If we are facing the ball and we dash towards it, we will either obtain the ball (GOAL) OR it we have not yet reached the ball (FACING, cyclic)
        if (state == BallState.FACING && action == AgentActionsFunction.ACTION_DASH) {
            results.add(new EnvironmentState(BallState.OBTAINED));
            return results;
        }


        throw new RuntimeException("A State/Action case was not specified. State: " + state + ", Action: " + action);
    }
}
