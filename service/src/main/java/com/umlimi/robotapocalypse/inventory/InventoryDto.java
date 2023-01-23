package com.umlimi.robotapocalypse.inventory;

import com.umlimi.robotapocalypse.enums.InventoryType;
import lombok.Data;

/**
 * @author MUNASHE MURIMI
 * @created 23/1/2023
 **/
@Data
public class InventoryDto {
    private Long id;
    private Long survivorId;
    private InventoryType inventoryType;
    private long quantity;
    private boolean activeStatus;

    public String getQuantity(){
        return quantity+ inventoryType.getUnitOfMeasure();
    }
}
