package strategy;

import environment.EnvironmentState;
import krislet.SendCommand;
import object.BallInfo;
import states.BrainState;
import states.*;

public class StateBrain extends Brain {

    private BrainState currentState;

    public StateBrain(SendCommand krislet, String team, char side, int number, String playMode) {
        super(krislet, team, side, number, playMode);
        currentState = new WaitingForGamePlayState();

        if (isPlaying(playMode))
            receivedState(EnvironmentState.GAME_PLAYING);

        super.start();
    }

    public void receivedState(EnvironmentState environmentState) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(Current State: ").append(currentState.getClass().getSimpleName()).append(", Env. State: ");
        stringBuilder.append(environmentState.name()).append(") -> ");

        currentState = getNextState(environmentState);

        stringBuilder.append("New State: ");
        stringBuilder.append(currentState.getClass().getSimpleName());

        System.out.println(stringBuilder.toString());
    }

    public BrainState getNextState(EnvironmentState environmentState) {

        switch (environmentState) {
            case GAME_PAUSED:
                return currentState.gamePaused();
            case GAME_PLAYING:
                return currentState.gamePlaying();
            case BALL_NOT_FOUND:
                return currentState.ballNotFound();
        }

        BallInfo ballObj = (BallInfo) m_memory.getObject("ball");

        switch (environmentState) {
            case BALL_FOUND:
                return currentState.ballFound(ballObj);
            case FACING_BALL:
                return currentState.facingBall(ballObj);
            case NEAR_BALL:
                return currentState.nearBall(ballObj);
            case BALL_RETRIEVED:
                return currentState.ballRetrieved(ballObj);
        }

        System.out.println("No action was performed. Returning the current state");
        return currentState;
    }

}
