package contingency;

import krislet.SendCommand;
import object.BallInfo;

/**
 * An interface that provides the ability to run a Krislet command (with ballInfo)
 */
public interface ActionRunner {
    void run(SendCommand sendCommand, BallInfo ballInfo);
}
