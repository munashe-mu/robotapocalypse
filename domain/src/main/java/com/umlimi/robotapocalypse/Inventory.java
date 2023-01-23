package com.umlimi.robotapocalypse;

import com.umlimi.robotapocalypse.enums.InventoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Inventory")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survivor_id")
    private Survivor survivor;

    @Enumerated(EnumType.STRING)
    private InventoryType inventoryType;

    private Long quantity;

    private Boolean activeStatus;
}
