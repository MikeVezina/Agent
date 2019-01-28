package krislet;//
//	File:			SoccerInterfaces.java
//	Author:		Krzysztof Langner
//	Date:			1997/04/28
//

//      Modified by:     Edgar Acosta
//      Date:            March 5, 2008
//      Added the bye command


import object.VisualInfo;


public interface SensorInput
{
    //---------------------------------------------------------------------------
    // This function sends see information
    public void see(VisualInfo info);

    //---------------------------------------------------------------------------
    // This function receives hear information from player
    public void hear(int time, int direction, String message);

    //---------------------------------------------------------------------------
    // This function receives hear information from referee
    public void hear(int time, String message);
}
