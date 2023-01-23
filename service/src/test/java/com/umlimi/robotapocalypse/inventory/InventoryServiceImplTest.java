package com.umlimi.robotapocalypse.inventory;

import com.umlimi.robotapocalypse.Inventory;
import com.umlimi.robotapocalypse.InventoryRepository;
import com.umlimi.robotapocalypse.Survivor;
import com.umlimi.robotapocalypse.SurvivorRepository;
import com.umlimi.robotapocalypse.embeddables.Location;
import com.umlimi.robotapocalypse.enums.InfectionStatus;
import com.umlimi.robotapocalypse.enums.InventoryType;
import com.umlimi.robotapocalypse.errorhandling.InvalidRequestException;
import com.umlimi.robotapocalypse.errorhandling.InventoryNotFoundException;
import com.umlimi.robotapocalypse.errorhandling.SurvivorNotFoundException;
import com.umlimi.robotapocalypse.inventory.impl.InventoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author MUNASHE MURIMI
 * @created 23/1/2023
 **/
@ExtendWith(MockitoExtension.class)
public class InventoryServiceImplTest {
    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SurvivorRepository survivorRepository;

    InventoryService underTest;

    @BeforeEach
    void setUp(){
        underTest = new InventoryServiceImpl(modelMapper, survivorRepository, inventoryRepository);
    }

    @Test
    void canGetInventoryBySurvivor() {
        //GIVEN
        long survivorId = 1L;
        Survivor survivor = Survivor.builder()
                .id(survivorId)
                .firstName("Albert")
                .lastName("Einstein")
                .location(new Location())
                .infectionStatus(InfectionStatus.INFECTED)
                .build();

        given(survivorRepository.findById(survivorId))
                .willReturn(Optional.ofNullable(survivor));
        //WHEN
        underTest.getInventoryBySurvivor(survivorId);
        //THEN
        verify(inventoryRepository).getInventoryBySurvivor(survivor);
    }

    @Test
    @DisplayName("getInventoryBySurvivor method Throws SurvivorNotFoundException When Survivor Not Found")
    void canGetInventoryBySurvivorThrowsSurvivorNotFoundExceptionWhenSurvivorNotFound() {
        //GIVEN
        long survivorId = 1L;

        given(survivorRepository.findById(survivorId))
                .willReturn(Optional.ofNullable(null));
        //WHEN
        //THEN
        assertThatThrownBy(() -> underTest.getInventoryBySurvivor(survivorId))
                .isInstanceOf(SurvivorNotFoundException.class)
                .hasMessageContaining( String.format("Survivor with id %s was not found", survivorId));
    }

    @Test
    void canSaveInventory() {
        //GIVEN
        long survivorId = 1L;
        Survivor survivor = Survivor.builder()
                .id(1L)
                .firstName("Albert")
                .lastName("Einstein")
                .location(new Location())
                .infectionStatus(InfectionStatus.INFECTED)
                .build();

        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setInventoryType(InventoryType.WATER);
        inventoryRequest.setQuantity(4);
        inventoryRequest.setSurvivorId(survivorId);
        inventoryRequest.setActiveStatus(true);

        given(survivorRepository.findById(inventoryRequest.getSurvivorId()))
                .willReturn(Optional.ofNullable(survivor));

        //WHEN
        underTest.saveInventory(inventoryRequest);

        //THEN
        Inventory expectedInventory = Inventory.builder()
                .inventoryType(inventoryRequest.getInventoryType())
                .quantity(inventoryRequest.getQuantity())
                .survivor(survivor)
                .activeStatus(true)
                .build();

        ArgumentCaptor<Inventory> inventoryArgumentCaptor =
                ArgumentCaptor.forClass(Inventory.class);

        verify(inventoryRepository).save(inventoryArgumentCaptor.capture());

        Inventory capturedInventory = inventoryArgumentCaptor.getValue();

        assertThat(capturedInventory).isEqualTo(expectedInventory);
    }

    @Test
    @DisplayName("canSaveInventory method Throws Survivor Not Found Exception When Survivor Not Found")
    void canSaveInventoryThrowsSurvivorNotFoundExceptionWhenSurvivorNotFound() {
        //GIVEN
        long survivorId = 1L;

        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setInventoryType(InventoryType.WATER);
        inventoryRequest.setQuantity(4);
        inventoryRequest.setSurvivorId(survivorId);
        inventoryRequest.setActiveStatus(true);

        given(survivorRepository.findById(inventoryRequest.getSurvivorId()))
                .willReturn(Optional.ofNullable(null));

        //WHEN

        //THEN
        assertThatThrownBy(() -> underTest.saveInventory(inventoryRequest))
                .isInstanceOf(SurvivorNotFoundException.class)
                .hasMessageContaining(String.format("Survivor with id" +
                        "%s not found", inventoryRequest.getSurvivorId()));
    }

    @Test@DisplayName("given negative value for quantity InvalidRequestException is thrown")
    void saveInventoryThrowsInvalidRequestExceptionWhenGivenNegativeValueForQuantity(){
        long survivorId = 1L;
        Survivor survivor = Survivor.builder()
                .id(survivorId)
                .firstName("Albert")
                .lastName("Einstein")
                .location(new Location())
                .infectionStatus(InfectionStatus.INFECTED)
                .build();

        given(survivorRepository.findById(survivorId))
                .willReturn(Optional.ofNullable(survivor));

        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setInventoryType(InventoryType.WATER);
        inventoryRequest.setQuantity(-3);
        inventoryRequest.setSurvivorId(survivor.getId());

        assertThatThrownBy(() -> underTest.saveInventory(inventoryRequest))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining(String.format("Quantity, %s, cannot be negative", inventoryRequest.getQuantity()));

    }

    @Test
    void canUpdateInventory() {
        //GIVEN
        long inventoryId=1L;

        Survivor survivor = Survivor.builder()
                .id(1L)
                .firstName("Albert")
                .lastName("Einstein")
                .location(new Location())
                .infectionStatus(InfectionStatus.INFECTED)
                .build();

        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setInventoryType(InventoryType.WATER);
        inventoryRequest.setQuantity(4);
        inventoryRequest.setSurvivorId(survivor.getId());
        inventoryRequest.setActiveStatus(true);

        Inventory expectedInventory = Inventory.builder()
                .inventoryType(inventoryRequest.getInventoryType())
                .quantity(inventoryRequest.getQuantity())
                .survivor(survivor)
                .activeStatus(true)
                .build();

        given( inventoryRepository.findById(inventoryId))
                .willReturn(Optional.ofNullable( expectedInventory));

        //WHEN
        underTest.updateInventory(inventoryId,inventoryRequest);

        //THEN

        ArgumentCaptor<Inventory> inventoryArgumentCaptor =
                ArgumentCaptor.forClass(Inventory.class);

        verify(inventoryRepository).save(inventoryArgumentCaptor.capture());

        Inventory capturedInventory = inventoryArgumentCaptor.getValue();

        assertThat(capturedInventory).isEqualTo(expectedInventory);
    }
    @Test
    void canUpdateInventoryThrowsInventoryNotFoundExceptionWhenInventoryNotFound() {
        //GIVEN
        long inventoryId=1L;
        InventoryRequest inventoryRequest = new InventoryRequest();
        inventoryRequest.setInventoryType(InventoryType.WATER);
        inventoryRequest.setQuantity(4);
        inventoryRequest.setSurvivorId(1L);
        inventoryRequest.setActiveStatus(true);

        given( inventoryRepository.findById(inventoryId))
                .willReturn(Optional.ofNullable( null));

        //WHEN

        //THEN
        assertThatThrownBy(() -> underTest.updateInventory(inventoryId,inventoryRequest))
                .isInstanceOf(InventoryNotFoundException.class)
                .hasMessageContaining( String.format("Survivor with id %s not found", inventoryId));
    }
}
