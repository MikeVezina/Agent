package krislet;

//***************************************************************************
//
//	This interface declares functions which are used to send
//	command to player
//
//***************************************************************************
public interface SendCommand {
    // This function sends move command to the server
    void move(double x, double y);

    // This function sends turn command to the server
    void turn(double moment);

    void turn_neck(double moment);

    // This function sends dash command to the server
    void dash(double power);

    // This function sends kick command to the server
    void kick(double power, double direction);

    // This function sends say command to the server
    void say(String message);

    // This function sends chage_view command to the server
    void changeView(String angle, String quality);

    // This function sends a bye command to the server
    void bye();
}


/**

 Ball == null                                   ====> turn(40) && wait()
 Ball && distance > 1.0 && direction != 0       ====> turn(10)
 Ball && distance > 1.0 && direction == 0       ====> dash(10)
 Ball && distance <= 1.0                        ====> kick(100, 10)





 Ball -> {null} u (distance, direction)



 */