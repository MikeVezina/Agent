package contingency.environment;

public class EnvironmentState {

    private BallState ballState;

    public EnvironmentState(BallState ballState)
    {
        this.ballState = ballState;
    }

    public BallState getBallState() {
        return ballState;
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof EnvironmentState))
            return false;

        if(o == this)
            return true;

        EnvironmentState state = (EnvironmentState) o;

        return this.ballState.equals(state.ballState);
    }

    @Override
    public String toString() {
        return "Environment State: [" + ballState + "]";
    }


}

