package com.umlimi.robotapocalypse.robot;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author MUNASHE MURIMI
 * @created 19/01/2023
 **/
@Data
@Builder
public class RobotDto {
    Long numberOfLandRobots;
    Long numberOfFlyingRobots;
    List<Robot> flyingRobots;
    List<Robot> landRobots;
}
