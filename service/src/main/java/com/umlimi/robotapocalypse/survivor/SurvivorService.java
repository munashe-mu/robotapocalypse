package com.umlimi.robotapocalypse.survivor;

import com.umlimi.robotapocalypse.embeddables.Location;
import com.umlimi.robotapocalypse.enums.InfectionStatus;

import java.util.List;

/**
 * @author MUNASHE MURIMI
 * @created 19/1/2023
 **/
public interface SurvivorService {
    /**
     * This method creates/saves Survivor from SurvivorSaveRequest
     * @param survivorSaveRequest
     * @return SurvivorDto
     */
    SurvivorDto saveSurvivor(SurvivorSaveRequest survivorSaveRequest);
    /**
     * This method updates the Survivor Location
     * @param survivorId unique identifier of Survivor
     * @param location location to update the Survivor with
     * @return SurvivorDto
     */
    SurvivorDto updateLocation(Long survivorId, Location location);
    /**
     * This method fetches survivors by infection status which can
     * either be INFECTED or NON_INFECTED
     * @param infectionStatus
     * @return List<SurvivorDto>
     */
    List<SurvivorDto> getSurvivorsByInfectionStatus(InfectionStatus infectionStatus);
    /**
     * This method calculates percentage of infected or non_infected survivors
     * @param infectionStatus
     * @return Double
     */
    Double getInfectedOrNonInfectedPercentage(InfectionStatus infectionStatus);
}
