package com.umlimi.robotapocalypse.errorhandling;

/**
 * @author MUNASHE MURIMI
 * @created 19/1/2023
 **/
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message){
        super(message);
    }
}
