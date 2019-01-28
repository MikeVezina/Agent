package states;

import krislet.SendCommand;
import object.BallInfo;

public class WaitingForGamePlayState extends BrainState {

    @Override
    public BrainState ballFound(BallInfo ballInfo) {
        return spinUntilGameStart();
    }

    @Override
    public BrainState facingBall(BallInfo ballInfo) {
        return spinUntilGameStart();
    }

    @Override
    public BrainState nearBall(BallInfo ballInfo) {
        return spinUntilGameStart();
    }

    @Override
    public BrainState ballRetrieved(BallInfo ballInfo) {
        return spinUntilGameStart();
    }

    @Override
    public BrainState ballNotFound() {
        return spinUntilGameStart();
    }

    @Override
    public BrainState gamePaused() {
        return spinUntilGameStart();
    }

    @Override
    public BrainState gamePlaying() {
        return new RetrievingBallState();
    }

    private BrainState spinUntilGameStart() {
        System.out.println("Spinning until the game starts. I'm already feeling a bit dizzy!");
        sendCommand.turn(90);
        return this;
    }


}
