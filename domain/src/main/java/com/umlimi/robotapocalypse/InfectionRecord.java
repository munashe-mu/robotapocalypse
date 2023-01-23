package com.umlimi.robotapocalypse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Infection_Record")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfectionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String reportedBy;

    private Long reportedById;

    private String survivorReported;

    private Long survivorReportedId;

    private LocalDateTime dateReported;
}
