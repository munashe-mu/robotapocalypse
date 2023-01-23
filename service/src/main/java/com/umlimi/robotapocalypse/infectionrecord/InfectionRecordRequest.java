package com.umlimi.robotapocalypse.infectionrecord;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MUNASHE MURIMI
 * @created 23/1/2023
 **/
@Data
public class InfectionRecordRequest {
    private Long reportedById;
    private Long survivorReportedId;
    private LocalDateTime dateReported;
}
