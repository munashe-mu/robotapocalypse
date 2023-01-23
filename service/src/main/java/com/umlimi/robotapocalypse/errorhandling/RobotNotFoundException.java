package com.umlimi.robotapocalypse.errorhandling;

/**
 * @author MUNASHE MURIMI
 * @created 19/1/2023
 **/
public class RobotNotFoundException extends RuntimeException{
    public RobotNotFoundException(String message){
        super(message);
    }
}
