package com.umlimi.robotapocalypse.errorhandling;

/**
 * @author MUNASHE MURIMI
 * @created 19/1/2023
 **/
public class SurvivorNotFoundException extends RuntimeException{
    public SurvivorNotFoundException(String message){
        super(message);
    }
}
