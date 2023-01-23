package com.umlimi.robotapocalypse.inventory.impl;

import com.umlimi.robotapocalypse.Inventory;
import com.umlimi.robotapocalypse.InventoryRepository;
import com.umlimi.robotapocalypse.Survivor;
import com.umlimi.robotapocalypse.SurvivorRepository;
import com.umlimi.robotapocalypse.errorhandling.InvalidRequestException;
import com.umlimi.robotapocalypse.errorhandling.InventoryNotFoundException;
import com.umlimi.robotapocalypse.errorhandling.SurvivorNotFoundException;
import com.umlimi.robotapocalypse.inventory.InventoryDto;
import com.umlimi.robotapocalypse.inventory.InventoryRequest;
import com.umlimi.robotapocalypse.inventory.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * @author MUNASHE MURIMI
 * @created 23/1/2023
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final ModelMapper modelMapper;
    private final SurvivorRepository survivorRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public List<InventoryDto> getInventoryBySurvivor(Long survivorId) {
        Survivor survivor   = survivorRepository.findById(survivorId).orElseThrow(
                ()-> new SurvivorNotFoundException(String.format("Survivor with id %s was not found", survivorId))
        );
        List<Inventory> inventoryBySurvivor = inventoryRepository.getInventoryBySurvivor(survivor);
        return inventoryBySurvivor
                .stream()
                .map(inventory -> convertInventoryToDto(inventory))
                .collect(Collectors.toList());
    }

    @Override
    public InventoryDto saveInventory(InventoryRequest inventoryRequest) {
        requireNonNull(inventoryRequest, "InventoryRequest can not be null");
        Survivor survivor = survivorRepository.findById(inventoryRequest.getSurvivorId())
                .orElseThrow(()-> new SurvivorNotFoundException(String.format("Survivor with id" +
                        "%s not found", inventoryRequest.getSurvivorId())));
        if(inventoryRequest.getQuantity() < 0)
            throw new InvalidRequestException(String.format("Quantity, %s, cannot be negative",
                    inventoryRequest.getQuantity()));

        Inventory inventory = Inventory.builder()
                .inventoryType(inventoryRequest.getInventoryType())
                .survivor(survivor)
                .quantity(inventoryRequest.getQuantity())
                .activeStatus(true)
                .build();
        return convertInventoryToDto(inventoryRepository.save(inventory));

    }

    @Override
    public InventoryDto updateInventory(Long inventoryId, InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(
                ()-> new InventoryNotFoundException(String.format("Survivor with id %s not found",
                        inventoryId)));
        inventory.setInventoryType(inventoryRequest.getInventoryType());
        inventory.setQuantity(inventoryRequest.getQuantity());
        inventory.setActiveStatus(inventoryRequest.isActiveStatus());
        return convertInventoryToDto(inventoryRepository.save(inventory));
    }

    private InventoryDto convertInventoryToDto(Inventory inventory){
        InventoryDto inventoryDto = modelMapper.map(inventory, InventoryDto.class);
        return inventoryDto;
    }
}
