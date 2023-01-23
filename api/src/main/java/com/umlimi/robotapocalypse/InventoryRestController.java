package com.umlimi.robotapocalypse;

import com.umlimi.robotapocalypse.inventory.InventoryDto;
import com.umlimi.robotapocalypse.inventory.InventoryRequest;
import com.umlimi.robotapocalypse.inventory.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MUNASHE MURIMI
 * @created 23/1/2023
 **/

@RestController
@RequestMapping("v1/robot/apocalypse")
@RequiredArgsConstructor
public class InventoryRestController {
    private final InventoryService inventoryService;

    @Operation(summary = "Save Inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventory saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InventoryDto.class)) })})
    @PostMapping("/save/inventory")
    ResponseEntity<InventoryDto> saveInventory(@RequestBody InventoryRequest inventoryRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inventoryService.saveInventory(inventoryRequest));
    }

    @GetMapping("/get-by-survivor/{survivorId}")
    @Operation(summary = "Get Inventory By Survivor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InventoryDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Inventory not found",
                    content = @Content) })
    ResponseEntity<List<InventoryDto>> getInventoryBySurvivor(@PathVariable Long survivorId){
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(inventoryService.getInventoryBySurvivor(survivorId));
    }

    @Operation(summary = "Updating Inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InventoryDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Inventory not found",
                    content = @Content) })
    @PutMapping("/update/{inventoryId}")
    ResponseEntity<InventoryDto> updateInventory(@PathVariable Long inventoryId,
                                                 @RequestBody InventoryRequest inventoryRequest){
        return ResponseEntity
                .ok()
                .body(inventoryService.updateInventory(inventoryId, inventoryRequest));
    }
}
