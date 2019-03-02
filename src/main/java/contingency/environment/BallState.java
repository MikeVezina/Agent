package contingency.environment;

public enum BallState {
    NOT_PRESENT,
    PRESENT,
    NEAR,
    FACING,
    OBTAINED;

    @Override
    public String toString() {
        return "BallState: " + this.name();
    }
}
