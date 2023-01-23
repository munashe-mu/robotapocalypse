package com.umlimi.robotapocalypse.embeddables;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Location {
    private Double longitude;
    private Double latitude;
}
