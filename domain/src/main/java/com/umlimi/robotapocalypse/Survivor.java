package com.umlimi.robotapocalypse;

import com.umlimi.robotapocalypse.embeddables.Location;
import com.umlimi.robotapocalypse.enums.Gender;
import com.umlimi.robotapocalypse.enums.InfectionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author MUNASHE MURIMI
 * @created 19/01/2023
 */

@Entity
@Table(name = "Survivor")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Survivor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private int age;

    private LocalDate dateCreated;

    @Embedded
    private Location location;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private InfectionStatus infectionStatus;

    private Integer infectionReportTracker;
}
