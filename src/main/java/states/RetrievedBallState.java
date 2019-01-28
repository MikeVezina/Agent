package states;

import object.BallInfo;

public class RetrievedBallState extends BrainState {


    public RetrievedBallState() {
    }

    @Override
    public BrainState ballFound(BallInfo ballInfo) {
        sendCommand.turn(ballInfo.m_direction);
        return new RetrievingBallState();
    }

    @Override
    public BrainState facingBall(BallInfo ballInfo) {
        sendCommand.dash(10 * ballInfo.m_distance);
        return new RetrievingBallState();
    }

    @Override
    public BrainState nearBall(BallInfo ballInfo) {
        sendCommand.turn(ballInfo.m_direction);
        return this;
    }

    @Override
    public BrainState ballRetrieved(BallInfo ballInfo) {
        sendCommand.kick(100, ballInfo.m_direction);
        return new RetrievingBallState();
    }
}