package com.umlimi.robotapocalypse.survivor;

import com.umlimi.robotapocalypse.embeddables.Location;
import com.umlimi.robotapocalypse.enums.Gender;
import com.umlimi.robotapocalypse.enums.InfectionStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Romeo Jerenyama
 * @created 19/1/2023
 */

@Data
public class SurvivorDto {
    private Long id;
    private String firstName;
    private  String lastName;
    private Gender gender;
    private InfectionStatus infectionStatus;
    private Location location;
    private int age;
    private int infectionReportTracker;
    private LocalDateTime dateCreated;
}
