package com.umlimi.robotapocalypse.inventory;

import java.util.List;

/**
 * @author MUNASHE MURIMI
 * @created 23/1/2023
 **/
public interface InventoryService {
    /**
     *This method gets Inventory page using survivor id
     * @param survivorId unique identify of Survivor
     * @return List<InventoryDto> list of InventoryDto
     */
    List<InventoryDto> getInventoryBySurvivor(Long survivorId);
    /**
     *This method saves Inventory  using InventoryRequest
     * @param inventoryRequest
     * @return InventoryDto
     */
    InventoryDto saveInventory(InventoryRequest inventoryRequest);
    /**
     * This method updates inventory using InventoryRequest after searching for it with the
     * inventory unique id
     * @param inventoryId
     * @param inventoryRequest
     * @return InventoryDto
     */
    InventoryDto updateInventory(Long inventoryId, InventoryRequest inventoryRequest);
}
