package com.umlimi.robotapocalypse.survivor.impl;

import com.umlimi.robotapocalypse.Survivor;
import com.umlimi.robotapocalypse.SurvivorRepository;
import com.umlimi.robotapocalypse.embeddables.Location;
import com.umlimi.robotapocalypse.enums.Gender;
import com.umlimi.robotapocalypse.enums.InfectionStatus;
import com.umlimi.robotapocalypse.errorhandling.InvalidRequestException;
import com.umlimi.robotapocalypse.errorhandling.SurvivorNotFoundException;
import com.umlimi.robotapocalypse.survivor.SurvivorDto;
import com.umlimi.robotapocalypse.survivor.SurvivorSaveRequest;
import com.umlimi.robotapocalypse.survivor.SurvivorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author MUNASHE MURIMI
 * @created 19/1/2023
 **/

@Service
@RequiredArgsConstructor
@Slf4j
public class SurvivorServiceImpl implements SurvivorService {
    private final SurvivorRepository survivorRepository;
    private final ModelMapper modelMapper;

    @Override
    public SurvivorDto saveSurvivor(SurvivorSaveRequest survivorSaveRequest) {
        if(Objects.isNull(survivorSaveRequest))
            throw new InvalidRequestException("Survivor request can not be null");
        if(survivorSaveRequest.getAge() <= 0)
            throw new InvalidRequestException(String.format("Survivor age %s cannot be negative or equal to zero",
                    survivorSaveRequest.getAge()));

        log.info("Adding survivor, survivorSaveRequest = {}", survivorSaveRequest);
        Survivor survivor = Survivor.builder()
                .age(survivorSaveRequest.getAge())
                .dateCreated(LocalDate.now())
                .firstName(survivorSaveRequest.getFirstName())
                .gender(Gender.valueOf(survivorSaveRequest.getGender().toUpperCase()))
                .infectionStatus(InfectionStatus.NOT_INFECTED)
                .location(survivorSaveRequest.getLocation())
                .infectionReportTracker(Integer.valueOf(0))
                .lastName(survivorSaveRequest.getLastName())
                .build();
        Survivor savedSurvivor = survivorRepository.save(survivor);
        return convertSurvivorToDto(savedSurvivor);
    }

    @Override
    public SurvivorDto updateLocation(Long survivorId, Location location) {
        Survivor survivor = survivorRepository.findById(survivorId).orElseThrow(
                () -> new SurvivorNotFoundException(String.format("Survivor with id %s not found", survivorId))
        );
        survivor.setLocation(location);
        Survivor savedSurvivor = survivorRepository.save(survivor);
        return convertSurvivorToDto(savedSurvivor);
    }

    @Override
    public List<SurvivorDto> getSurvivorsByInfectionStatus(InfectionStatus infectionStatus) {
        List<Survivor> survivors = survivorRepository.getSurvivorsByInfectionStatus(infectionStatus)
                .orElseThrow(() -> new SurvivorNotFoundException(String.format(
                        "Survivor with infection status %s not found", infectionStatus)));
                return survivors.stream().map(survivor -> convertSurvivorToDto(survivor))
                        .collect(Collectors.toList());
    }

    @Override
    public Double getInfectedOrNonInfectedPercentage(InfectionStatus infectionStatus) {
        Long countByInfectionStatus = survivorRepository.countByInfectionStatus(infectionStatus);

        Long allSurvivorsCount = survivorRepository.count();

        return Double.valueOf(countByInfectionStatus / allSurvivorsCount * 100);
    }

    private SurvivorDto convertSurvivorToDto(Survivor survivor){
        SurvivorDto survivorDto =  modelMapper.map(survivor, SurvivorDto.class);
        return survivorDto;
    }

}
