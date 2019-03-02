package contingency.environment;

public enum GameState {
    PLAYING,
    NOT_PLAYING;

    @Override
    public String toString() {
        return "GameState: " + this.name();
    }
}
