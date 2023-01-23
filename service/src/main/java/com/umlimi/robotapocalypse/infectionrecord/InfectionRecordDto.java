package com.umlimi.robotapocalypse.infectionrecord;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MUNASHE MURIMI
 * @created 23/1/2023
 **/
@Data
public class InfectionRecordDto {
    private Long id;
    private String reportedBy;
    private String survivorReported;
    private Long reportedById;
    private Long survivorReportedId;
    private LocalDateTime dateReported;
}
