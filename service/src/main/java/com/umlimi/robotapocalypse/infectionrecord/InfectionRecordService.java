package com.umlimi.robotapocalypse.infectionrecord;

/**
 * @author MUNASHE MURIMI
 * @created 23/1/2023
 **/
public interface InfectionRecordService {
    /**
     * This method report infection of survivor using InfectionRecordRequest
     * @param infectionRecordRequest
     * @return InfectionRecordDto
     */
    InfectionRecordDto reportInfection(InfectionRecordRequest infectionRecordRequest);
}
