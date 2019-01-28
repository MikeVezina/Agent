package states;

import krislet.Krislet;
import krislet.SendCommand;
import object.BallInfo;

public abstract class BrainState {

    protected SendCommand sendCommand;

    public BrainState() {
        this.sendCommand = Krislet.getInstance();
    }

    public abstract BrainState ballFound(BallInfo ballInfo);
    public abstract BrainState facingBall(BallInfo ballInfo);
    public abstract BrainState nearBall(BallInfo ballInfo);
    public abstract BrainState ballRetrieved(BallInfo ballInfo);

    public BrainState ballNotFound() {
        sendCommand.turn(40);
        return new RetrievingBallState();
    }

    public BrainState gamePaused() {
        return new WaitingForGamePlayState();
    }

    public BrainState gamePlaying() {
        return this;
    }
}
