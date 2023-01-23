package com.umlimi.robotapocalypse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
@Repository
public interface InventoryRepository extends BaseRepository<Inventory>{
   // Page<Inventory> getInventoryBySurvivor(Survivor survivor, Pageable pageable);
    List<Inventory> getInventoryBySurvivor(Survivor survivor);
}
