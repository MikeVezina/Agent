package strategy;//
//	File:			strategy.Brain.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//
//    Modified by:	Paul Marlow

//    Modified by:      Edgar Acosta
//    Date:             March 4, 2008

import environment.EnvironmentState;
import krislet.Memory;
import krislet.SendCommand;
import krislet.SensorInput;
import krislet.SoccerParams;
import object.BallInfo;
import object.ObjectInfo;
import object.VisualInfo;

import java.lang.Math;
import java.util.regex.*;

import static environment.EnvironmentState.GAME_PAUSED;
import static environment.EnvironmentState.GAME_PLAYING;

public abstract class Brain extends Thread implements SensorInput {


    protected SendCommand m_krislet;
    protected Memory m_memory;
    protected char m_side;
    volatile protected boolean m_timeOver;
    protected String m_playMode;

    public Brain(SendCommand krislet, String team, char side, int number, String playMode) {
        m_timeOver = false;
        m_krislet = krislet;
        m_memory = new Memory();
        //m_team = team;
        m_side = side;
        // m_number = number;
        m_playMode = playMode;
    }

    protected abstract void receivedState(EnvironmentState environmentState);

    private EnvironmentState getEnvironmentState() {
        ObjectInfo ballObj = m_memory.getObject("ball");

        if (!(ballObj instanceof BallInfo))
            return EnvironmentState.BALL_NOT_FOUND;

        BallInfo ballInfo = (BallInfo) ballObj;

        boolean nearBall = ballInfo.m_distance <= 1.0;
        boolean facingBall = ballInfo.m_direction == 0;

        if (nearBall && facingBall)
            return EnvironmentState.BALL_RETRIEVED;

        if (nearBall)
            return EnvironmentState.NEAR_BALL;

        if (facingBall)
            return EnvironmentState.FACING_BALL;

        return EnvironmentState.BALL_FOUND;
    }

    public void run() {
        // first put it somewhere on my side
        if (Pattern.matches("^before_kick_off.*", m_playMode))
            m_krislet.move(-Math.random() * 52.5, 34 - Math.random() * 68.0);

        while (!m_timeOver) {

            EnvironmentState currentState = getEnvironmentState();

            receivedState(currentState);

            // sleep one receivedState to ensure that we will not send
            // two commands in one cycle.
            sleep();
        }
        m_krislet.bye();
    }

    protected void sleep() {
        try {
            Thread.sleep(2 * SoccerParams.simulator_step);
        } catch (Exception e) {
        }
    }


    //---------------------------------------------------------------------------
    // This is main brain function used to make decision
    // In each cycle we decide which command to issue based on
    // current situation. the rules are:
    //
    //	1. If you don't know where is ball then turn right and wait for new info
    //
    //	2. If ball is too far to kick it then
    //		2.1. If we are directed towards the ball then go to the ball
    //		2.2. else turn to the ball
    //
    //	3. If we dont know where is opponent goal then turn wait 
    //				and wait for new info
    //
    //	4. Kick ball
    //
    //	To ensure that we don't send commands to often after each cycle
    //	we waits one simulator steps. (This of course should be done better)

    // ***************  Improvements ******************
    // Allways know where the goal is.
    // Move to a place on my side on a kick_off
    // ************************************************


    //===========================================================================
    // Here are suporting functions for implement logic


    //===========================================================================
    // Implementation of krislet.SensorInput Interface

    //---------------------------------------------------------------------------
    // This function sends see information
    public void see(VisualInfo info) {
        m_memory.store(info);
    }


    //---------------------------------------------------------------------------
    // This function receives hear information from player
    public void hear(int time, int direction, String message) {
    }

    //---------------------------------------------------------------------------
    // This function receives hear information from referee
    public void hear(int time, String message) {
        // send game start event when the play mode is not before kick off
        if (isPlaying(message))
            receivedState(GAME_PLAYING);
        else {
            receivedState(GAME_PAUSED);
            if (message.compareTo("time_over") == 0)
                m_timeOver = true;
        }
    }

    protected boolean isPlaying(String playMode) {
        return playMode.contains("play_on") || playMode.contains("drop_ball") || (playMode.contains("_" + m_side) && !playMode.contains("fault"));
    }


}
