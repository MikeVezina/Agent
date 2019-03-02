package strategy;

import aima.core.search.nondeterministic.AndOrSearch;
import aima.core.search.nondeterministic.IfStateThenPlan;
import aima.core.search.nondeterministic.NondeterministicProblem;
import aima.core.search.nondeterministic.Plan;
import contingency.environment.BallState;
import contingency.AgentAction;
import contingency.AgentActionsFunction;
import contingency.AgentResultsFunction;
import object.BallInfo;
import contingency.environment.EnvironmentState;
import krislet.SendCommand;


public class ContingencyBrain extends Brain {

    private Plan currentPlan;
    private EnvironmentState previousState;
    private AndOrSearch andOrSearch;
    private AgentActionsFunction agentActionsFunction;
    private AgentResultsFunction agentResultsFunction;

    public ContingencyBrain(SendCommand krislet, String team, char side, int number, String playMode) {
        super(krislet, team, side, number, playMode);
        this.andOrSearch = new AndOrSearch();

        agentActionsFunction = new AgentActionsFunction();
        agentResultsFunction = new AgentResultsFunction();

        super.start();
    }

    private void generateNewContingencyPlan() {
        NondeterministicProblem ndp = new NondeterministicProblem(super.getEnvironmentState(), agentActionsFunction, agentResultsFunction, ContingencyBrain::goalTest);
        currentPlan = andOrSearch.search(ndp);
        System.out.println("Generated contingency plan: " + currentPlan);
        previousState = null;
    }


    private static boolean goalTest(Object o) {
        return ((EnvironmentState) o).getBallState() == BallState.OBTAINED;
    }

    @Override
    protected void receivedState(EnvironmentState environmentState) {
        if (currentPlan == null || currentPlan.isEmpty()) {
            generateNewContingencyPlan();
        }

        executePlan();
    }

    protected void executePlan() {
        if (currentPlan == null || currentPlan.isEmpty())
            return;

        Object nextObj = currentPlan.pop();
        EnvironmentState currentState = getEnvironmentState();

        if (nextObj instanceof AgentAction) {

            // Execute the action if it is the first time we are executing the action, or if the state hasn't changed
            if (previousState == null || previousState.equals(currentState)) {
                AgentAction nextAction = (AgentAction) nextObj;
                nextAction.run(m_krislet, getBallInfo());
                previousState = currentState;

                // This allows us to perform cyclic behaviour. currentPlan.addFirst allows us to repeat the action until we advance states.
                currentPlan.addFirst(nextObj);
                return;
            }

            previousState = currentState;
        }
        else if (nextObj instanceof IfStateThenPlan) {
            IfStateThenPlan ifStateThenPlan = (IfStateThenPlan) nextObj;
            Plan nextPlan = ifStateThenPlan.ifStateMatches(currentState);

            // If the state matches, i.e. nextPlan is not null, then we should use that plan.
            if (nextPlan != null)
                currentPlan = nextPlan;
        }
        else if (nextObj instanceof Plan) {
            currentPlan = (Plan) nextObj;
        }
        executePlan();
    }

    protected BallInfo getBallInfo() {
        Object ballObj = m_memory.getObject("ball");
        if (!(ballObj instanceof BallInfo))
            return null;

        return (BallInfo) ballObj;
    }

}
