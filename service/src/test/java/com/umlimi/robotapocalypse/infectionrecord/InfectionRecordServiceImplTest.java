package com.umlimi.robotapocalypse.infectionrecord;

import com.umlimi.robotapocalypse.InfectionRecord;
import com.umlimi.robotapocalypse.InfectionRecordRepository;
import com.umlimi.robotapocalypse.Survivor;
import com.umlimi.robotapocalypse.SurvivorRepository;
import com.umlimi.robotapocalypse.embeddables.Location;
import com.umlimi.robotapocalypse.errorhandling.SurvivorNotFoundException;
import com.umlimi.robotapocalypse.infectionrecord.impl.InfectionRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author MUNASHE MURIMI
 * @created 23/1/2023
 **/
@ExtendWith(MockitoExtension.class)
public class InfectionRecordServiceImplTest {
    @Mock
    private InfectionRecordRepository infectionRecordRepository;
    @Mock
    private SurvivorRepository survivorRepository;
    @Mock
    private ModelMapper modelMapper;

    private InfectionRecordService underTest;

    @BeforeEach
    void setUp(){
        underTest = new InfectionRecordServiceImpl(modelMapper, survivorRepository, infectionRecordRepository);
    }

    @Test
    void reportInfectionThrowsNullPointerExceptionWhenInfectionRecordRequestIsNull(){
        //GIVEN
        InfectionRecordRequest infectionRecordRequest = null;
        //WHEN
        infectionRecordRequest = null;
        //THEN
        assertThatThrownBy(()-> underTest.reportInfection(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("InfectionRecordRequest cannot be null");
    }

    @Test
    void reportInfectionSurvivorNotFoundExceptionWhenSurvivorIsNotFound(){
        //GIVEN
        InfectionRecordRequest infectionRecordRequest = new InfectionRecordRequest();
        infectionRecordRequest.setReportedById(1L);
        infectionRecordRequest.setSurvivorReportedId(2L);

        given(survivorRepository.findById(infectionRecordRequest.getSurvivorReportedId()))
                .willReturn(Optional.ofNullable(null));
        //WHEN
        //THEN
        assertThatThrownBy( ()-> underTest.reportInfection(infectionRecordRequest))
                .isInstanceOf(SurvivorNotFoundException.class)
                .hasMessageContaining(String.format("Survivor being reported with id %s not found",
                        infectionRecordRequest.getSurvivorReportedId()));
    }

    @Test
    void reportInfectionSurvivorNotFoundExceptionWhenReportingSurvivorIsNotFound(){
        //GIVEN
        InfectionRecordRequest infectionRecordRequest = new InfectionRecordRequest();
        infectionRecordRequest.setReportedById(1L);
        infectionRecordRequest.setSurvivorReportedId(2L);

        Survivor survivorReported = Survivor.builder()
                .id(1L)
                .firstName("Munashe")
                .lastName("Murimi")
                .location(new Location())
                .build();

        given(survivorRepository.findById(infectionRecordRequest.getSurvivorReportedId()))
                .willReturn(Optional.ofNullable(survivorReported));

        given(survivorRepository.findById(infectionRecordRequest.getReportedById()))
                .willReturn(Optional.ofNullable(null));
        //WHEN
        //THEN

        assertThatThrownBy(()-> underTest.reportInfection(infectionRecordRequest))
                .isInstanceOf(SurvivorNotFoundException.class)
                .hasMessageContaining(String.format("Survivor reporting infection with id %s not found",
                        infectionRecordRequest.getReportedById()));
    }

    @Test
    void canSaveInfectionReported(){
        //GIVEN
        InfectionRecordRequest infectionRecordRequest = new InfectionRecordRequest();
        infectionRecordRequest.setReportedById(1L);
        infectionRecordRequest.setSurvivorReportedId(2L);

        Survivor survivorReported = Survivor.builder()
                .id(1L)
                .firstName("David")
                .lastName("Tom")
                .location(new Location())
                .infectionReportTracker(0)
                .build();

        Survivor survivorReporter = Survivor.builder()
                .id(2L)
                .firstName("John")
                .lastName("Doe")
                .location(new Location())
                .infectionReportTracker(0)
                .build();

        InfectionRecord expectedInfectionRecord = InfectionRecord.builder()
                .reportedById(survivorReporter.getId())
                .survivorReportedId(survivorReported.getId())
                .survivorReported(survivorReported.getFirstName().concat(survivorReported.getLastName()))
                .reportedBy(survivorReporter.getFirstName().concat(survivorReporter.getLastName()))
                .dateReported(LocalDateTime.now())
                .build();

        given(survivorRepository.findById(infectionRecordRequest.getReportedById()))
                .willReturn(Optional.ofNullable(survivorReporter));

        given(survivorRepository.findById(infectionRecordRequest.getSurvivorReportedId()))
                .willReturn(Optional.ofNullable(survivorReported));
        //WHEN
        underTest.reportInfection(infectionRecordRequest);

        //THEN
        ArgumentCaptor<InfectionRecord> infectionRecordArgumentCaptor =
                ArgumentCaptor.forClass(InfectionRecord.class);

        verify(infectionRecordRepository).save(infectionRecordArgumentCaptor.capture());

        InfectionRecord capturedInfectionRecord = infectionRecordArgumentCaptor.getValue();

        assertThat(capturedInfectionRecord.getSurvivorReported())
                .isEqualTo(expectedInfectionRecord.getSurvivorReported());
    }
}
