package com.umlimi.robotapocalypse.robot;

import javax.naming.CommunicationException;

/**
 * @author MUNASHE MURIMI
 * @created 19/01/2023
 **/
public interface RobotService {
    /**
     * retrieves a list of robots
     * @return
     */
    RobotDto listAllRobots() throws CommunicationException;
}
