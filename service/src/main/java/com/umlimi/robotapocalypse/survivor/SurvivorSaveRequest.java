package com.umlimi.robotapocalypse.survivor;

import com.umlimi.robotapocalypse.embeddables.Location;
import lombok.Data;

/**
 * @author MUNASHE MURIMI
 * @created 19/1/2023
 **/

@Data
public class SurvivorSaveRequest {
    private String firstName;
    private  String lastName;
    private String gender;
    private Location location;
    private int age;
}
