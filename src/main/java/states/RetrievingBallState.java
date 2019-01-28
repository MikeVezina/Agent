package states;

import object.BallInfo;

public class RetrievingBallState extends BrainState {

    @Override
    public BrainState ballFound(BallInfo ballInfo) {
        sendCommand.turn(ballInfo.m_direction);
        return this;
    }

    @Override
    public BrainState facingBall(BallInfo ballInfo) {
        System.out.println("Running for the ball!");
        sendCommand.dash(5 * ballInfo.m_distance);
        return this;
    }

    @Override
    public BrainState nearBall(BallInfo ballInfo) {
        sendCommand.turn(ballInfo.m_direction);
        return new RetrievedBallState();
    }

    @Override
    public BrainState ballRetrieved(BallInfo ballInfo) {
        // We will not perform any action while exiting this state
        return new RetrievedBallState();
    }

}
