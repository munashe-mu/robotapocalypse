package com.umlimi.robotapocalypse;

import com.umlimi.robotapocalypse.enums.InfectionStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SurvivorRepository extends BaseRepository<Survivor>{
    Optional<Survivor> findById(Long id);
    Optional<List<Survivor>> getSurvivorsByInfectionStatus(InfectionStatus infectionStatus);
    Long countByInfectionStatus(InfectionStatus infectionStatus);
}
