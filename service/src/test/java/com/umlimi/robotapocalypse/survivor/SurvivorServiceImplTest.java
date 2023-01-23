package com.umlimi.robotapocalypse.survivor;

import com.umlimi.robotapocalypse.Survivor;
import com.umlimi.robotapocalypse.SurvivorRepository;
import com.umlimi.robotapocalypse.embeddables.Location;
import com.umlimi.robotapocalypse.enums.Gender;
import com.umlimi.robotapocalypse.enums.InfectionStatus;
import com.umlimi.robotapocalypse.errorhandling.InvalidRequestException;
import com.umlimi.robotapocalypse.errorhandling.SurvivorNotFoundException;
import com.umlimi.robotapocalypse.survivor.impl.SurvivorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


/**
 * @author MUNASHE MURIMI
 * @created 23/1/2023
 **/
@ExtendWith(MockitoExtension.class)
public class SurvivorServiceImplTest {

    @Mock
    private SurvivorRepository survivorRepository;
    @Mock
    private ModelMapper  modelMapper;

    private SurvivorService underTest;

    @BeforeEach
    void setUp(){
        underTest = new SurvivorServiceImpl(survivorRepository, modelMapper);
    }

    @Test
    @DisplayName("Can save a survivor")
    void canSaveSurvivor(){
        //GIVEN
        SurvivorSaveRequest survivorSaveRequest = new SurvivorSaveRequest();
        survivorSaveRequest.setAge(26);
        survivorSaveRequest.setGender(Gender.MALE.name());
        survivorSaveRequest.setFirstName("Lionel");
        survivorSaveRequest.setLastName("Messi");
        survivorSaveRequest.setLocation(new Location());

        //WHEN
        underTest.saveSurvivor(survivorSaveRequest);

        //THEN
        Survivor expectedSurvivor = Survivor.builder()
                .infectionReportTracker(Integer.valueOf(0))
                .location(survivorSaveRequest.getLocation())
                .lastName(survivorSaveRequest.getLastName())
                .firstName(survivorSaveRequest.getFirstName())
                .gender(Gender.valueOf(survivorSaveRequest.getGender().toUpperCase()))
                .dateCreated(LocalDate.now())
                .age(survivorSaveRequest.getAge())
                .infectionStatus(InfectionStatus.NOT_INFECTED)
                .build();

        ArgumentCaptor<Survivor> survivorArgumentCaptor = ArgumentCaptor.forClass(Survivor.class);

        verify(survivorRepository).save(survivorArgumentCaptor.capture());

        Survivor capturedSurvivor = survivorArgumentCaptor.getValue();

        assertThat(capturedSurvivor).isEqualTo(expectedSurvivor);
    }

    @Test
    @DisplayName("Given null request save survivor method throws invalid request exception")
    void saveSurvivorMethodThrowsInvalidRequestExceptionWhenPassedNullRequest(){
        //GIVEN
        SurvivorSaveRequest survivorSaveRequest = null;

        //THEN
        assertThatThrownBy(()-> underTest.saveSurvivor(survivorSaveRequest))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("Survivor request can not be null");
    }

    @Test
    @DisplayName("Given a negative value for age save survivor ")
    void saveSurvivorThrowsInvalidRequestExceptionWhenGivenNegativeValueForAge(){
        //GIVEN
        int age = -23;
        SurvivorSaveRequest survivorSaveRequest = new SurvivorSaveRequest();
        survivorSaveRequest.setAge(age);
        survivorSaveRequest.setGender(Gender.FEMALE.name());
        survivorSaveRequest.setLocation(new Location());
        survivorSaveRequest.setFirstName("Emmerson");
        survivorSaveRequest.setLastName("Tosh");

        //WHEN
        //THEN
        assertThatThrownBy(()-> underTest.saveSurvivor(survivorSaveRequest))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining(String.format("Survivor age %s cannot be negative or equal to zero",
                        survivorSaveRequest.getAge()));
    }

    @Test
    @DisplayName("Update location method updates the Location of the survivor")
    void canUpdateLocation(){
        //GIVEN
        Survivor expectedSurvivor = Survivor.builder().id(1L)
                .location(new Location()).
                firstName("Albert")
                .lastName("Einstein")
                .build();
        Location location = new Location();
        location.setLatitude(-17.841236);
        location.setLongitude(30.983880);

        given(survivorRepository.findById(expectedSurvivor.getId()))
                .willReturn(Optional.ofNullable(expectedSurvivor));

        //WHEN
        underTest.updateLocation(expectedSurvivor.getId(), location);

        //THEN
        ArgumentCaptor<Survivor> survivorArgumentCaptor = ArgumentCaptor.forClass(Survivor.class);
        verify(survivorRepository).save(survivorArgumentCaptor.capture());
        Survivor capturedSurvivor = survivorArgumentCaptor.getValue();

        assertThat(capturedSurvivor).isEqualTo(expectedSurvivor);
    }

    @Test
    @DisplayName("Update location method throws survivor not found exception")
    void updateLocationThrowsSurvivorNotFoundException(){
        //GIVEN
        Location location = new Location();
        location.setLatitude(-17.841236);
        location.setLongitude(30.983880);

        long survivorId = 1L;

        //WHEN
        //THEN
        assertThatThrownBy(()-> underTest.updateLocation(survivorId, location))
                .isInstanceOf(SurvivorNotFoundException.class)
                .hasMessageContaining(String.format("Survivor with id %s not found", survivorId));
    }

    @Test
    @DisplayName("Get Survivors By Infection method throws invalidParameterException when passed invalid infection status")
    void getSurvivorsByInfectionStatusThrowsInvalidParameterExceptionWhenPassedInvalidInfectionStatus(){
        //GIVEN
        InfectionStatus infectionStatus = InfectionStatus.INFECTED;

        given(survivorRepository.getSurvivorsByInfectionStatus(infectionStatus))
                .willReturn(Optional.ofNullable(null));
        //WHEN
        //THEN
        assertThatThrownBy(()-> underTest.getSurvivorsByInfectionStatus(infectionStatus))
                .isInstanceOf(SurvivorNotFoundException.class)
                .hasMessageContaining(String.format("Survivor with infection status %s not found", infectionStatus));
    }

    @Test
    @DisplayName("Can get survivors by infection status")
    void canGetSurvivorsByInfectionStatus(){
        //GIVEN
        InfectionStatus infectionStatus = InfectionStatus.INFECTED;
        List<Survivor> survivorList = new ArrayList<>();

        Survivor survivor1 = Survivor.builder()
                .id(1L)
                .lastName("David")
                .firstName("Junior")
                .location(new Location())
                .infectionStatus(InfectionStatus.INFECTED)
                .build();

        Survivor survivor2 = Survivor.builder()
                .id(2L)
                .firstName("Tafadzwa")
                .lastName("Tomu")
                .location(new Location())
                .infectionStatus(InfectionStatus.INFECTED)
                .build();
        survivorList.add(survivor1);
        survivorList.add(survivor2);

        given(survivorRepository.getSurvivorsByInfectionStatus(infectionStatus))
                .willReturn(Optional.ofNullable(survivorList));

        //WHEN
        List<SurvivorDto> result = underTest.getSurvivorsByInfectionStatus(infectionStatus);

        //THEN
        assertThat(result.size() == 2);
    }
    @Test
    @DisplayName("Can get percentage of the infection status")
    void getInfectedOrNonInfectedPercentage(){
        //GIVEN
        InfectionStatus infectionStatus = InfectionStatus.INFECTED;

        given(survivorRepository.countByInfectionStatus(infectionStatus))
                .willReturn(30L);

        given(survivorRepository.count())
                .willReturn(100L);

        //WHEN
        Double result = underTest.getInfectedOrNonInfectedPercentage(infectionStatus);

        //THEN
        Double expectedResult = Double.valueOf(30/100 * 100);
        assertThat(result == expectedResult);
    }
}
