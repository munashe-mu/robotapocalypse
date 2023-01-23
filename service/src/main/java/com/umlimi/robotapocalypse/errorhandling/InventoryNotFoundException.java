package com.umlimi.robotapocalypse.errorhandling;

/**
 * @author MUNASHE MURIMI
 * @created 19/1/2023
 **/
public class InventoryNotFoundException extends RuntimeException{
    public InventoryNotFoundException(String message){
        super(message);
    }
}
