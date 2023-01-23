package com.umlimi.robotapocalypse.infectionrecord.impl;

import com.umlimi.robotapocalypse.InfectionRecord;
import com.umlimi.robotapocalypse.InfectionRecordRepository;
import com.umlimi.robotapocalypse.Survivor;
import com.umlimi.robotapocalypse.SurvivorRepository;
import com.umlimi.robotapocalypse.enums.InfectionStatus;
import com.umlimi.robotapocalypse.errorhandling.SurvivorNotFoundException;
import com.umlimi.robotapocalypse.infectionrecord.InfectionRecordDto;
import com.umlimi.robotapocalypse.infectionrecord.InfectionRecordRequest;
import com.umlimi.robotapocalypse.infectionrecord.InfectionRecordService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

/**
 * @author MUNASHE MURIMI
 * @created 23/1/2023
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class InfectionRecordServiceImpl implements InfectionRecordService {
    private final ModelMapper modelMapper;
    private final SurvivorRepository survivorRepository;
    private final InfectionRecordRepository infectionRecordRepository;

    @Override
    public InfectionRecordDto reportInfection(InfectionRecordRequest infectionRecordRequest) {
        requireNonNull(infectionRecordRequest, "InfectionRecordRequest cannot be null");
        log.info("Reporting survivor infection, infectionRecordRequest = {}", infectionRecordRequest);
        Survivor survivorReported = survivorRepository.findById(infectionRecordRequest.getSurvivorReportedId())
                .orElseThrow(()-> new SurvivorNotFoundException(String.format("Survivor being reported with id %s " +
                        "not " + "found", infectionRecordRequest.getSurvivorReportedId())));
        Survivor reportedBy = survivorRepository.findById(infectionRecordRequest.getReportedById())
                .orElseThrow(()-> new SurvivorNotFoundException(String.format("Survivor reporting infection with " +
                                "id %s not found", infectionRecordRequest.getReportedById())));

        InfectionRecord infectionRecord = InfectionRecord.builder()
                .dateReported(LocalDateTime.now())
                .reportedBy(reportedBy.getFirstName().concat(reportedBy.getLastName()))
                .survivorReported(survivorReported.getFirstName().concat(survivorReported.getLastName()))
                .survivorReportedId(survivorReported.getId())
                .reportedById(reportedBy.getId())
                .build();

        survivorReported.setInfectionReportTracker(survivorReported.getInfectionReportTracker() + 1);

        if(survivorReported.getInfectionReportTracker() == 3){
            survivorReported.setInfectionStatus(InfectionStatus.INFECTED);
        }

        survivorRepository.save(survivorReported);

        return convertInfectionRecordDto(infectionRecordRepository.save(infectionRecord));
    }

    private InfectionRecordDto convertInfectionRecordDto(InfectionRecord infectionRecord){
        InfectionRecordDto infectionRecordDto = modelMapper.map(infectionRecord, InfectionRecordDto.class);
        return infectionRecordDto;
    }
}
